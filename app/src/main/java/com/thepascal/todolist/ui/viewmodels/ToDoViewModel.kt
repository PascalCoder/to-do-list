package com.thepascal.todolist.ui.viewmodels

import android.graphics.Color
import androidx.lifecycle.ViewModel
import com.thepascal.todolist.POSITION_NOT_SET
import com.thepascal.todolist.model.DataManager
import com.thepascal.todolist.model.ToDoModel

class ToDoViewModel: ViewModel() {

    /*var _type: LiveData<String> = MutableLiveData<String>().apply { value = ""}
    var _title: LiveData<String> = MutableLiveData<String>().apply { value = ""}
    var _description: LiveData<String?> = MutableLiveData<String?>().apply { value = null}
    var _completed: LiveData<Boolean?> = MutableLiveData<Boolean?>().apply { value = false}
    var _datePosted: LiveData<String> = MutableLiveData<String>().apply { value = ""}
    var _dueDate: LiveData<String?> = MutableLiveData<String?>().apply { value = null}
    var _dueTime: LiveData<String?> = MutableLiveData<String?>().apply { value = null}*/

    /*private fun <T> LiveData(formattedDate: T, function: () -> Unit): LiveData<String?> {

    }*/
    var isViewModelNew = true

    var isAddressContentDisplayed: Boolean = false
    var taskPosition = POSITION_NOT_SET

    var toDoTask: ToDoModel? = null
    var toDoTaskColor: Int? = Color.TRANSPARENT

    fun createOrUpdateTask(taskPosition: Int) {
        //Add the task to the database
        //For now let's add it to the DataManager list of tasks
        when (taskPosition) {
            POSITION_NOT_SET -> {
                toDoTask?.let {
                    DataManager.activeTaskList.add(it)
                }
            }
            else -> {
                //we just want to update
                toDoTask?.let {
                    DataManager.activeTaskList[taskPosition] = it
                }
            }
        }
    }
}