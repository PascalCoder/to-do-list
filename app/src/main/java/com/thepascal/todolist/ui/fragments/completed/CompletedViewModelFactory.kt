package com.thepascal.todolist.ui.fragments.completed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thepascal.todolist.repository.TaskRepository
import com.thepascal.todolist.ui.fragments.active.HomeViewModel

class CompletedViewModelFactory(private val repository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CompletedViewModel(repository) as T
    }
}