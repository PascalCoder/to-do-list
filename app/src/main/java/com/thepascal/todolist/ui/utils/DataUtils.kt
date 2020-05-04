package com.thepascal.todolist.ui.utils

import com.thepascal.todolist.db.entities.AddressEntity
import com.thepascal.todolist.db.entities.TaskEntity
import com.thepascal.todolist.model.AddressModel
import com.thepascal.todolist.model.ToDoModel

fun AddressModel.convertToAddressEntity(): AddressEntity {
    return AddressEntity(
        addressLine1,
        addressLine2,
        city, state,
        country, zipCode
    )
}

fun ToDoModel.convertToTaskEntity(): TaskEntity {
    return TaskEntity(
        0, type, title,
        description, datePosted,
        dueDate, dueTime,
        address?.convertToAddressEntity(),
        taskState, color
    )
}