package com.example.tpmodulonativo.Repositories

import GeoPoint
import android.content.ContentValues.TAG
import android.util.Log
import com.example.tpmodulonativo.Models.User
import com.example.tpmodulonativo.interfaces.IUserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

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

    override fun GetUserByEmail(email: String): Task<User> {
        val docRef = Store.collection("usuarios").whereEqualTo("email", email).limit(1)

        return docRef.get()
            .continueWith { task ->
                val result = task.result

                if (task.isSuccessful && result != null && !result.isEmpty) {
                    val document = result.documents[0]

                    val name = document.getString("name") ?: ""
                    val lastname = document.getString("apellido") ?: ""
                    val emailUser = document.getString("email") ?: ""
                    val nickname = document.getString("nickname")?:""
                    val birthday = Date()
                    val latitude = document.getDouble("latitude") ?: 0.0
                    val longitude = document.getDouble("longitude") ?: 0.0
                    val geoPoint = GeoPoint(latitude, longitude)

                    val user = User(nickname,name,lastname,emailUser,birthday,geoPoint,"")
                    user
                } else {
                    throw Exception("Documento no encontrado o error al obtenerlo.")
                }
            }
    }





}

