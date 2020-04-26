package com.thepascal.todolist.ui.viewmodels.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thepascal.todolist.repository.TaskRepository
import com.thepascal.todolist.ui.viewmodels.ToDoViewModel

class ToDoViewModelFactory(private val repository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ToDoViewModel(repository) as T
    }
}