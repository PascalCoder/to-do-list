package com.thepascal.todolist.repository

import androidx.lifecycle.LiveData
import com.thepascal.todolist.db.dao.TaskDao
import com.thepascal.todolist.db.entities.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepositoryImpl(private val taskDao: TaskDao) : TaskRepository {

    override suspend fun insertTask(task: TaskEntity) {
        withContext(Dispatchers.IO) {
            taskDao.insertOrUpdateTask(task)
        }
    }

    override suspend fun updateTask(task: TaskEntity) {
        withContext(Dispatchers.IO) {
            taskDao.updateTask(task)
        }
    }

    override suspend fun loadAllTasks(): LiveData<List<TaskEntity>> {
        return withContext(Dispatchers.IO) {
            return@withContext taskDao.getAllTasks()
        }
    }

    override suspend fun loadActiveTasks(): LiveData<List<TaskEntity>> {
        return withContext(Dispatchers.IO) {
            return@withContext taskDao.getActiveTasks()
        }
    }

    override suspend fun loadCompletedTasks(): LiveData<List<TaskEntity>> {
        return withContext(Dispatchers.IO) {
            return@withContext taskDao.getCompletedTasks()
        }
    }

    override suspend fun loadDeletedTasks(): LiveData<List<TaskEntity>> {
        return withContext(Dispatchers.IO) {
            return@withContext taskDao.getDeletedTasks()
        }
    }

    override suspend fun deleteTask(task: TaskEntity) {
        withContext(Dispatchers.IO) {
            taskDao.deleteTask(task)
        }
    }
}