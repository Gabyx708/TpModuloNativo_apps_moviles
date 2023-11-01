package com.example.tpmodulonativo.interfaces

import com.example.tpmodulonativo.Models.User

interface ICreateUserHandler {
    fun CreateNewUser(newUser:User)
}