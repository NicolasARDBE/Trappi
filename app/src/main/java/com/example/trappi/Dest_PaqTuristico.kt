package com.example.trappi

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.trappi.adapters.AdapterPaquetes
import com.example.trappi.controllers.CarritoController
import com.example.trappi.model.entities.Plan

class Dest_PaqTuristico : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dest_paq_turistico)

        mostrarPlanes()
    }

    private fun mostrarPlanes() {
        // Aquí deberías obtener la lista de planes desde donde sea que la tengas almacenada
        val planes: List<Plan> = obtenerListaPlanes()

        val adapter = AdapterPaquetes(this, planes)
        val lista_planes = findViewById<ListView>(R.id.listStar)
        lista_planes.adapter = adapter
    }

    private fun obtenerListaPlanes(): List<Plan> {
        // TODO:  El Plan es el CARRITO, no existe una LISTA DE PLANES
        val carritoJson = CarritoController.getCarrito(assets) // Obtener el JSON del carrito
        val planes = mutableListOf<Plan>()

        // Si el carrito no está vacío, convertir el JSON del carrito a objetos Plan
        if (carritoJson != null) {
            //val carrito = CarritoController.fromJson()
            //planes.add(carrito) // Agregar el carrito como un plan más
        }

        // Aquí puedes agregar más lógica para obtener otros planes, si es necesario

        return planes
    }
}