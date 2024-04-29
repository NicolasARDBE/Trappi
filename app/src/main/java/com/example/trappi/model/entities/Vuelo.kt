package com.example.trappi.model.entities

import java.util.Date

data class Vuelo(
    val id: String,
    val numeroVuelo: String,
    val aerolineaId: String,
    val lugarOrigen: String,
    val lugarDestino: String,
    val fechaSalida: Date,
    val precio: Double
)
