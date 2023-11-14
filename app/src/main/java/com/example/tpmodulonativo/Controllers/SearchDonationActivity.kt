package com.example.tpmodulonativo.Controllers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tpmodulonativo.R
import com.example.tpmodulonativo.Repositories.DonationsRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class SearchDonationActivity : AppCompatActivity() {
    private val db = Firebase.firestore
    private val repository = DonationsRepository(db)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_donation)
    }
}