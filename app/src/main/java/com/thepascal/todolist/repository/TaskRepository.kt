package com.thepascal.todolist.repository

import androidx.lifecycle.LiveData
import com.thepascal.todolist.db.entities.TaskEntity

interface TaskRepository {

    suspend fun insertTask(task: TaskEntity)
    suspend fun loadAllTasks() : LiveData<List<TaskEntity>>
    suspend fun loadActiveTasks() : LiveData<List<TaskEntity>>
    suspend fun loadCompletedTasks() : LiveData<List<TaskEntity>>
    suspend fun loadDeletedTasks() : LiveData<List<TaskEntity>>
    suspend fun deleteTask(id: Int)
}