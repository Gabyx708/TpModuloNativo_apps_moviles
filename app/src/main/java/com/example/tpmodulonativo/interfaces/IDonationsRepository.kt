package com.example.tpmodulonativo.interfaces

import com.example.tpmodulonativo.Models.Donation
import com.google.android.gms.tasks.Task

interface IDonationsRepository {

    fun InsertDonation(donation: Donation)
    fun GetUserDonations(idUser:String) : List<Donation>
    fun GetDonation(idDonation: String): Task<Donation>
    fun GetAllDonations(): Task<List<Donation>>
}