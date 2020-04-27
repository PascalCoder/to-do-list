package com.thepascal.todolist

import android.app.Application
import com.thepascal.todolist.di.databaseModule
import com.thepascal.todolist.di.repositoryModule
import com.thepascal.todolist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TaskApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@TaskApplication)
            modules(listOf(viewModelModule, databaseModule, repositoryModule))
        }
    }
}