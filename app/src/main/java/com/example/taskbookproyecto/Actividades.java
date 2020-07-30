package com.example.taskbookproyecto;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskbookproyecto.Adaptadores.AdapterActividad;
import com.example.taskbookproyecto.Entidades.Actividad;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.taskbookproyecto.CargarEnBaseDatos.cargarDatosFirebase;


public class Actividades extends Fragment {


    AdapterActividad adapterActividad;
    RecyclerView recyclerViewActividades;
    ArrayList<Actividad> listaActividad;
     EditText edtFecha, edtNombre;
     DialogFragment dialogFragment;
     CargarEnBaseDatos cargar;
        String nombre,fecha, descripcion;
        int imagen;
    FloatingActionButton floatingActionButton;
    public String datousuario;




    public Actividades() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_actividades, container, false);
        recyclerViewActividades = view.findViewById(R.id.recyclerView);




        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.añadir);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder =  new AlertDialog.Builder(getActivity());
                final View mview = getLayoutInflater().inflate(R.layout.dialog_crear_actividad,null);
                 edtNombre = (EditText) mview.findViewById(R.id.edtNombreActividad);

                edtFecha = (EditText) mview.findViewById(R.id.edtFechaActividad);
               edtFecha.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       showDataPickerDialog();
                   }
               });
                final EditText edtDescripcion = (EditText) mview.findViewById(R.id.edtDescripcionActividad);
                Button btnCrearActividad = (Button) mview.findViewById(R.id.btnDialogCrear);
                final Button btnCancelar = (Button) mview.findViewById(R.id.btnDialogCancelar);

                btnCrearActividad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Creara una actividad", Toast.LENGTH_SHORT).show();


                        nombre = edtNombre.getText().toString();
                        fecha = edtFecha.getText().toString();
                        descripcion = edtDescripcion.getText().toString();

                       cargarDatosFirebase(nombre,fecha,descripcion,15);

                    }
                });

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });

                mBuilder.setView(mview);
                AlertDialog dialog =mBuilder.create();
                dialog.show();



            }
        });




        listaActividad = new ArrayList<>();
        //cargar la lista
        cargarLista();

        //mostrar datos

        mostrarData();
        // Inflate the layout for this fragment
        return view;
    }

    public void showDataPickerDialog(){
        //DataPickerFragment newFragment1 =  new DataPickerFragment();
        DataPickerFragment newFragment = DataPickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final String selectedDate = dayOfMonth + " / " + (month+1) +" / " + year;
                edtFecha.setText(selectedDate);
            }
        });


        newFragment.show(getActivity().getSupportFragmentManager(),"view");


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






        /*

        AlertDialog inciial
        final AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        final EditText  input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setMessage(" Nombre de la actividad nueva").
                setPositiveButton("Agregar", new DialogInterface.OnClickListener() {

                    Intent intent = new Intent(getActivity(),MainActivity.class);


                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }


                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
        */



}