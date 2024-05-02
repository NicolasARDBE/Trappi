package com.example.trappi.controllers

import android.content.res.AssetManager
import android.util.Log
import com.example.trappi.model.entities.Actividad
import com.example.trappi.model.entities.Hospedaje
import com.example.trappi.model.entities.Plan
import com.example.trappi.model.entities.Vuelo
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

object CarritoController {

    var CARRITO_SAMPLE = "carrito_sample.json"

    fun agregarHotel(producto: String) {
        // Agregar producto al carrito
    }

    fun agregarVuelo(producto: String) {
        // Agregar producto al carrito
    }

    fun eliminarVuelo(producto: String) {
        // Eliminar producto del carrito
    }

    fun getCarrito(assets: AssetManager): Plan {
        try {
            val carrito_json = assets.open(CARRITO_SAMPLE).bufferedReader().use {
                it.readText()
            }
            return fromJson(carrito_json)
        } catch (e: Exception) {
            Log.e("[CarritoController] Error getCarrito", e.toString())
        }
        // Obtener productos del carrito
        return Plan(
            id = "",
            userId = "",
            vuelos = mutableListOf(),
            hospedajes = mutableListOf(),
            actividades = mutableListOf()
        )



    }

    fun fromJson(json: String): Plan {
        try {
            val jsonObject = JSONObject(json)
            val id = jsonObject.getString("id")
            val userId = jsonObject.getString("userId")
            val vuelos = fromJsonVuelos(jsonObject.getJSONArray("vuelos"))
            val hospedajes = fromJsonHospedajes(jsonObject.getJSONArray("hospedajes"))
            val actividades = fromJsonActividades(jsonObject.getJSONArray("actividades"))
            // Convertir JSON a Plan
            return Plan(
                id = id,
                userId = userId,
                vuelos = vuelos,
                hospedajes = hospedajes,
                actividades = actividades
            )
        } catch (e: JSONException) {
            Log.e("[CarritoController]", "Error al convertir JSON a Plan: "+e.toString())
        }
        // Convertir JSON a Plan
        return Plan(
            id = "",
            userId = "",
            vuelos = mutableListOf(),
            hospedajes = mutableListOf(),
            actividades = mutableListOf()
        )
    }

    private fun fromJsonVuelos(array_vuelos: JSONArray): MutableList<Vuelo> {
        val vuelos = mutableListOf<Vuelo>();
        for (i in 0 until array_vuelos.length()) {
            val vuelo = array_vuelos.getJSONObject(i)
            vuelos.add(
                Vuelo(
                    id = vuelo.getString("id"),
                    numeroVuelo = vuelo.getString("numeroVuelo"),
                    lugarOrigen = vuelo.getString("lugarOrigen"),
                    lugarDestino = vuelo.getString("lugarDestino"),
                    fechaSalida = stringToDate(vuelo.getString("fechaSalida")),
                    aerolineaId = vuelo.getString("aerolineaId"),
                    precio = vuelo.getDouble("precio")
                )
            )
        }
        return vuelos
    }

    private fun fromJsonHospedajes(array_hospedajes: JSONArray): MutableList<Hospedaje> {
        val hospedajes = mutableListOf<Hospedaje>();
        for (i in 0 until array_hospedajes.length()) {
            val hospedaje = array_hospedajes.getJSONObject(i)
            hospedajes.add(
                Hospedaje(
                    id = hospedaje.getString("id"),
                    nombre = hospedaje.getString("nombre"),
                    estrellas = hospedaje.getString("estrellas"),
                    precioNoche = hospedaje.getDouble("precioNoche"),
                    destinoId = hospedaje.getString("destinoId"),
                )
            )
        }
        return hospedajes
    }

    private fun fromJsonActividades(array_actividades: JSONArray): MutableList<Actividad> {
        val actividades = mutableListOf<Actividad>()
        for (i in 0 until array_actividades.length()) {
            val actividad = array_actividades.getJSONObject(i)
            actividades.add(
                Actividad(
                    id = actividad.getString("id"),
                    nombre = actividad.getString("nombre"),
                    destinoId = actividad.getString("destinoId"),
                    fecha = stringToDate(actividad.getString("fecha")),
                    precio = actividad.getDouble("precio"),
                    estrellas = (actividad.getDouble("estrellas")).toInt(),
                    tipo = actividad.getString("tipo")
                )
            )
        }
        return actividades
    }

    fun toJson(plan: Plan): String {
        val jsonObject = JSONObject()
        jsonObject.put("id", plan.id)
        jsonObject.put("userId", plan.userId)
        jsonObject.put("vuelos", plan.vuelos)
        jsonObject.put("hospedajes", plan.hospedajes)
        jsonObject.put("actividades", plan.actividades)
        // Convertir Plan a JSON
        return jsonObject.toString()
    }

    private fun stringToDate(date: String): Date {
        try {
            return SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(date)!!
        } catch (e: Exception) {
            Log.e("[CarritoController]", "Error al convertir String a Date")
        }
        // Mon Apr 29 06:33:30 GMT 2024
        return Date()
    }
}