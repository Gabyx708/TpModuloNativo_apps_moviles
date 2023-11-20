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

    override fun GetUserByEmail(email: String): User? {
        var user: User? = null

        // Realiza la consulta en la colección "usuarios" filtrando por el campo "email"
        Store.collection("usuarios")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                // Si la consulta tiene resultados, obtén el primer documento (debería haber solo uno)
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]

                    // Crea un objeto User a partir de los datos del documento
                    user = document.toObject(User::class.java)
                    Log.d(TAG, "User found: $user")
                } else {
                    Log.d(TAG, "No user found with email: $email")
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error getting user by email", e)
            }

        return user
    }

}

