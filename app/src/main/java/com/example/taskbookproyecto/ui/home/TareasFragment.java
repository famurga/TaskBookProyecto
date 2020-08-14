package com.example.taskbookproyecto.ui.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
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
import com.example.taskbookproyecto.iComunicaFragments;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TareasFragment extends Fragment {

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
    String personName;

    Activity activity;
    iComunicaFragments interfazComunicaFragments;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewActividades = view.findViewById(R.id.recyclerView);

        recyclerViewActividades.setLayoutManager(new LinearLayoutManager(getContext()));
        mDataBase = FirebaseDatabase.getInstance().getReference();


        listaActividad = new ArrayList<>();

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {


            personName = acct.getDisplayName();


            Log.e("Correo de usuario en oc", "Este es su correo en oc:" + personName);
            getDatosFromFirebase();
        }
        else{
            Toast.makeText(getContext(), "No hay cuenta Asociada", Toast.LENGTH_SHORT).show();
        }

       // verificarExiste("Rociooo");




/*
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
*/


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

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {


            personName = acct.getDisplayName();


            Log.e("Correo de usuario en HF", "Este es su correo en HF:" + personName);

        }

        mDataBase.child("Usuarios").child(personName).child("Actividades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){



                    for( DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        Actividad actividad = dataSnapshot1.getValue(Actividad.class);
                        String nombre = actividad.getNombre();
                        String fecha = actividad.getFecha();
                        String Descripcion = actividad.getDescripcion();
                        int imagenId = actividad.getImagenId();

                       // Toast.makeText(getContext(), "Llega la descripcion"+Descripcion, Toast.LENGTH_SHORT).show();

                        listaActividad.add(new Actividad(nombre,fecha,Descripcion,R.drawable.ic_menu_camera));

                    }



                    if(getActivity()!=null){
                    adapterActividad= new AdapterActividad(getContext(),listaActividad);
                    recyclerViewActividades.setAdapter(adapterActividad);

                    adapterActividad.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String nombre = listaActividad.get(recyclerViewActividades.getChildAdapterPosition(v)).getNombre();
                            Toast.makeText(getContext(), "Seleccionó: "+ nombre, Toast.LENGTH_SHORT).show();
                            interfazComunicaFragments.enviarTarea(listaActividad.get(recyclerViewActividades.getChildAdapterPosition(v)));
                        }
                    });

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public void verificarExiste(String dato){

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {


            personName = acct.getDisplayName();


            Log.e("Correo de usuario en HF", "Este es su correo en HF:" + personName);

        }

        Query consulta = FirebaseDatabase.getInstance().getReference()
                .child("Usuarios").child(personName).child("Actividades").orderByChild("nombre").equalTo(dato);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.activity = (Activity) context;
            interfazComunicaFragments = (iComunicaFragments) this.activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}