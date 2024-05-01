package com.example.trappi

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.trappi.adapters.AdapterPaquetes
import com.example.trappi.controllers.PlanManager

class Dest_PaqTuristico : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dest_paq_turistico)

        mostrarPlanes()
    }

    private fun mostrarPlanes() {
        PlanManager.instance.fillListOfPlanes(this)
        val adapter = AdapterPaquetes(this, PlanManager.instance.listaPlanes)
        val listaPlanes = findViewById<ListView>(R.id.listStar)
        listaPlanes.adapter = adapter
    }
}
