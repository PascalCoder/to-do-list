package com.thepascal.todolist.ui.viewmodels

import com.thepascal.todolist.POSITION_NOT_SET
import com.thepascal.todolist.db.entities.TaskEntity
import com.thepascal.todolist.model.AddressModel
import com.thepascal.todolist.model.DataManager
import com.thepascal.todolist.model.TaskState
import com.thepascal.todolist.model.ToDoModel
import com.thepascal.todolist.repository.TaskRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class ToDoViewModelTest {

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private lateinit var viewModel: ToDoViewModel

    @Mock
    private lateinit var repository: TaskRepository

    private val taskEntity = TaskEntity(
        0, DataManager.taskTypeList[1], "Dentist Appointment",
        "Will need to go see my dentist for clean up", "04/27/2020",
        "05/04/2020", "10:00 AM", null, TaskState.ACTIVE
    )

    @Before
    fun setUp() {
        viewModel = ToDoViewModel(repository)
    }

    @Test
    fun `test createOrUpdateTask when creating new task`() {
        val positionNotSet = POSITION_NOT_SET
        val newToDoTask = ToDoModel(
            type = DataManager.taskTypeList[0],
            title = "Getting grocery",
            description = "Need to buy food and supply",
            datePosted = "Sat, Apr. 11 2020",
            dueDate = "Sat, Apr. 11 2020",
            dueTime = "Sat, Apr. 11 2020",
            address = AddressModel("Walmart",
            null, null, null,
                null, null)
        )
        viewModel.toDoTask = newToDoTask
        viewModel.createOrUpdateTask(positionNotSet)
        val actualToDoTask = DataManager.activeTaskList[DataManager.activeTaskList.size - 1]
        assertEquals(newToDoTask.type, actualToDoTask.type)
        assertEquals(newToDoTask.title, actualToDoTask.title)
        assertEquals(newToDoTask.description, actualToDoTask.description)
        assertEquals(newToDoTask.datePosted, actualToDoTask.datePosted)
        assertEquals(newToDoTask.dueDate, actualToDoTask.dueDate)
        assertEquals(newToDoTask.dueTime, actualToDoTask.dueTime)
        assertEquals(newToDoTask.address, actualToDoTask.address)
        assertEquals(newToDoTask.taskState, actualToDoTask.taskState)
    }

    @Test
    fun `test createOrUpdateTask when updating new task`() {
        val taskPosition = 1
        var updatedToDoTask = DataManager.activeTaskList[taskPosition]
        updatedToDoTask = ToDoModel(0,
            updatedToDoTask.type,
            "This is the updated title",
            updatedToDoTask.description,
            updatedToDoTask.datePosted,
            updatedToDoTask.dueDate,
            updatedToDoTask.dueTime,
            updatedToDoTask.address,
            TaskState.ACTIVE, 0
        )

        viewModel.toDoTask = updatedToDoTask
        viewModel.createOrUpdateTask(taskPosition)
        val actualToDoTask = DataManager.activeTaskList[taskPosition]
        assertEquals(updatedToDoTask.type, actualToDoTask.type)
        assertEquals(updatedToDoTask.title, actualToDoTask.title)
        assertEquals(updatedToDoTask.description, actualToDoTask.description)
        assertEquals(updatedToDoTask.datePosted, actualToDoTask.datePosted)
        assertEquals(updatedToDoTask.dueDate, actualToDoTask.dueDate)
        assertEquals(updatedToDoTask.dueTime, actualToDoTask.dueTime)
        assertEquals(updatedToDoTask.address, actualToDoTask.address)
        assertEquals(updatedToDoTask.taskState, actualToDoTask.taskState)
    }

    @Test
    fun `test addTask`() = runBlocking {
        viewModel.taskEntity = taskEntity
        viewModel.addTask(taskEntity)

        Mockito.verify(repository).insertTask(taskEntity)
    }

    @Test
    fun `test getAllTasks`() {
        runBlocking {
            viewModel.getAllTasks()
            Mockito.verify(repository).loadAllTasks()
        }
    }

    @Test
    fun `test getActiveTasks`() {
        runBlocking {
            viewModel.getActiveTasks()
            Mockito.verify(repository).loadActiveTasks()
        }
    }

    @Test
    fun `test getCompletedTasks`() {
        runBlocking {
            viewModel.getCompletedTasks()
            Mockito.verify(repository).loadCompletedTasks()
        }
    }

    @Test
    fun `test getDeletedTasks`() {
        runBlocking {
            viewModel.getDeletedTasks()
            Mockito.verify(repository).loadDeletedTasks()
        }
    }

    @Test
    fun `test deleteTask`() = runBlocking {
        viewModel.deleteTask(taskEntity)
        Mockito.verify(repository).deleteTask(taskEntity)
    }
}