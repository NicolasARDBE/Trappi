package com.example.trappi.controllers

import android.app.Activity
import android.util.Log
import com.example.trappi.model.entities.Actividad
import com.example.trappi.model.entities.Hospedaje
import com.example.trappi.model.entities.Plan
import com.example.trappi.model.entities.Vuelo
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PlanManager {
    lateinit var planes: JSONArray
    var listaPlanes: MutableList<Plan> = mutableListOf()
    val jsonFile = "carrito_sample.json"

    companion object{
        val instance: PlanManager by lazy { PlanManager() }
    }

    fun loadJSONFromAsset(activity: Activity): String? {
        try {
            val assets = activity.assets
            val inputStream = assets.open(jsonFile)
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            return String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }

    fun getPlanes(activity: Activity): JSONArray {
        val json = JSONArray(loadJSONFromAsset(activity))
        return json
    }

    fun fillListOfPlanes(activity: Activity): MutableList<Plan> {
        try {
            val jsonArray = getPlanes(activity)
            val planes = mutableListOf<Plan>()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id: String = jsonObject.getString("id")
                val userId: String = jsonObject.getString("userId")
                val vuelosArray: JSONArray = jsonObject.getJSONArray("vuelos")
                val hospedajesArray: JSONArray? = jsonObject.optJSONArray("hospedajes")
                val actividadesArray: JSONArray = jsonObject.getJSONArray("actividades")

                val vuelos = parseVuelos(vuelosArray)
                val hospedajes = parseHospedajes(hospedajesArray)
                val actividades = parseActividades(actividadesArray)

                val plan = Plan(id = id, userId = userId, vuelos = vuelos, hospedajes = hospedajes, actividades = actividades)
                planes.add(plan)

                if(!isPlanAdded(id)){
                    listaPlanes.add(plan)
                }
            }
            return planes
        } catch (ex: Exception) {
            ex.printStackTrace()
            return mutableListOf()
        }
    }

    private fun parseVuelos(array: JSONArray): List<Vuelo> {
        val vuelos = mutableListOf<Vuelo>()
        for (i in 0 until array.length()) {
            val vueloObject = array.getJSONObject(i)
            vuelos.add(
                Vuelo(
                    id = vueloObject.getString("id"),
                    numeroVuelo = vueloObject.getString("numeroVuelo"),
                    lugarOrigen = vueloObject.getString("lugarOrigen"),
                    lugarDestino = vueloObject.getString("lugarDestino"),
                    fechaSalida = stringToDate(vueloObject.getString("fechaSalida")),
                    aerolineaId = vueloObject.getString("aerolineaId"),
                    precio = vueloObject.getDouble("precio")
                )
            )
        }
        return vuelos
    }

    private fun parseHospedajes(array: JSONArray?): List<Hospedaje> {
        val hospedajes = mutableListOf<Hospedaje>()
        array?.let {
            for (i in 0 until it.length()) {
                val hospedajeObject = it.getJSONObject(i)
                hospedajes.add(
                    Hospedaje(
                        id = hospedajeObject.getString("id"),
                        nombre = hospedajeObject.getString("nombre"),
                        estrellas = hospedajeObject.getString("estrellas"),
                        precioNoche = hospedajeObject.getString("precioNoche"),
                        destinoId = hospedajeObject.getString("destinoId")
                    )
                )
            }
        }
        return hospedajes
    }

    private fun parseActividades(array: JSONArray): List<Actividad> {
        val actividades = mutableListOf<Actividad>()
        for (i in 0 until array.length()) {
            val actividadObject = array.getJSONObject(i)
            actividades.add(
                Actividad(
                    id = actividadObject.getString("id"),
                    nombre = actividadObject.getString("nombre"),
                    destinoId = actividadObject.getString("destinoId"),
                    fecha = stringToDate(actividadObject.getString("fecha")),
                    precio = actividadObject.getDouble("precio"),
                    estrellas = actividadObject.getInt("estrellas"),
                    tipo = actividadObject.getString("tipo")
                )
            )
        }
        return actividades
    }

    private fun stringToDate(date: String): Date {
        try {
            return SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(date)!!
        } catch (e: Exception) {
            Log.e("CarritoController", "Error al convertir String a Date")
        }
        // Mon Apr 29 06:33:30 GMT 2024
        return Date()
    }

    fun getPlanById(id: String): Plan? {
        for (plan in listaPlanes) {
            if (plan.id == id) {
                return plan
            }
        }
        return null
    }

    fun isPlanAdded(id: String): Boolean {
        for (plan in listaPlanes) {
            if (plan.id == id) {
                return true
            }
        }
        return false
    }
}
