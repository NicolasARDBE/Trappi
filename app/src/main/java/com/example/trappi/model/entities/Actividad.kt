package com.example.trappi.model.entities

import java.util.Date

data class Actividad (
    val id: String,
    val tipo: String,
    val nombre: String,
    val destinoId: String,
    val fecha: Date,
    val estrellas: Int,
    val precio: Double,
)