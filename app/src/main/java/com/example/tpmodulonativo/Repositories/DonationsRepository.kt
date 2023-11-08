package com.example.tpmodulonativo.Repositories

import android.content.ContentValues
import android.util.Log
import com.example.tpmodulonativo.Models.Donation
import com.example.tpmodulonativo.interfaces.IDonationsRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore


class DonationsRepository(val Store : FirebaseFirestore) : IDonationsRepository {

    override fun InsertDonation(newDonation: Donation){
        Store.collection("donaciones")
            .add(newDonation)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }

    override fun GetDonation(idDonation: String): Task<Donation> {
        val docRef = Store.collection("donaciones").document(idDonation)

        return docRef.get()
            .continueWith { task ->
                val result = task.result
                if (task.isSuccessful && result.exists()) {
                    val data = result.data
                    val id = result.id
                    val name = data?.get("name") as String
                    val description = data["description"] as String
                    val observations = data["observations"] as String
                    val imageUri = data["imageUri"] as String? // Puede ser nulo si la imagen no se especifica

                    val donation = Donation(id, name, description, observations, imageUri)
                    return@continueWith donation
                } else {
                    throw Exception("Documento no encontrado o error al obtenerlo.")
                }
            }
    }

    override fun GetUserDonations(idUser: String): List<Donation> {
        TODO("Not yet implemented")
    }
}