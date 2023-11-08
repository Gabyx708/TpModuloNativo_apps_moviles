package com.example.tpmodulonativo.Controllers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tpmodulonativo.Models.Donation
import com.example.tpmodulonativo.R
import com.example.tpmodulonativo.Repositories.DonationsRepository
import com.example.tpmodulonativo.interfaces.ICreateDonations
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MakeDonationActivity : AppCompatActivity() , ICreateDonations{

    private val db = Firebase.firestore
    private val repository = DonationsRepository(db)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_donation)
    }

    override fun createDonation(newDotation: Donation) {
        repository.InsertDonation(newDotation)
    }
}