package com.example.tpmodulonativo.interfaces

import com.google.firebase.firestore.GeoPoint

interface IGeoManager {
    fun getUserLocation(): GeoPoint?
}