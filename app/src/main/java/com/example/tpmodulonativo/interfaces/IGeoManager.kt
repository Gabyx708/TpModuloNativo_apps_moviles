package com.example.tpmodulonativo.interfaces

import GeoPoint
import android.content.Context

interface IGeoManager {
    fun getUserLocation(context: Context): GeoPoint
}