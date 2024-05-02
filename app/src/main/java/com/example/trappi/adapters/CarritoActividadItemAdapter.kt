package com.example.trappi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trappi.R
import com.example.trappi.model.entities.Actividad
import com.example.trappi.model.entities.Vuelo

class CarritoActividadItemAdapter (
    var tvTotal: TextView,
    var carroCompras: ArrayList<Actividad>
): RecyclerView.Adapter<CarritoActividadItemAdapter.ViewHolder>() {

    var total: Double = 0.0

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvNomProducto = itemView.findViewById(R.id.tvNomProducto) as TextView
        val tvDescripcion = itemView.findViewById(R.id.tvDescripcion) as TextView
        val tvPrecio = itemView.findViewById(R.id.tvPrecio) as TextView
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.carrito_item, parent, false)

        try {
            total = tvTotal.text.toString().toDouble()
        }catch (e: Exception){
            total = 0.0
        }

        carroCompras.forEach {
            total += it.precio
        }

        tvTotal.text = "$total"

        return ViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = carroCompras[position]

        holder.tvNomProducto.text = producto.nombre
        holder.tvDescripcion.text = producto.tipo
        holder.tvPrecio.text = "${producto.precio}"
    }

    override fun getItemCount(): Int {
        return carroCompras.size
    }

}