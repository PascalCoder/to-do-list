package com.thepascal.todolist.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thepascal.todolist.model.TaskState

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val type: String?,
    val title: String?,
    val description: String?,
    @ColumnInfo(name = "date_posted")
    val datePosted: String?,
    @ColumnInfo(name = "due_date")
    var dueDate: String?,
    @ColumnInfo(name = "due_time")
    var dueTime: String?,
    @Embedded(prefix = "address_")
    val address: AddressEntity?,
    @ColumnInfo(name = "task_state")
    var taskState: TaskState? = TaskState.ACTIVE,
    var color: Int? = android.R.color.transparent
)