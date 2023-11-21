package com.example.tpmodulonativo.Models

import GeoPoint

data class Donation(
    val id: String,
    val name: String,
    val description: String,
    val observations: String,
    val imageUri: String?,
    var estado: Boolean,
    val ubication: GeoPoint,
    val user: String
) {
    constructor(name: String, description: String, observations: String, imageUri: String?, estado: Boolean, ubication: GeoPoint, user: String) :
            this("", name, description, observations, imageUri, estado, ubication, user)
}
