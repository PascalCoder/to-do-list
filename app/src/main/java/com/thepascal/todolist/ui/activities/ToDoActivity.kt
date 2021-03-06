package com.thepascal.todolist.ui.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.thepascal.todolist.POSITION_NOT_SET
import com.thepascal.todolist.R
import com.thepascal.todolist.TASK_POSITION
import com.thepascal.todolist.model.AddressModel
import com.thepascal.todolist.model.DataManager
import com.thepascal.todolist.model.TaskState
import com.thepascal.todolist.model.ToDoModel
import com.thepascal.todolist.ui.fragments.DatePickerFragment
import com.thepascal.todolist.ui.fragments.TimePickerFragment
import com.thepascal.todolist.ui.utils.convertToTaskEntity
import com.thepascal.todolist.ui.utils.convertToToDoModel
import com.thepascal.todolist.ui.viewmodels.ToDoViewModel
import com.thepascal.todolist.ui.viewmodels.utils.ToDoViewModelFactory
import kotlinx.android.synthetic.main.activity_to_do.*
import kotlinx.android.synthetic.main.color_selector.view.*
import kotlinx.android.synthetic.main.content_address.view.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.text.DateFormat
import java.util.*

class ToDoActivity : ScopedActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private val mToDoViewModelFactory by inject<ToDoViewModelFactory>()
    private val toDoViewModel: ToDoViewModel by lazy {
        ViewModelProvider(this, mToDoViewModelFactory).get(
            ToDoViewModel::class.java
        )
    }
    private val calendar: Calendar = Calendar.getInstance()
    private var update = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)

        toDoViewModel.isViewModelNew = (savedInstanceState == null)

        populateSpinner()

        if (intent.hasExtra(TASK_POSITION)) {
            toDoTaskSubmitButton.text = getString(R.string.update_task_button_text)
            update = true
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
            update = false
            if (!toDoViewModel.isViewModelNew) {
                repopulateFieldsWithViewModel()
            }
        }

        toDoTaskColorSelector.setListener {
            toDoViewModel.toDoTask?.color = it
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
            //toDoViewModel.createOrUpdateTask(toDoViewModel.taskPosition)

            launch {
                toDoViewModel.toDoTask?.let {
                    if (update) {
                        toDoViewModel.updateTask(it.convertToTaskEntity())
                        Toast.makeText(this@ToDoActivity, "Task updated",Toast.LENGTH_LONG).show()
                    } else {
                        toDoViewModel.addTask(it.convertToTaskEntity())
                        Toast.makeText(this@ToDoActivity, "Task inserted",Toast.LENGTH_LONG).show()
                    }
                }
            }
            Log.d("Created task", "Task: ${toDoViewModel.toDoTask?.convertToTaskEntity()}")

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
        toDoTaskColorSelector.selectedColorValue = toDoViewModel.toDoTask?.color
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
        //toDoViewModel.toDoTask = DataManager.activeTaskList[position] //needs to be updated to use the db tasks
        //Let's retrieve the active task list from the db
        //and assign the one at "position" to toDoViewModel.toDoTask
        launch {
            toDoViewModel.getActiveTasks().observe(this@ToDoActivity, Observer {
                toDoViewModel.toDoTask = (it[position]).convertToToDoModel()

                val taskTypePosition: Int =
                    DataManager.taskTypeList.indexOf<Any?>(toDoViewModel.toDoTask?.type)
                toDoTaskTypeSpinner.setSelection(taskTypePosition)
                toDoTaskTitle.setText(toDoViewModel.toDoTask?.title)
                toDoTaskDescription.setText(toDoViewModel.toDoTask?.description)
                toDoTaskDueDateText.text = toDoViewModel.toDoTask?.dueDate
                toDoTaskDueTimeText.text = toDoViewModel.toDoTask?.dueTime

                toDoViewModel.toDoTask?.address?.let { address ->
                    showAddressField()
                    fillInAddressFields(address)
                }

                toDoTaskColorSelector.selectedColorValue = toDoViewModel.toDoTask?.color
            })
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
        val color = (toDoTaskColorSelector.selectedColor.background as ColorDrawable).color

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
            taskState = TaskState.ACTIVE,
            color = color
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
