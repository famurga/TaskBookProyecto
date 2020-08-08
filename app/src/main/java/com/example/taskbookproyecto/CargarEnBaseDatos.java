package com.example.taskbookproyecto;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taskbookproyecto.Entidades.Actividad;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CargarEnBaseDatos extends AppCompatActivity{


    public static DatabaseReference mRootReference;

    TextView txtnom, txtape,txttel,txtdir;
    public String personEmail="no entra";
    String nombre2;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         nombre2 = getIntent().getStringExtra("nombre");

        }




    public void cargarDatosFirebase(String CorreoUsuario,String nombre, String fecha, String descripcion, int imagen) {




        Log.e("Prueba1","El correo es:");

        Map<String, Object> datosUsuario = new HashMap<>();
        datosUsuario.put("nombre", nombre);
        datosUsuario.put("fecha", fecha);
        datosUsuario.put("Descripcion", descripcion);
        datosUsuario.put("ImagenId", imagen);





        mRootReference = FirebaseDatabase.getInstance().getReference();

        mRootReference.child("Usuarios").child(CorreoUsuario).child("Actividades").push().setValue(datosUsuario);
    }



    public void verificarExiste(String dato){

        Query consulta = FirebaseDatabase.getInstance().getReference()
                .child("Usuario").orderByChild("nombre").equalTo(dato);
        consulta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for( DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {


                        Actividad acti = dataSnapshot1.getValue(Actividad.class);
                        String nombre = acti.getNombre();
                        String apellido = acti.getFecha();
                        int telefono = acti.getImagenId();



                        txtnom.setText(String.valueOf(nombre));
                        txtape.setText(String.valueOf(apellido));
                        txttel.setText(String.valueOf(telefono));


                    }

                }
                else{
                    Toast.makeText(CargarEnBaseDatos.this, "No se encuentra en la base de datos", Toast.LENGTH_SHORT).show();
                    Log.e("NombreUsuario:", "No se encuentra en la base de datos");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
