package com.thepascal.todolist.ui.fragments.active

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thepascal.todolist.repository.TaskRepository

class HomeViewModelFactory(private val repository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}