package com.example.trappi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.trappi.R
import com.example.trappi.model.entities.Plan

class AdapterPaquetes(private val context: Context, private val planes: List<Plan>) : BaseAdapter() {
    override fun getCount(): Int {
        return planes.size
    }

    override fun getItem(position: Int): Any {
        return planes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater
            .from(context).inflate(R.layout.paquetes_image, parent, false)

        val txtName = view.findViewById<TextView>(R.id.txtName)
        val anotherText = view.findViewById<TextView>(R.id.anotherText)
        val location = view.findViewById<TextView>(R.id.location)

        val plan = getItem(position) as Plan

        txtName.text = plan.actividades.firstOrNull()?.nombre
        anotherText.text = "$${plan.getPrecioTotal()}"
        location.text = plan.vuelos.firstOrNull()?.lugarDestino

        return view
    }
}