package com.example.eco_life

data class EmissionModel(
    val id: Int,
    val emissionFactor: Double,
    val emissionValue: Double,
    val emissionDate: String,
    val type: String,
    val hours: Double
)
