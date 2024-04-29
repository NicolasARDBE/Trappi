package com.example.trappi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.trappi.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.core.View

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        mAuth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            // El usuario está autenticado, puedes a cceder a sus datos
            val email = currentUser.email
            binding.userName.text = email
        } else {
            // No hay usuario autenticado
            println("No hay usuario autenticado")
        }


        binding.empezar.setOnClickListener{
            startActivity(Intent(baseContext, Preferencias::class.java))
        }
        binding.logOut.setOnClickListener{
            cerrarSesion()
            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }
    }
    private fun cerrarSesion(){
        val sharedPreferences = getSharedPreferences("mi_app_pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        mAuth.signOut()
    }
}