package com.d2k.weatherforecasting.di

import androidx.lifecycle.LiveData
import com.d2k.weatherforecasting.db.AppDatabase
import com.d2k.weatherforecasting.db.entity.UserTable
import javax.inject.Inject

class DBRepository @Inject constructor(val appDatabase: AppDatabase) {

    suspend fun insertIntoUser(article: UserTable): Long {
        return appDatabase.userDao()
            .insertIntoTable(article)
    }

    suspend fun delete(article: UserTable) {
        appDatabase.userDao().deleteUser(article)
    }


    fun getAllUser(): LiveData<List<UserTable>> {
        return appDatabase.userDao().getAllUsers()
    }


}