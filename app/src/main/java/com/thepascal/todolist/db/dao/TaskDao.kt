package com.thepascal.todolist.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.thepascal.todolist.db.entities.TaskEntity

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateTask(task: TaskEntity)

    @Update//(onConflict = OnConflictStrategy.REPLACE)
    fun updateTask(task: TaskEntity)

    @Query("SELECT * FROM tasks" )
    fun getAllTasks() : LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE task_state LIKE 1")
    fun getActiveTasks() : LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE task_state LIKE 2")
    fun getCompletedTasks() : LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE task_state LIKE 3")
    fun getDeletedTasks() : LiveData<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTaskById(id: Int) : LiveData<List<TaskEntity>>

    @Delete
    fun deleteTask(task: TaskEntity)
}