package com.example.trappi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.trappi.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    companion object {
        const val FIREBASE_URL = "https://trappi-d3ac6.firebaseapp.com"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.register.setOnClickListener {
            startActivity(Intent(baseContext, RegisterActivity::class.java))
        }
        binding.login.setOnClickListener{
            signIn(binding.username.text.toString(), binding.pass.text.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser!=null) {
            val i = Intent(this, HomeActivity::class.java)
            i.putExtra("email", currentUser.email.toString())
            startActivity(i)
        }
    }


    private fun signIn(email:String, password:String){
        if(validEmailAddress(email) && password!=null && validateForm(email, password)){
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful){
                    updateUI(auth.currentUser)
                }else{
                    val message = it.exception!!.message
                    binding.username.text.clear()
                    binding.pass.text.clear()
                }
            }
        }
    }

    private fun validateForm(email : String, password: String) : Boolean {
        var valid = false
        if (email.isEmpty()) {
            binding.username.error = "Requerido!"
        } else if (!validEmailAddress(email)) {
            binding.username.error = "Dirección de correo inváñida"
        } else if (password.isEmpty()) {
            binding.pass.error = "Requerido!"
        } else if (password.length < 6){
            binding.pass.error = ("La contraseña debe tener mínimo 6 caracteres!")
        }else {
            valid = true
        }
        return valid
    }

    private fun validEmailAddress(email:String):Boolean{
        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(regex.toRegex())
    }

}