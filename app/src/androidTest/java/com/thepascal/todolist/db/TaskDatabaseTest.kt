package com.thepascal.todolist.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.thepascal.todolist.db.dao.TaskDao
import com.thepascal.todolist.db.entities.TaskEntity
import com.thepascal.todolist.model.DataManager
import com.thepascal.todolist.model.TaskState
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TaskDatabaseTest {

    private lateinit var taskDao: TaskDao
    private lateinit var taskDatabase: TaskDatabase

    private val taskEntity = TaskEntity(
        0, DataManager.taskTypeList[1], "Dentist Appointment",
        "Will need to go see my dentist for clean up", "04/27/2020",
        "05/04/2020", "10:00 AM", null, TaskState.ACTIVE
    )

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        taskDatabase = Room.inMemoryDatabaseBuilder(
            context, TaskDatabase::class.java).build()
        taskDao = taskDatabase.taskDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        taskDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertUserTest() {
        taskDao.insertOrUpdateTask(taskEntity)
        val tasks = taskDao.getAllTasks().value!!
        assertEquals(tasks[0], taskEntity)
    }

    // TODO: add more test for other operations such as retrieving and deleting Tasks
}