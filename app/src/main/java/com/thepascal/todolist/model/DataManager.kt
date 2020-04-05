package com.thepascal.todolist.model

object DataManager {
    val activeTaskList = mutableListOf(
        ToDoModel(type = "Grocery", title = "Need some paper towel", description = null,
             datePosted = "03/24/2020", dueDate = "03/27/2020",
            dueTime = "03:00 PM", address = null, taskState = TaskState.ACTIVE),
        ToDoModel("Grocery", "Need some paper towel",
            null, "03/24/2020", "03/27/2020",
            "03:00 PM", null, TaskState.ACTIVE),
        ToDoModel("Grocery", "Need some paper towel",
            null, "03/24/2020", "03/27/2020",
            "03:00 PM", null, TaskState.ACTIVE),
        ToDoModel("Grocery", "Need some paper towel",
            null, "03/24/2020", "03/27/2020",
            "03:00 PM", null, TaskState.ACTIVE),
        ToDoModel("Grocery", "Need some paper towel",
            null, "03/24/2020", "03/27/2020",
            "03:00 PM", null, TaskState.ACTIVE)

    )

    val completedTaskList = mutableListOf<ToDoModel>()
    val deletedTaskList = mutableListOf<ToDoModel>()
    val overdueTaskList = mutableListOf<ToDoModel>()

    val allTaskList = activeTaskList + completedTaskList + deletedTaskList + overdueTaskList

    val taskTypeList = mutableListOf("Grocery", "Appointment", "Meeting", "Outdoor Activity", "Homework")
}