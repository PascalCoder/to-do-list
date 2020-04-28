package com.thepascal.todolist.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.thepascal.todolist.POSITION_NOT_SET
import com.thepascal.todolist.db.entities.TaskEntity
import com.thepascal.todolist.model.DataManager
import com.thepascal.todolist.model.ToDoModel
import com.thepascal.todolist.repository.TaskRepository
import kotlinx.coroutines.runBlocking

class ToDoViewModel(private val repository: TaskRepository?): ViewModel() {

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

    var taskEntity: TaskEntity? = null

    var taskId: Int? = null

    fun addTask(task: TaskEntity) {
        runBlocking {
            repository?.insertTask(task)
        }

    }

    fun getAllTasks() {
        runBlocking {
            repository?.loadAllTasks()
        }
    }

    fun getActiveTasks() {
        runBlocking {
            repository?.loadActiveTasks()
        }
    }

    fun getCompletedTasks() {
        runBlocking {
            repository?.loadCompletedTasks()
        }
    }

    fun getDeletedTasks() {
        runBlocking {
            repository?.loadDeletedTasks()
        }
    }

    fun deleteTask(task: TaskEntity) {
        runBlocking {
            repository?.deleteTask(task)
        }
    }
}