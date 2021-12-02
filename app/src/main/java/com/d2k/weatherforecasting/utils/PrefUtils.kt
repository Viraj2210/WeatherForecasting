package com.d2k.weatherforecasting.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

@SuppressLint("CommitPrefEdits")
class PrefUtils(val context: Context) {

    var pref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    var _context: Context? = null

    var PRIVATE_MODE = 0

    // Shared preferences file name
    private val PREF_NAME = context.applicationInfo.loadLabel(context.packageManager).toString()

    init {
        _context = context
        pref = _context!!.getSharedPreferences(
            PREF_NAME,
            PRIVATE_MODE
        )
        editor = pref!!.edit()

    }

    // shared pref mode


    fun setBoolean(PREF_NAME: String?, `val`: Boolean?) {
        editor!!.putBoolean(PREF_NAME, `val`!!)
        editor!!.commit()
    }

    fun setString(PREF_NAME: String?, VAL: String?) {
        editor!!.putString(PREF_NAME, VAL)
        editor!!.commit()
    }

    fun setInt(PREF_NAME: String?, VAL: Int) {
        editor!!.putInt(PREF_NAME, VAL)
        editor!!.commit()
    }

    fun getBoolean(PREF_NAME: String?): Boolean {
        return pref!!.getBoolean(PREF_NAME, false)
    }

    fun remove(PREF_NAME: String?) {
        if (pref!!.contains(PREF_NAME)) {
            editor!!.remove(PREF_NAME)
            editor!!.commit()
        }
    }

    fun getString(PREF_NAME: String): String? {
        if (pref!!.contains(PREF_NAME)) {
            return pref!!.getString(PREF_NAME, null)
        } else {
            return null
        }
    }

    fun getInt(key: String?): Int {
        return pref!!.getInt(key, 0)
    }

//    fun isUserLoggedIn() = pref?.getBoolean(Constants.PREF_IS_USER_LOGGED_IN, false)

/*
    fun getUserData(): UserRegisterationModel.User? {
        if (isUserLoggedIn()!!){
            val user = pref?.getString(Constants.PREF_USER,null)
            val userModel = Gson().fromJson(user, UserRegisterationModel.User::class.java)
            return userModel
        }else{
            return null
        }

    }*/

    fun logout() = pref?.edit()?.clear()?.apply()
}