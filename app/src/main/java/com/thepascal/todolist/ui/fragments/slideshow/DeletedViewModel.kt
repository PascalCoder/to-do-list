package com.thepascal.todolist.ui.fragments.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepascal.todolist.model.DataManager

class DeletedViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    fun handleTaskDeleted(itemPosition: Int) {
        val toDoModel = DataManager.deletedTaskList[itemPosition]
        DataManager.deletedTaskList.removeAt(itemPosition)
        DataManager.allTaskList.remove(toDoModel)
    }
}