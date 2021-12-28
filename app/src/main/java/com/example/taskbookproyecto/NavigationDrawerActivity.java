package com.example.taskbookproyecto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taskbookproyecto.Entidades.Actividad;
import com.example.taskbookproyecto.ui.TareaDetalleFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.Serializable;

public class NavigationDrawerActivity extends AppCompatActivity implements iComunicaFragments{

    public static final String user = "names";
    TextView txtUser;
    private AppBarConfiguration mAppBarConfiguration;
    //variable del fragmentdetalle tarea
    TareaDetalleFragment tareaDetalleFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        /*
        //BOTON FLOTANTE GENERAL
       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();




                final AlertDialog.Builder builder =  new AlertDialog.Builder(NavigationDrawerActivity.this);
                final EditText input = new EditText(NavigationDrawerActivity.this);
                final EditText input2 = new EditText(NavigationDrawerActivity.this);
                builder.setTitle("Crear Actividad");
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setView(input2);
                builder.setMessage("Nombre de la actividad").setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();





            }
        });

        */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.actividades,R.id.miPerfil,R.id.PantallaPrincipal)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




        txtUser = (TextView) findViewById(R.id.txtuser);
        String user = getIntent().getStringExtra("names");
        txtUser.setText("Bienvenido "+ user +"!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void enviarTarea(Actividad actividad) {
        //logica para realizar el envio

        tareaDetalleFragment = new TareaDetalleFragment();
        Bundle bundle = new Bundle();
        //enviar el objeto que esta llegando con serializable
        bundle.putSerializable("objeto",actividad);
        tareaDetalleFragment.setArguments(bundle);
        //Abrir fragment
        fragmentManager =  getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameTareas,tareaDetalleFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();



    }
}