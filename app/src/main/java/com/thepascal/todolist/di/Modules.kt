package com.thepascal.todolist.di

import android.content.Context
import com.thepascal.todolist.db.TaskDatabase
import com.thepascal.todolist.db.dao.TaskDao
import com.thepascal.todolist.repository.TaskRepository
import com.thepascal.todolist.repository.TaskRepositoryImpl
import com.thepascal.todolist.ui.fragments.active.HomeViewModelFactory
import com.thepascal.todolist.ui.fragments.completed.CompletedViewModelFactory
import com.thepascal.todolist.ui.viewmodels.ToDoViewModel
import com.thepascal.todolist.ui.viewmodels.utils.ToDoViewModelFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ToDoViewModel(repository = get()) }
    factory { ToDoViewModelFactory(repository = get()) }
    factory { HomeViewModelFactory(repository = get()) }
    factory { CompletedViewModelFactory(repository = get()) }
}

val databaseModule = module {
    fun provideDatabase(context: Context): TaskDatabase {
        return TaskDatabase.invoke(context)
    }

    fun provideDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    single { provideDatabase(context = get()) }
    single { provideDao(database = get()) }
}

val repositoryModule = module {
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(taskDao)
    }

    single { provideTaskRepository(taskDao = get()) }
}



