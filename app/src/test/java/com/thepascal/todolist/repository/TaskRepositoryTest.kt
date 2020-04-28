package com.thepascal.todolist.repository

import com.thepascal.todolist.db.dao.TaskDao
import com.thepascal.todolist.db.entities.TaskEntity
import com.thepascal.todolist.model.DataManager
import com.thepascal.todolist.model.TaskState
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class TaskRepositoryTest {

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var repository: TaskRepository

    @Mock
    private lateinit var taskDao: TaskDao

    private val taskEntity = TaskEntity(
        0, DataManager.taskTypeList[1], "Dentist Appointment",
        "Will need to go see my dentist for clean up", "04/27/2020",
        "05/04/2020", "10:00 AM", null, TaskState.ACTIVE
    )

    @Before
    fun setUp() {
        repository = TaskRepositoryImpl(taskDao)
    }

    @Test
    fun `test insertTask`() = runBlocking {
        repository.insertTask(taskEntity)
        Mockito.verify(taskDao).insertOrUpdateTask(taskEntity)
    }

    @Test
    fun `test loadAllTasks`() {
        runBlocking {
            repository.loadAllTasks()
        }
        Mockito.verify(taskDao).getAllTasks()
    }

    @Test
    fun `test loadActiveTasks`() {
        runBlocking {
            repository.loadActiveTasks()
        }
        Mockito.verify(taskDao).getActiveTasks()
    }

    @Test
    fun `test loadCompletedTasks`() {
        runBlocking {
            repository.loadCompletedTasks()
        }
        Mockito.verify(taskDao).getCompletedTasks()
    }

    @Test
    fun `test loadDeletedTasks`() {
        runBlocking {
            repository.loadDeletedTasks()
        }
        Mockito.verify(taskDao).getDeletedTasks()
    }

    @Test
    fun `test deleteTask`() {
        runBlocking {
            repository.deleteTask(taskEntity)
        }
        Mockito.verify(taskDao).deleteTask(taskEntity)
    }
}