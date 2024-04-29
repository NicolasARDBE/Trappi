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
import com.example.trappi.model.entities.Vuelo

class CarritoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCarritoBinding
    private lateinit var adapter: CarritoItemAdapter
    var carrito = ArrayList<Vuelo>()
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
            val carrito_json = assets.open(CarritoController.CARRITO_SAMPLE).bufferedReader().use {
                it.readText()
            }
            val plan = CarritoController.fromJson(carrito_json)
            Log.i("Carrito",plan.toString())
//            val sharedPref: SharedPreferences = applicationContext.getSharedPreferences("carrito", MODE_PRIVATE)
//            val carritoString = sharedPref.getString("carrito", null)
        }
        catch (e: Exception) {
            carrito = ArrayList()
            Log.e("Carrito", e.toString())
        }
        setupRecyclerView()
    }



    fun setupRecyclerView() {
        binding.rvListaCarro.layoutManager = LinearLayoutManager(this)
        adapter = CarritoItemAdapter(binding.tvTotal, carrito)
        binding.rvListaCarro.adapter = adapter
    }
}