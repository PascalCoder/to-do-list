package com.thepascal.todolist.ui.fragments.deleted

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepascal.todolist.db.entities.TaskEntity
import com.thepascal.todolist.model.DataManager
import com.thepascal.todolist.repository.TaskRepository

class DeletedViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    fun handleTaskDeleted(itemPosition: Int) {
        val toDoModel = DataManager.deletedTaskList[itemPosition]
        DataManager.deletedTaskList.removeAt(itemPosition)
        DataManager.allTaskList.remove(toDoModel)
    }

    suspend fun getDeletedTasks(): LiveData<List<TaskEntity>> {
        return repository.loadDeletedTasks()
    }
}