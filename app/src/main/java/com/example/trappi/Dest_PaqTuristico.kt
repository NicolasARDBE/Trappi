package com.example.trappi

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.trappi.adapters.AdapterPaquetes
import com.example.trappi.controllers.PlanManager
import com.example.trappi.databinding.ActivityDestPaqTuristicoBinding

class Dest_PaqTuristico : AppCompatActivity() {
    private lateinit var binding: ActivityDestPaqTuristicoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dest_paq_turistico)

        mostrarPlanes()

        binding.menu.setOnClickListener{
            startActivity(Intent(baseContext, MenuActivity::class.java))
        }
    }

    private fun mostrarPlanes() {
        PlanManager.instance.fillListOfPlanes(this)
        val adapter = AdapterPaquetes(this, PlanManager.instance.listaPlanes)
        val listaPlanes = findViewById<ListView>(R.id.listStar)
        listaPlanes.adapter = adapter
    }
}
