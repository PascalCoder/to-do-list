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

fun AddressEntity.convertToAddressModel(): AddressModel {
    return AddressModel(
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

fun TaskEntity.convertToToDoModel(): ToDoModel {
    return ToDoModel(
        id, type, title,
        description, datePosted,
        dueDate, dueTime,
        address?.convertToAddressModel(),
        taskState,color
    )
}

fun List<TaskEntity>.convertToToDoModelList(): List<ToDoModel> {
    //var result = mutableListOf<ToDoModel>()
    return this.map { it.convertToToDoModel() }
}