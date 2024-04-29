package com.example.trappi.model.entities

class Plan(id: String, userId: String, vuelos: List<Vuelo>, hospedajes: List<Hospedaje>, actividades: List<Actividad>){
    val id: String
    val userId: String
    val vuelos: List<Vuelo>
    val hospedajes: List<Hospedaje>?
    val actividades: List<Actividad>

    init {
        this.id = id
        this.userId = id
        this.vuelos = vuelos
        this.hospedajes = hospedajes
        this.actividades = actividades
    }

    fun getPrecioTotal(): Double{
        return this.vuelos.sumOf { it.precio } + this.actividades.sumOf { it.precio}
    }

    override fun toString(): String {
        return "Plan(id='$id', userId='$userId', vuelos=$vuelos, hospedajes=$hospedajes, actividades=$actividades)"
    }


}
