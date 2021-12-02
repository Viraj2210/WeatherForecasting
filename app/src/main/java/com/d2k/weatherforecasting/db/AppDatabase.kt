package com.d2k.weatherforecasting.db

import androidx.room.Database
import androidx.room.RoomDatabase

import com.d2k.weatherforecasting.db.dao.UserDao
import com.d2k.weatherforecasting.db.entity.UserTable

@Database(
    version = 1,
    entities = [UserTable::class],
)

abstract class AppDatabase :RoomDatabase(){

    abstract fun userDao(): UserDao
}