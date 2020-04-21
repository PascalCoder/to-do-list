package com.thepascal.todolist.ui.fragments.active

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepascal.todolist.model.DataManager
import com.thepascal.todolist.model.TaskState

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun handleTaskCompleted(itemPosition: Int) {
        val toDoModel = DataManager.activeTaskList[itemPosition]
        DataManager.activeTaskList[itemPosition].taskState = TaskState.COMPLETED
        DataManager.completedTaskList.add(toDoModel)
        DataManager.activeTaskList.removeAt(itemPosition)
    }

    fun handleTaskDeleted(itemPosition: Int) {
        val toDoModel = DataManager.activeTaskList[itemPosition]
        DataManager.activeTaskList[itemPosition].taskState = TaskState.DELETED
        DataManager.deletedTaskList.add(toDoModel)
        DataManager.activeTaskList.removeAt(itemPosition)
    }
}