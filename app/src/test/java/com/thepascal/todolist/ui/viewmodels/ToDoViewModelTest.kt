package com.thepascal.todolist.ui.viewmodels

import com.thepascal.todolist.POSITION_NOT_SET
import com.thepascal.todolist.model.AddressModel
import com.thepascal.todolist.model.DataManager
import com.thepascal.todolist.model.ToDoModel
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class ToDoViewModelTest {

    private lateinit var viewModel: ToDoViewModel

    @Before
    fun setUp() {
        viewModel = ToDoViewModel()
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
        updatedToDoTask = ToDoModel(
            updatedToDoTask.type,
            "This is the updated title",
            updatedToDoTask.description,
            updatedToDoTask.datePosted,
            updatedToDoTask.dueDate,
            updatedToDoTask.dueTime,
            updatedToDoTask.address
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
}