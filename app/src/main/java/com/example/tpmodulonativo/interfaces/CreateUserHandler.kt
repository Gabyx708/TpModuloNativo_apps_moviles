package com.example.tpmodulonativo.interfaces

import com.example.tpmodulonativo.Models.User

interface CreateUserHandler {
    fun CreateNewUser(newUser:User)
}