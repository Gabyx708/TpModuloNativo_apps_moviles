package com.example.tpmodulonativo.interfaces

import android.content.Context
import com.google.firebase.firestore.GeoPoint

interface IGeoManager {
    fun getUserLocation(context: Context): GeoPoint?
}