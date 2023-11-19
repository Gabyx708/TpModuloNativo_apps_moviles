package com.example.tpmodulonativo.Repositories

import android.content.Context

class Preferences(val context:Context){

    val SHARED_NAME = "user_data"
    val SHARED_USER_NAME = "user_name"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveName(name:String){
        storage.edit().putString(SHARED_USER_NAME,name).apply()
    }

    fun getName():String{
        return storage.getString(SHARED_USER_NAME,"default_name")!!
    }
}