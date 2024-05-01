package com.example.trappi.model.entities

class Plan(id: String, userId: String, vuelos: MutableList<Vuelo>, hospedajes: MutableList<Hospedaje>, actividades: MutableList<Actividad>){
    val id: String
    val userId: String
    val vuelos: MutableList<Vuelo>
    val hospedajes: MutableList<Hospedaje>?
    val actividades: MutableList<Actividad>

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
