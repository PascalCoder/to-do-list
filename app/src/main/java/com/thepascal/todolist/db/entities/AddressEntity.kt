package com.thepascal.todolist.db.entities

import androidx.room.ColumnInfo

class AddressEntity(
    @ColumnInfo(name = "line_1")
    val addressLine1: String?,
    @ColumnInfo(name = "line_2")
    val addressLine2: String?,
    val city: String?, val state: String?,
    val country: String?,
    @ColumnInfo(name = "zip_code")
    val zipCode: String?
)