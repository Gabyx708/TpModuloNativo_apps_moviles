package com.example.tpmodulonativo.Repositories

import GeoPoint
import android.content.Context

class Preferences(val context:Context){

    val SHARED_NAME = "user_data"
    val SHARED_USER_NAME = "user_name"
    val SHARED_USER_MAIL = "user_mail"
    val SHARED_USER_LATITUDE = "user_latitude"
    val SHARED_USER_LONGITUDE = "user_longitude"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveName(name:String){
        storage.edit().putString(SHARED_USER_NAME,name).apply()
    }

    fun getName():String{
        return storage.getString(SHARED_USER_NAME,"default_name")!!
    }

    fun saveMail(mail:String){
        storage.edit().putString(SHARED_USER_MAIL,mail).apply()
    }

    fun getMail():String{
        return storage.getString(SHARED_USER_MAIL,"default_mail")!!
    }

    fun saveLocation(latitude:Double,longitude:Double){
        storage.edit().putLong(SHARED_USER_LATITUDE, latitude.toLong()).apply()
        storage.edit().putLong(SHARED_USER_LONGITUDE, longitude.toLong()).apply()
    }

    fun getLocation():GeoPoint{
        val latitude = storage.getLong(SHARED_USER_LATITUDE, 34.343434.toLong())!!
        val longitude = storage.getLong(SHARED_USER_LONGITUDE, 32.343434.toLong())!!
        return GeoPoint(latitude.toDouble(),longitude.toDouble())
    }
}