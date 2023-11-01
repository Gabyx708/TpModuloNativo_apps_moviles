package com.example.tpmodulonativo.interfaces

import com.example.tpmodulonativo.Models.User

interface ICreateUserHandler {
    fun registerUser(newUser : User) //registra usuario
}