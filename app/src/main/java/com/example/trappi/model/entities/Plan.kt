package com.example.trappi.model.entities

class Plan(id: String, userId: String, vuelos: List<Vuelo>, hospedajes: List<Hospedaje>, actividades: List<Actividad>){
    val id: String
    val userId: String
    val vuelos: MutableList<Vuelo>
    val hospedajes: MutableList<Hospedaje>?
    val actividades: MutableList<Actividad>

    init {
        this.id = id
        this.userId = id
        this.vuelos = vuelos.toMutableList()
        this.hospedajes = hospedajes.toMutableList()
        this.actividades = actividades.toMutableList()
    }

    fun getPrecioTotal(): Double{
        return this.vuelos.sumOf { it.precio } + this.actividades.sumOf { it.precio}
    }

    override fun toString(): String {
        return "Plan(id='$id', userId='$userId', vuelos=$vuelos, hospedajes=$hospedajes, actividades=$actividades)"
    }


}
