package com.d2k.weatherforecasting.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*

import com.d2k.weatherforecasting.db.entity.UserTable

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoTable(userTable: UserTable) : Long

    @Delete
    suspend fun deleteUser(userTable: UserTable)

    @Query("Select * from Users")
    fun getAllUsers() : LiveData<List<UserTable>>
}