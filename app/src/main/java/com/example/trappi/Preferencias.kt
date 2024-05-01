package com.example.trappi

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import com.example.trappi.databinding.ActivityPreferenciasBinding
import com.example.trappi.model.objects.DataPreferencias
import java.util.Calendar

class Preferencias : AppCompatActivity() {
    private lateinit var binding: ActivityPreferenciasBinding
    private val pressedButtons = mutableSetOf<Int>()
    private val preferenciasAux = mutableListOf<String>()
    var preferenciasPrueba = mutableListOf<String>()
    var fechaSalidaCheck = false
    var fechaLlegadaCheck = false
    var personasCheck = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)
        binding = ActivityPreferenciasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.FechaSalida.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year1, monthOfYear, dayOfMonth ->
                if (year1 < year || (year1 == year && monthOfYear < month) || (year1 == year && monthOfYear == month && dayOfMonth < day)) {
                    Toast.makeText(this, "La fecha de salida no puede ser anterior a la fecha actual", Toast.LENGTH_SHORT).show()
                    binding.FechaSalidaText.text = ""
                    binding.FechaSalidaText.hint = "00/00/0000"
                    binding.FechaSalida.setBackgroundColor(Color.parseColor("#747679"))
                    fechaSalidaCheck = false
                } else {
                    binding.FechaSalidaText.text = "" + dayOfMonth + "/" + monthOfYear + "/" + year1
                    DataPreferencias.year1 = year1
                    DataPreferencias.month1 = monthOfYear
                    DataPreferencias.day1 = dayOfMonth
                    binding.FechaSalida.setBackgroundColor(Color.parseColor("#4CAF50"))
                    fechaSalidaCheck = true
                }
            }, year, month, day)
            dpd.show()
        }
        binding.FechaLlegada.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                if(DataPreferencias.year1==0){
                    Toast.makeText(this, "Por favor, selecciona primero la fecha de salida", Toast.LENGTH_SHORT).show()
                    binding.FechaLlegadaText.text = ""
                    binding.FechaLlegadaText.hint = "00/00/0000"
                    return@OnDateSetListener
                }else{
                    if (year < DataPreferencias.year1 || (year == DataPreferencias.year1 && monthOfYear < DataPreferencias.month1) || (year == DataPreferencias.year1 && monthOfYear == DataPreferencias.month1 && dayOfMonth <= DataPreferencias.day1)) {
                        Toast.makeText(this, "La fecha de llegada no puede ser anterior o en la fecha de salida", Toast.LENGTH_SHORT).show()
                        binding.FechaLlegadaText.text = ""
                        binding.FechaLlegadaText.hint = "00/00/0000"
                        binding.FechaLlegada.setBackgroundColor(Color.parseColor("#747679"))
                        fechaLlegadaCheck = false
                    } else {
                        binding.FechaLlegadaText.text = "" + dayOfMonth + "/" + monthOfYear + "/" + year
                        DataPreferencias.year2 = year
                        DataPreferencias.month2 = monthOfYear
                        DataPreferencias.day2 = dayOfMonth
                        binding.FechaLlegada.setBackgroundColor(Color.parseColor("#4CAF50"))
                        fechaLlegadaCheck = true
                    }
                }
            }, year, month, day)
            dpd.show()
        }
        binding.Personas.setOnClickListener {
            val numberPicker = NumberPicker(this)
            numberPicker.minValue = 0
            numberPicker.maxValue = 10
            numberPicker.wrapSelectorWheel = false
            numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                binding.PersonasText.text = newVal.toString()
                if (newVal == 0) {
                    binding.PersonasText.text = ""
                    binding.PersonasText.hint = "0"
                    binding.Personas.setBackgroundColor(Color.parseColor("#747679"))
                    personasCheck = false
                    return@setOnValueChangedListener
                }else{
                    DataPreferencias.personas = newVal
                    binding.Personas.setBackgroundColor(Color.parseColor("#4CAF50"))
                    personasCheck = true
                }
            }
            val builder = AlertDialog.Builder(this).setView(numberPicker)
            builder.setPositiveButton("Aceptar") { dialog, which -> }
            builder.show()
        }

        binding.Listo.setOnClickListener {
            if (fechaSalidaCheck && fechaLlegadaCheck && personasCheck && preferenciasAux.size >= 5) {
                Toast.makeText(this, "Preferencias guardadas", Toast.LENGTH_SHORT).show()
                DataPreferencias.preferencias = preferenciasAux
                Toast.makeText(this, DataPreferencias.preferencias.toString(), Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Dest_PaqTuristico::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Algun dato incompleto", Toast.LENGTH_SHORT).show()
            }
        }

        Refrescar()
        binding.Refrescar.setOnClickListener {
            Refrescar()
        }
        configurarBotones()

    }

    private fun configurarBotones() {
        val buttonClickListener = View.OnClickListener { view ->
            val button = view as Button
            val buttonId = button.id
            if (buttonId in pressedButtons) {

                button.setBackgroundColor(Color.parseColor("#747679"))
                pressedButtons.remove(buttonId)
                preferenciasAux.remove(button.text.toString())
            } else {
                button.setBackgroundColor(Color.parseColor("#4CAF50"))
                pressedButtons.add(buttonId)
                preferenciasAux.add(button.text.toString())
            }
        }
        binding.button1.setOnClickListener(buttonClickListener)
        binding.button2.setOnClickListener(buttonClickListener)
        binding.button3.setOnClickListener(buttonClickListener)
        binding.button4.setOnClickListener(buttonClickListener)
        binding.button5.setOnClickListener(buttonClickListener)
        binding.button6.setOnClickListener(buttonClickListener)
        binding.button7.setOnClickListener(buttonClickListener)
        binding.button8.setOnClickListener(buttonClickListener)
        binding.button9.setOnClickListener(buttonClickListener)

    }

    private fun Refrescar() {
        PreparaDatos()
        MostrarDatos()
    }

    private fun MostrarDatos() {
        assignPreferenceToButton(binding.button1)
        assignPreferenceToButton(binding.button2)
        assignPreferenceToButton(binding.button3)
        assignPreferenceToButton(binding.button4)
        assignPreferenceToButton(binding.button5)
        assignPreferenceToButton(binding.button6)
        assignPreferenceToButton(binding.button7)
        assignPreferenceToButton(binding.button8)
        assignPreferenceToButton(binding.button9)

    }

    private fun assignPreferenceToButton(button: Button) {
        val randomPreference = preferenciasPrueba.random()
        button.text = randomPreference

        if(preferenciasAux.contains(randomPreference)){
            button.setBackgroundColor(Color.parseColor("#4CAF50"))
            pressedButtons.add(button.id)
        }else{
            button.setBackgroundColor(Color.parseColor("#747679"))
            pressedButtons.remove(button.id)
        }

        preferenciasPrueba.remove(randomPreference)
    }

    private fun PreparaDatos() {
        preferenciasPrueba.clear()
        preferenciasPrueba.add("Relajación")
        preferenciasPrueba.add("Aventura")
        preferenciasPrueba.add("Cultura")
        preferenciasPrueba.add("Gastronomía")
        preferenciasPrueba.add("Naturaleza")
        preferenciasPrueba.add("Historia")
        preferenciasPrueba.add("Senderismo")
        preferenciasPrueba.add("Playa")
        preferenciasPrueba.add("Montaña")
        preferenciasPrueba.add("Ciudad")
        preferenciasPrueba.add("Rural")
        preferenciasPrueba.add("Lujo")
        preferenciasPrueba.add("Económico")
        preferenciasPrueba.add("Crucero")
        preferenciasPrueba.add("Safari")
        preferenciasPrueba.add("Trekking")
        preferenciasPrueba.add("Buceo")
        preferenciasPrueba.add("Esquí")
        preferenciasPrueba.add("Parapente")
        preferenciasPrueba.add("Camping")
        preferenciasPrueba.add("Carretera")
        preferenciasPrueba.add("Turismo ecológico")
        preferenciasPrueba.add("Viajes en tren")
        preferenciasPrueba.add("Festivales/eventos")
        preferenciasPrueba.add("Safaris")
        preferenciasPrueba.add("Vida nocturna")
        preferenciasPrueba.add("Asombro")
        preferenciasPrueba.add("Curiosidad")
        preferenciasPrueba.add("Adrenalina")
        preferenciasPrueba.add("Calma")
        preferenciasPrueba.add("Libertad")
        preferenciasPrueba.add("Romance")
        preferenciasPrueba.add("Desconexión")
        preferenciasPrueba.add("Paz")
        preferenciasPrueba.add("Atracciones")
        preferenciasPrueba.add("Extremo")
    }
}

