package com.thepascal.todolist.ui.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.thepascal.todolist.POSITION_NOT_SET
import com.thepascal.todolist.R
import com.thepascal.todolist.TASK_POSITION
import com.thepascal.todolist.ui.viewmodels.ToDoViewModel
import com.thepascal.todolist.model.AddressModel
import com.thepascal.todolist.model.DataManager
import com.thepascal.todolist.model.TaskState
import com.thepascal.todolist.model.ToDoModel
import com.thepascal.todolist.ui.fragments.DatePickerFragment
import com.thepascal.todolist.ui.fragments.TimePickerFragment
import kotlinx.android.synthetic.main.activity_to_do.*
import kotlinx.android.synthetic.main.content_address.view.*
import java.text.DateFormat
import java.util.*

class ToDoActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private val toDoViewModel: ToDoViewModel by lazy {
        ViewModelProvider(this).get(
            ToDoViewModel::class.java
        )
    }
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)

        toDoViewModel.isViewModelNew = (savedInstanceState == null)

        populateSpinner()

        if (intent.hasExtra(TASK_POSITION)) {
            toDoTaskSubmitButton.text = getString(R.string.update_task_button_text)
            toDoViewModel.taskPosition = intent.getIntExtra(
                TASK_POSITION,
                POSITION_NOT_SET
            )
            if (toDoViewModel.isViewModelNew) {
                updateTaskAtPosition(toDoViewModel.taskPosition)
            } else {
                repopulateFieldsWithViewModel()
            }
        } else {
            if (!toDoViewModel.isViewModelNew) {
                repopulateFieldsWithViewModel()
            }
        }

        toDoTaskDueDateButton.setOnClickListener {
            val datePicker: DialogFragment =
                DatePickerFragment()
            datePicker.show(supportFragmentManager, "Date Picker")
        }

        toDoTaskDueTimeButton.setOnClickListener {
            val timePicker: DialogFragment =
                TimePickerFragment()
            timePicker.show(supportFragmentManager, "Time Picker")
        }

        toDoTaskAddressButton.setOnClickListener {
            toggleAddressButton()
        }

        toDoTaskSubmitButton.setOnClickListener {
            setUpToDoViewModelWithUserInputs()
            toDoViewModel.createOrUpdateTask(toDoViewModel.taskPosition)
            Log.d("Created task", "Task: ${toDoViewModel.toDoTask}")

            //Navigate to the Home page
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        setUpToDoViewModelWithUserInputs()
    }

    private fun repopulateFieldsWithViewModel() {
        toDoTaskDueDateText.text = toDoViewModel.toDoTask?.dueDate
        toDoTaskDueTimeText.text = toDoViewModel.toDoTask?.dueTime
        toDoViewModel.toDoTask?.address?.let {
            showAddressField()
            fillInAddressFields(it)
        }
    }

    private fun toggleAddressButton() {
        if (!toDoViewModel.isAddressContentDisplayed ||
            toDoTaskAddressButton.text == getString(R.string.add_address_btn_text)) {
            showAddressField()
        } else {
            hideAddressField()
        }
    }

    private fun updateTaskAtPosition(position: Int) {
        toDoViewModel.toDoTask = DataManager.activeTaskList[position]

        val taskTypePosition: Int =
            DataManager.taskTypeList.indexOf<Any?>(toDoViewModel.toDoTask?.type)
        toDoTaskTypeSpinner.setSelection(taskTypePosition)
        toDoTaskTitle.setText(toDoViewModel.toDoTask?.title)
        toDoTaskDescription.setText(toDoViewModel.toDoTask?.description)
        toDoTaskDueDateText.text = toDoViewModel.toDoTask?.dueDate
        toDoTaskDueTimeText.text = toDoViewModel.toDoTask?.dueTime

        toDoViewModel.toDoTask?.address?.let {
            showAddressField()
            fillInAddressFields(it)
        }
    }

    private fun showAddressField() {
        toDoViewModel.isAddressContentDisplayed = true
        toDoTaskAddressContent.visibility = View.VISIBLE
        toDoTaskAddressButton.text = getString(R.string.hide_address_btn_text)
    }

    private fun hideAddressField() {
        toDoViewModel.isAddressContentDisplayed = false
        toDoTaskAddressButton.text = getString(R.string.add_address_btn_text)
        toDoTaskAddressContent.visibility = View.GONE
    }

    private fun fillInAddressFields(address: AddressModel) {
        toDoTaskAddressContent.taskAddressLine1.editText?.setText(address.addressLine1)
        toDoTaskAddressContent.taskAddressLine2.editText?.setText(address.addressLine2)
        toDoTaskAddressContent.taskCity.editText?.setText(address.city)
        toDoTaskAddressContent.taskState.editText?.setText(address.state)
        toDoTaskAddressContent.taskCountry.editText?.setText(address.country)
        toDoTaskAddressContent.taskZipCode.editText?.setText(address.zipCode)
    }

    private fun setUpToDoViewModelWithUserInputs() {
        val type = toDoTaskTypeSpinner.selectedItem.toString()
        val title = toDoTaskTitle.text.toString()
        val description = toDoTaskDescription.text.toString()
        val datePosted = Calendar.getInstance().time.toString()
        val dueDate = toDoTaskDueDateText.text.toString()
        val dueTime = toDoTaskDueTimeText.text.toString()

        val address: AddressModel? = if (toDoViewModel.isAddressContentDisplayed) {
            AddressModel(
                toDoTaskAddressContent.taskAddressLine1.editText?.text.toString(),
                toDoTaskAddressContent.taskAddressLine2.editText?.text.toString(),
                toDoTaskAddressContent.taskCity.editText?.text.toString(),
                toDoTaskAddressContent.taskState.editText?.text.toString(),
                toDoTaskAddressContent.taskCountry.editText?.text.toString(),
                toDoTaskAddressContent.taskZipCode.editText?.text.toString()
            )
        } else {
            toDoViewModel.toDoTask?.address
        }
        //we'll do some validation first

        //Create the task if validation passes
        toDoViewModel.toDoTask = ToDoModel(
            type = type, title = title, description = description,
            datePosted = datePosted, dueDate = dueDate,
            dueTime = dueTime, address = address,
            taskState = TaskState.ACTIVE
        )
    }

    private fun populateSpinner() {
        val taskTypeList = DataManager.taskTypeList
        val taskAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, taskTypeList)
        taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        toDoTaskTypeSpinner.adapter = taskAdapter
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val stringDate = DateFormat.getDateInstance(DateFormat.FULL)
            .format(calendar.time)

        toDoTaskDueDateText.text = stringDate
        toDoViewModel.toDoTask?.dueDate = stringDate
    }

    override fun onTimeSet(timePicker: TimePicker?, hour: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        var stringTime = DateFormat.getInstance().format(calendar.time)
        stringTime = stringTime.substring(stringTime.indexOf(" "))

        toDoTaskDueTimeText.text = stringTime
        //toDoTaskDueTimeText.textSize = 12f

        toDoViewModel.toDoTask?.dueTime = stringTime
    }
}
