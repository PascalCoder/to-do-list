package com.thepascal.todolist.ui.fragments.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepascal.todolist.model.DataManager
import com.thepascal.todolist.model.TaskState

class CompletedViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is gallery Fragment"
    }
    val text: LiveData<String> = _text

    fun handleTaskCompleted(itemPosition: Int) {
        val toDoModel = DataManager.completedTaskList[itemPosition]
        DataManager.completedTaskList[itemPosition].taskState = TaskState.ACTIVE
        DataManager.activeTaskList.add(toDoModel)
        DataManager.completedTaskList.removeAt(itemPosition)
    }

    fun handleTaskDeleted(itemPosition: Int) {

    }
}