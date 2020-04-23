package com.thepascal.todolist.db.utils

import androidx.room.TypeConverter
import com.thepascal.todolist.model.TaskState

class TaskConverters {

    @TypeConverter
    fun fromStateToInt(taskState: TaskState) : Int = taskState.intValue

    @TypeConverter
    fun fromIntToState(value: Int) : TaskState {
        return when(value) {
            1 -> TaskState.ACTIVE
            2 -> TaskState.COMPLETED
            3 -> TaskState.DELETED
            else -> TaskState.OVERDUE
        }
    }
}