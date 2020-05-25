package com.thepascal.todolist.ui.fragments.completed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepascal.todolist.db.entities.TaskEntity
import com.thepascal.todolist.model.DataManager
import com.thepascal.todolist.model.TaskState
import com.thepascal.todolist.repository.TaskRepository

class CompletedViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text

    private var _completedTasks = MutableLiveData<List<TaskEntity>>()
    val completedTasks: LiveData<List<TaskEntity>>
        get() = _completedTasks

    suspend fun handleTaskReactivated(taskEntity: TaskEntity?) {

        taskEntity?.let {
            it.taskState = TaskState.ACTIVE
            repository.updateTask(taskEntity)
        }

    }

    suspend fun handleTaskDeleted(taskEntity: TaskEntity?) {

        taskEntity?.let {
            it.taskState = TaskState.DELETED
            repository.updateTask(taskEntity)
        }

    }

    suspend fun loadCompletedTasks() {
       /* val mediatorLiveData = MediatorLiveData<List<TaskEntity>>()
        mediatorLiveData.addSource(repository.loadCompletedTasks()){_completedTasks.value = it}*/
        repository.loadCompletedTasks().value?.let {
            _completedTasks = MutableLiveData(it)
        }
    }

    suspend fun getCompletedTasks(): LiveData<List<TaskEntity>> {
        return repository.loadCompletedTasks()
    }
}