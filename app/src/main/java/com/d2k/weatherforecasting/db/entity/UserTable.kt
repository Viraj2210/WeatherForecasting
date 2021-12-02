package com.d2k.weatherforecasting.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class UserTable(
        @PrimaryKey(autoGenerate = true) var userId: Int? = 0,
        @ColumnInfo(name = "FirstName")
        var firstName: String,
        @ColumnInfo(name = "LastName")
        var lastName: String,
        @ColumnInfo(name = "Email")
        var email: String
        )
