package com.example.andre_000.mrservice;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by andre_000 on 11/24/2015.
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    private List<Servicios> items;

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item

        public TextView Fecha;
        public TextView Hora;
        public TextView OrigenText;
        public TextView DestinoText;

        public ServiceViewHolder(View v) {
            super(v);

            Fecha = (TextView) v.findViewById(R.id.Fecha);
            Hora = (TextView) v.findViewById(R.id.Hora);
            OrigenText = (TextView) v.findViewById(R.id.OrigenText);
            DestinoText = (TextView) v.findViewById(R.id.DestinoText);
        }
    }

    public ServiceAdapter(List<Servicios> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.servicescard, viewGroup, false);


        View row = viewGroup;
        final int position = i;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceDetails.indiceSeleccionado = position; // Aqui al hacer click obtengo el indice, creo que deberia funcionar
                Intent i = new Intent(viewGroup.getContext(), ServiceDetails.class);
                viewGroup.getContext().startActivity(i);

            }
        });


        return new ServiceViewHolder(v);
    }

    public String FechayHora(String date){
        String[] split = date.split(" ");
        String fecha = split[0];
        String hora = split[1];
        return date;
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder viewHolder, int i) {
        String Fecha, Hora;
        String [] split = items.get(i).created_at.split(" ");
        Fecha = split[0];
        Hora = split[1];

        viewHolder.Fecha.setText(Fecha);
        viewHolder.Hora.setText(Hora);
        viewHolder.OrigenText.setText("Desde: "+String.valueOf(items.get(i).origen));
        viewHolder.DestinoText.setText("Hasta:"+String.valueOf(items.get(i).destino));


    }
}