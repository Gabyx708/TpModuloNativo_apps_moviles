package com.example.tpmodulonativo.interfaces

import com.example.tpmodulonativo.Models.User

interface IUserRepository {
    fun InsertUser(NewUser: User): User
    fun GetUserById(IdUser : String) : User

}