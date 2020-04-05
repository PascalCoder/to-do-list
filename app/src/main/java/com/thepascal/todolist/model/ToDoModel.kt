package com.thepascal.todolist.model

import android.os.Parcel
import android.os.Parcelable

data class ToDoModel(
    val type: String?,
    val title: String?,
    val description: String?,
    val datePosted: String?,
    val dueDate: String?,
    val dueTime: String?,
    val address: AddressModel?,
    var taskState: TaskState? = TaskState.ACTIVE
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(AddressModel::class.java.classLoader)
        //parcel.readInt() as TaskState
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(datePosted)
        parcel.writeString(dueDate)
        parcel.writeString(dueTime)
        parcel.writeParcelable(address, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ToDoModel> {
        override fun createFromParcel(parcel: Parcel): ToDoModel {
            return ToDoModel(parcel)
        }

        override fun newArray(size: Int): Array<ToDoModel?> {
            return arrayOfNulls(size)
        }
    }
}

enum class TaskState(var intValue: Int) {
    ACTIVE(1),
    COMPLETED(2),
    DELETED(3),
    OVERDUE(4)
}
