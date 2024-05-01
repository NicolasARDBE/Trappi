package com.example.trappi

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trappi.adapters.CarritoItemAdapter
import com.example.trappi.controllers.CarritoController
import com.example.trappi.databinding.ActivityCarritoBinding
import com.example.trappi.model.entities.Actividad
import com.example.trappi.model.entities.Hospedaje
import com.example.trappi.model.entities.Plan
import com.example.trappi.model.entities.Vuelo

class CarritoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCarritoBinding
    private lateinit var adapter: CarritoItemAdapter
    var carrito: Plan = Plan("Carrito", "user", ArrayList<Vuelo>(), ArrayList<Hospedaje>(), ArrayList<Actividad>())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarritoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        try {
            carrito = CarritoController.getCarrito(assets)
            Log.i("[Carrito]",carrito.toString())

//            val sharedPref: SharedPreferences = applicationContext.getSharedPreferences("carrito", MODE_PRIVATE)
//            val carritoString = sharedPref.getString("carrito", null)
        }
        catch (e: Exception) {
            Log.e("[Carrito]", e.toString())
        }
        setupRecyclerView()
    }



    fun setupRecyclerView() {
        binding.rvListaCarro.layoutManager = LinearLayoutManager(this)
        adapter = CarritoItemAdapter(binding.tvTotal, ArrayList(carrito.vuelos))
        binding.rvListaCarro.adapter = adapter
    }
}