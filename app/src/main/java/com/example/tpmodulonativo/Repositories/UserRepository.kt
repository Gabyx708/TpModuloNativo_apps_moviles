package com.example.tpmodulonativo.Repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.example.tpmodulonativo.Models.User
import com.example.tpmodulonativo.interfaces.IUserRepository
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository(val Store : FirebaseFirestore) : IUserRepository {

    override fun InsertUser(NewUser: User): User {

        // agrega el usuario a la DB
        Store.collection("usuarios")
            .add(NewUser)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }


        return NewUser
    }

    override fun GetUserById(IdUser: String): User {
        TODO("Not yet implemented")
    }

}

