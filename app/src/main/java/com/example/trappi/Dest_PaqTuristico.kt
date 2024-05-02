package com.example.trappi

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trappi.adapters.AdapterPaquetes
import com.example.trappi.controllers.PlanManager
import com.example.trappi.databinding.ActivityDestPaqTuristicoBinding

class Dest_PaqTuristico : AppCompatActivity() {
    private lateinit var binding: ActivityDestPaqTuristicoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDestPaqTuristicoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
