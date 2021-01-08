package com.thepascal.todolist.ui.fragments.active

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepascal.todolist.db.entities.TaskEntity
import com.thepascal.todolist.model.TaskState
import com.thepascal.todolist.repository.TaskRepository
import com.thepascal.todolist.ui.viewmodels.utils.lazyDeferred
import kotlinx.coroutines.Deferred

class HomeViewModel(private val repository: TaskRepository) : ViewModel() {

    //private var _activeTasks: LiveData<List<TaskEntity>> = MutableLiveData()
    private val _activeTasks by lazyDeferred {
        repository.loadActiveTasks()
    }
    val activeTasks: Deferred<LiveData<List<TaskEntity>>>
        get() = _activeTasks

    suspend fun handleTaskCompleted(taskEntity: TaskEntity?) {

        taskEntity?.let {
            it.taskState = TaskState.COMPLETED
            repository.updateTask(taskEntity)
        }

    }

    suspend fun handleTaskDeleted(taskEntity: TaskEntity?) {

        taskEntity?.let {
            it.taskState = TaskState.DELETED
            repository.updateTask(taskEntity)
        }

    }

    suspend fun getActiveTasks(): LiveData<List<TaskEntity>> {
        return repository.loadActiveTasks()
    }
}