package com.example.taskbookproyecto;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taskbookproyecto.Adaptadores.AdapterActividad;
import com.example.taskbookproyecto.Entidades.Actividad;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Actividades extends Fragment {

    ArrayList<Actividad> listaActividad;
     EditText edtFecha, edtNombre;
     DialogFragment dialogFragment;
     CargarEnBaseDatos cargar;
        String nombre,fecha, descripcion;
        int imagen;
    FloatingActionButton floatingActionButton;
    public String personName;
    ImageView fotoTarea, micImageView;
    private static final int SPEECH_REQUEST_CODE = 0;
     EditText edtDescripcion;

    AdapterActividad adapterActividad;
    RecyclerView recyclerViewActividades;
    DatabaseReference mDataBase;



    public Actividades() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_actividades, container, false);


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

    //Boton Flotante
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.añadir);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder =  new AlertDialog.Builder(getActivity());
                final View mview = getLayoutInflater().inflate(R.layout.dialog_crear_actividad,null);
                 edtNombre = (EditText) mview.findViewById(R.id.edtNombreActividad);

                edtFecha = (EditText) mview.findViewById(R.id.edtFechaActividad);
                micImageView = mview.findViewById(R.id.microAlertDialog);

                micImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        try{
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
                            startActivityForResult(intent, SPEECH_REQUEST_CODE);
                        }
                        catch(ActivityNotFoundException e)
                        {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                          Uri.parse("https://market.android.com/details?id=com.google.android" +
                                  ".googlequicksearchbox"));
                            startActivity(browserIntent);

                        }



                    }
                });







               edtFecha.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       showDataPickerDialog();


                   }
               });
                 edtDescripcion = (EditText) mview.findViewById(R.id.edtDescripcionActividad);
                Button btnCrearActividad = (Button) mview.findViewById(R.id.btnDialogCrear);
                final Button btnCancelar = (Button) mview.findViewById(R.id.btnDialogCancelar);

                btnCrearActividad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Creara una actividad", Toast.LENGTH_SHORT).show();


                        nombre = edtNombre.getText().toString();
                        fecha = edtFecha.getText().toString();
                        descripcion = edtDescripcion.getText().toString();



                        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
                        if (acct != null) {


                            personName = acct.getDisplayName();


                            Log.e("Correo de usuario","Este es su correo:"+personName);

                            CargarEnBaseDatos ac = new CargarEnBaseDatos();

                            ac.cargarDatosFirebase(personName,nombre,fecha,descripcion,15);


                        }



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
                      //  String fecha = actividad.getFecha();
                       // String Descripcion = actividad.getDescripcion();
                        //int imagenId = actividad.getImagenId();

                        // Toast.makeText(getContext(), "Llega la descripcion"+Descripcion, Toast.LENGTH_SHORT).show();

                        listaActividad.add(new Actividad(nombre,null,null,R.drawable.ic_menu_camera));

                    }

                    adapterActividad= new AdapterActividad(getContext(),listaActividad);
                    recyclerViewActividades.setAdapter(adapterActividad);

                    adapterActividad.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String nombre = listaActividad.get(recyclerViewActividades.getChildAdapterPosition(v)).getNombre();
                            Toast.makeText(getContext(), "Seleccionó: "+ nombre +" Para mas detalles dirijase a Tareas", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }






    // Esta devolución de llamada se invoca cuando regresa el Reconocimiento de voz.
    // Aquí es donde procesas la intención y extraes el texto del discurso de la intención.

    public void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);

            descripcion =spokenText;
          //  edtDescripcion = (EditText) getActivity().findViewById(R.id.edtDescripcionActividad);
            edtDescripcion.setText(descripcion);


        }
        super.onActivityResult(requestCode, resultCode, data);
    }





    public void showDataPickerDialog(){
        //DataPickerFragment newFragment1 =  new DataPickerFragment();
        DataPickerFragment newFragment = DataPickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final String selectedDate = dayOfMonth + " / " + (month+1) +" / " + year;
                edtFecha.setText(selectedDate);
                Calendar calendar = Calendar.getInstance();
                calendar.set(calendar.YEAR,year);
                calendar.set(calendar.MONTH,month);
                calendar.set(calendar.DAY_OF_MONTH,dayOfMonth);
                Utils.setAlarm(1, calendar.getTimeInMillis(), getActivity());

                Toast.makeText(getActivity(), "Las horas son : "+(calendar.getTimeInMillis())/3600000, Toast.LENGTH_LONG).show();
            }
        });


        newFragment.show(getActivity().getSupportFragmentManager(),"view");


    }


}