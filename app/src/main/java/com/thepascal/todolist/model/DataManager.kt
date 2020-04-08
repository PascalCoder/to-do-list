package com.thepascal.todolist.model

object DataManager {
    val taskTypeList = mutableListOf("Grocery", "Appointment", "Meeting", "Outdoor Activity", "Homework")

    val activeTaskList = mutableListOf(
        ToDoModel(type = "Grocery", title = "Need some paper towel 1", description = null,
             datePosted = "03/24/2020", dueDate = "03/27/2020",
            dueTime = "03:00 PM", address = null, taskState = TaskState.ACTIVE),
        ToDoModel("Grocery", "Need some paper towel 2",
            null, "03/24/2020", "03/27/2020",
            "03:00 PM", null, TaskState.ACTIVE),
        ToDoModel("Grocery", "Need some paper towel 3",
            null, "03/24/2020", "03/27/2020",
            "03:00 PM", null, TaskState.ACTIVE),
        ToDoModel("Grocery", "Need some paper towel 4",
            null, "03/24/2020", "03/27/2020",
            "03:00 PM", null, TaskState.ACTIVE),
        ToDoModel("Grocery", "Need some paper towel 5",
            null, "03/24/2020", "03/27/2020",
            "03:00 PM", null, TaskState.ACTIVE)

    )

    val completedTaskList = mutableListOf(
        ToDoModel(type = "Appointment", title = "Need some paper towel", description = null,
            datePosted = "03/24/2020", dueDate = "03/27/2020",
            dueTime = "03:00 PM", address = null, taskState = TaskState.COMPLETED)
    )
    val deletedTaskList = mutableListOf(
        ToDoModel(taskTypeList[3], "Need some paper towel",
        null, "03/24/2020", "03/27/2020",
        "03:00 PM", null, TaskState.DELETED)
    )
    val overdueTaskList = mutableListOf<ToDoModel>()

    var allTaskList:MutableList<ToDoModel> = (activeTaskList + completedTaskList + overdueTaskList + deletedTaskList).toMutableList()

}