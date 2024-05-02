package com.example.trappi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trappi.adapters.CarritoActividadItemAdapter
import com.example.trappi.adapters.CarritoHospedajeItemAdapter
import com.example.trappi.adapters.CarritoVuelosItemAdapter
import com.example.trappi.controllers.CarritoController
import com.example.trappi.databinding.ActivityCarritoBinding
import com.example.trappi.model.entities.Actividad
import com.example.trappi.model.entities.Hospedaje
import com.example.trappi.model.entities.Plan
import com.example.trappi.model.entities.Vuelo

class CarritoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCarritoBinding
    private lateinit var adapter: CarritoVuelosItemAdapter
    private lateinit var actividadAdapter: CarritoActividadItemAdapter
    private lateinit var hospedajeAdapter: CarritoHospedajeItemAdapter
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
        setupRecyclerViewVuelos()
        setupRecyclerViewActividad()
        setupRecyclerViewHospedaje()
        binding.CheckOutButton.setOnClickListener {
            val intent = Intent(this, CheckOut::class.java)
            startActivity(intent)
        }

    }


    fun setupRecyclerViewVuelos() {
        binding.rvListaCarro.layoutManager = LinearLayoutManager(this)
        adapter = CarritoVuelosItemAdapter(binding.tvTotal, ArrayList(carrito.vuelos))
        binding.rvListaCarro.adapter = adapter
    }
    fun setupRecyclerViewActividad() {
        binding.rvListaActividadesCarro.layoutManager = LinearLayoutManager(this)
        actividadAdapter = CarritoActividadItemAdapter(binding.tvTotal, ArrayList(carrito.actividades))
        binding.rvListaActividadesCarro.adapter = actividadAdapter

    }
    fun setupRecyclerViewHospedaje() {
        binding.rvListaHospedajeCarro.layoutManager = LinearLayoutManager(this)
        hospedajeAdapter = CarritoHospedajeItemAdapter(binding.tvTotal, ArrayList(carrito.hospedajes))
        binding.rvListaHospedajeCarro.adapter = hospedajeAdapter

    }
}