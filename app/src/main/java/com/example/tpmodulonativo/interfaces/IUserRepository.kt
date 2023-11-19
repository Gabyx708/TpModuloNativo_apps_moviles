package com.example.tpmodulonativo.interfaces

import com.example.tpmodulonativo.Models.User
import com.google.android.gms.tasks.Task

interface IUserRepository {
    fun InsertUser(NewUser: User): User
    fun GetUserById(IdUser : String) : User

    fun GetUserByEmail(email :String) : Task<User>

}