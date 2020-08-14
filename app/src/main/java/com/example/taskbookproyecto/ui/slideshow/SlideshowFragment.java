package com.example.taskbookproyecto.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskbookproyecto.Adaptadores.AdapterActividad;
import com.example.taskbookproyecto.Entidades.Actividad;
import com.example.taskbookproyecto.R;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    AdapterActividad adapterActividad;
    RecyclerView recyclerViewActividades;
    ArrayList<Actividad> listaActividad;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_slideshow, container, false);
        recyclerViewActividades = view.findViewById(R.id.recyclerView);


        listaActividad = new ArrayList<>();
        //cargar la lista
        cargarLista();

        //mostrar datos

        mostrarData();
        // Inflate the layout for this fragment

        return view;
    }

    public void cargarLista(){
        listaActividad.add(new Actividad("Rutina de Ejercicio","20/07/2020","Esta es la descripcion",R.drawable.ic_menu_share));
        listaActividad.add(new Actividad("Tarea de PS","05/07/2020","Esta es la descripcion",R.drawable.ic_menu_share));
        listaActividad.add(new Actividad("Tarea de IR","04/07/2020","Esta es la descripcion",R.drawable.ic_menu_share));
        listaActividad.add(new Actividad("Mas tarea de PS","03/07/2020","Esta es la descripcion",R.drawable.ic_menu_share));
        listaActividad.add(new Actividad("Tarea de Plataformas","09/07/2020","Esta es la descripcion",R.drawable.ic_menu_share));
        listaActividad.add(new Actividad("Salir a correr","06/07/2020","Esta es una descripcion",R.drawable.ic_menu_camera));
    }
    public void mostrarData(){
        recyclerViewActividades.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterActividad = new AdapterActividad(getContext(), listaActividad);
        recyclerViewActividades.setAdapter(adapterActividad);

        adapterActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = listaActividad.get(recyclerViewActividades.getChildAdapterPosition(v)).getNombre();
                Toast.makeText(getContext(), "Seleccionó: "+ nombre, Toast.LENGTH_SHORT).show();
            }
        });
    }

}