package com.example.tpmodulonativo.Models

data class Donation(
    val id: String,
    val name: String,
    val description: String,
    val observations: String,
    val imageUri: String?, // Puede ser nulo si la imagen no se especifica
    val estado: Boolean
    // Agrega otros campos según tu modelo de datos
) {
    // Constructor secundario para crear una Donation sin proporcionar un ID
    constructor(name: String, description: String, observations: String, imageUri: String?, estado: Boolean) :
            this("", name, description, observations, imageUri, estado)
}
