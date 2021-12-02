package com.d2k.weatherforecasting.ui.vm

import androidx.lifecycle.*

import com.d2k.weatherforecasting.db.entity.UserTable
import com.d2k.weatherforecasting.di.DBRepository
import com.d2k.weatherforecasting.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserVm @Inject constructor(private val dbRepository: DBRepository) : ViewModel() {


    /*Transformation converts the LiveData article entity to LiveData article model class
    * and LiveData Datahandler  is observed from fragment
    */
    var userList = Transformations.map(dbRepository.getAllUser()) { list ->

        /*val temp = list.map {
            Transformer.convertArticleEntityToArticleModel(it)
        }*/
        if (list.isNullOrEmpty()) {
            DataHandler.ERROR(null, "LIST IS EMPTY OR NULL")
        } else {
            DataHandler.SUCCESS(list)
        }
    }



    fun insertIntoUser(userTable: UserTable) {
        viewModelScope.launch {
            dbRepository.insertIntoUser(userTable)
        }
    }

    fun deleteUser(userTable: UserTable) {
        viewModelScope.launch {

            dbRepository.delete(userTable)
        }
    }

    fun getAllUser() = dbRepository.getAllUser()


}