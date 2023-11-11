package com.example.tpmodulonativo.interfaces

import android.content.Context

interface ISessionUserHandler {

    fun signUp(user:String,password:String,context: Context) //iniciar sesion
    fun SignUpFail(message: String, context: Context) //majear errores de sesion
}