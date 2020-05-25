package com.thepascal.todolist.ui.fragments.deleted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thepascal.todolist.repository.TaskRepository
import com.thepascal.todolist.ui.fragments.completed.CompletedViewModel

class DeletedViewModelFactory(private val repository: TaskRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DeletedViewModel(repository) as T
    }
}