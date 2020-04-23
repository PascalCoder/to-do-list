package com.thepascal.todolist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thepascal.todolist.db.dao.TaskDao
import com.thepascal.todolist.db.entities.TaskEntity
import com.thepascal.todolist.db.utils.TaskConverters

@Database(
    entities = [TaskEntity::class],
    version = 1
)
@TypeConverters(TaskConverters::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao() : TaskDao

    companion object {
        private var instance: TaskDatabase? = null

        operator fun invoke(context: Context) =
            instance ?: buildDatabase(context).also {
                instance = it
            }

        private fun buildDatabase(context: Context) =
             Room.databaseBuilder(context.applicationContext,
             TaskDatabase::class.java, "task.db")
                 .build()
    }
}