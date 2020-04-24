package com.thepascal.todolist

import android.app.Application
import com.thepascal.todolist.db.TaskDatabase
import com.thepascal.todolist.repository.TaskRepository
import com.thepascal.todolist.repository.TaskRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TaskApplication : Application() {

    private val dataModule = module {
        //single { get<TaskDao>() }
        //single { TaskDatabase.invoke(this@TaskApplication) }
        single { TaskDatabase(this@TaskApplication) }
        single { get<TaskDatabase>().taskDao() }
        single<TaskRepository> { TaskRepositoryImpl(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TaskApplication)
            modules(listOf(dataModule))
        }
    }
}