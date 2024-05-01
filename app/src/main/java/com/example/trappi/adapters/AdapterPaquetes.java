package com.example.trappi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trappi.R;
import com.example.trappi.model.entities.Actividad;
import com.example.trappi.model.entities.Hospedaje;
import com.example.trappi.model.entities.Plan;
import com.example.trappi.model.entities.Vuelo;

import java.util.List;

public class AdapterPaquetes extends BaseAdapter {
    private final Context context;
    private final List<Plan> planes;

    public AdapterPaquetes(Context context, List<Plan> planes) {
        this.context = context;
        this.planes = planes;
    }

    @Override
    public int getCount() {
        int count = 0;
        for (Plan plan : planes) {
            count += plan.getVuelos().size() + (plan.getHospedajes() != null ? plan.getHospedajes().size() : 0) + plan.getActividades().size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        int currentIndex = position;

        for (Plan plan : planes) {
            int itemCount = plan.getVuelos().size() + (plan.getHospedajes() != null ? plan.getHospedajes().size() : 0) + plan.getActividades().size();
            if (currentIndex < itemCount) {
                if (currentIndex < plan.getVuelos().size()) {
                    return plan.getVuelos().get(currentIndex);
                } else if (currentIndex < plan.getVuelos().size() + (plan.getHospedajes() != null ? plan.getHospedajes().size() : 0)) {
                    return plan.getHospedajes().get(currentIndex - plan.getVuelos().size());
                } else {
                    return plan.getActividades().get(currentIndex - plan.getVuelos().size() - (plan.getHospedajes() != null ? plan.getHospedajes().size() : 0));
                }
            } else {
                currentIndex -= itemCount;
            }
        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.paquetes_image, parent, false);
        }

        TextView actividad = view.findViewById(R.id.txtName);
        TextView precio = view.findViewById(R.id.anotherText);
        TextView destino = view.findViewById(R.id.location);

        Object item = getItem(position);

        if (item instanceof Vuelo) {
            Vuelo vuelo = (Vuelo) item;
            actividad.setText("Vuelo: " + vuelo.getNumeroVuelo());
            destino.setText("Destino: " + vuelo.getLugarDestino());
            precio.setText("Precio: " + vuelo.getPrecio());
        } else if (item instanceof Hospedaje) {
            Hospedaje hospedaje = (Hospedaje) item;
            actividad.setText("Hospedaje: " + hospedaje.getNombre());
            destino.setText("Destino: " + hospedaje.getDestinoId());
            precio.setText("Precio por noche: " + hospedaje.getPrecioNoche());
        } else if (item instanceof Actividad) {
            Actividad actividadObj = (Actividad) item;
            actividad.setText("Actividad: " + actividadObj.getNombre());
            destino.setText("Destino: " + actividadObj.getDestinoId());
            precio.setText("Precio: " + actividadObj.getPrecio());
        }

        return view;
    }
}