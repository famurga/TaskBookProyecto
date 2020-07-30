package com.example.taskbookproyecto.ui.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskbookproyecto.Adaptadores.AdapterActividad;
import com.example.taskbookproyecto.CargarEnBaseDatos;
import com.example.taskbookproyecto.DataPickerFragment;
import com.example.taskbookproyecto.Entidades.Actividad;
import com.example.taskbookproyecto.MainActivity;
import com.example.taskbookproyecto.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    AdapterActividad adapterActividad;
    RecyclerView recyclerViewActividades;
    ArrayList<Actividad> listaActividad;
    EditText edtFecha, edtNombre;
    DialogFragment dialogFragment;
    CargarEnBaseDatos cargar;
    String nombre,fecha;
    int imagen;
    FloatingActionButton floatingActionButton;
    DatabaseReference mDataBase;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewActividades = view.findViewById(R.id.recyclerView);

        recyclerViewActividades.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataBase = FirebaseDatabase.getInstance().getReference();


        listaActividad = new ArrayList<>();
        verificarExiste("karina");




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

    public  void getDatosFromFirebase(){

        mDataBase.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                       String descripcion = ds.child("Descripcion").getValue().toString();

                        String nombre = ds.child("nombre").getValue().toString();
                        String fecha = ds.child("fecha").getValue().toString();
                        listaActividad.add(new Actividad(nombre,fecha,descripcion,2));
                    }

                    adapterActividad= new AdapterActividad(getContext(),listaActividad);
                    recyclerViewActividades.setAdapter(adapterActividad);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public void verificarExiste(String dato){

        Query consulta = FirebaseDatabase.getInstance().getReference()
                .child("Usuarios").child("karina").child("Actividades").orderByChild("nombre").equalTo(dato);
        consulta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for( DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        Actividad actividad = dataSnapshot1.getValue(Actividad.class);
                        String nombre = actividad.getNombre();
                        String fecha = actividad.getFecha();
                        String Descripcion = actividad.getDescripcion();
                        int imagenId = actividad.getImagenId();

                        listaActividad.add(new Actividad(nombre,fecha,Descripcion,R.drawable.ic_menu_camera));

                    }
                    adapterActividad= new AdapterActividad(getContext(),listaActividad);
                    recyclerViewActividades.setAdapter(adapterActividad);




                }
                else{
                    Toast.makeText(getActivity(), "No se encuentra ese dato", Toast.LENGTH_SHORT).show();
                    Log.e("NombreUsuario:", "No se encuentra en la base de datos");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}