package com.example.taskbookproyecto.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskbookproyecto.Entidades.Actividad;
import com.example.taskbookproyecto.R;

import java.util.ArrayList;

public class AdapterActividad extends RecyclerView.Adapter<AdapterActividad.ViewHolder> implements View.OnClickListener {
    LayoutInflater inflater;
    ArrayList<Actividad> model;

    //Listener
    private  View.OnClickListener listener;


    public AdapterActividad(Context context, ArrayList<Actividad> model){
    this.inflater = LayoutInflater.from(context);
    this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listas_actividades, parent, false);
         view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void  setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String nombre = model.get(position).getNombre();
        String fecha = model.get(position).getFecha();
        String descripcion = model.get(position).getDescripcion();
        int imagen = model.get(position).getImagenId();

        holder.nombres.setText(nombre);
        holder.fechalimite.setText(fecha);
        holder.descripcion.setText(descripcion);
        holder.imagen.setImageResource(imagen);

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View v) {

        if(listener != null){
            listener.onClick(v);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombres,  fechalimite, descripcion;
        ImageView imagen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombres = itemView.findViewById(R.id.nombre_actividad);
            fechalimite = itemView.findViewById(R.id.fecha_limite);
            descripcion = itemView.findViewById(R.id.txtDescripcion);
            imagen = itemView.findViewById(R.id.imagenpersona);

        }
    }

}
