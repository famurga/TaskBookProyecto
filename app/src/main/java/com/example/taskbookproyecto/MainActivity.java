package com.example.taskbookproyecto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taskbookproyecto.Entidades.Actividad;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //defining view objects
    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar,btnLogin, buttonInvi;
    private ProgressDialog progressDialog;
    private  GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton botonGoogle;
    private static final int RC_SIGN_IN = 777;




    Actividades actividades = new Actividades();


    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        TextEmail = (EditText) findViewById(R.id.edit1);
        TextPassword = (EditText) findViewById(R.id.edit2);

        btnRegistrar = (Button) findViewById(R.id.button1);
        btnLogin = (Button) findViewById(R.id.button2);
        buttonInvi = (Button)findViewById(R.id.btnInvitado);


        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        btnRegistrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        buttonInvi.setOnClickListener(this);


        //LOGIN GOOGLE
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
         gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);

        botonGoogle = findViewById(R.id.btnGoogle);
        botonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
         Intent intent = new Intent(getApplication(), NavigationDrawerActivity.class);
         startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Mensaje de Error xd", "signInResult:failed code=" + e.getStatusCode());

        }
    }



    private void registrarUsuario(){

        //Obtenemos el email y la contraseña desde las cajas de texto
        String email = TextEmail.getText().toString().trim();
        String password  = TextPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){

                            Toast.makeText(MainActivity.this,"Se ha registrado el usuario con el email: "+ TextEmail.getText(),Toast.LENGTH_LONG).show();


                        }else{
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(MainActivity.this,"Ese usuario ya existe ",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this,"No se pudo registrar el usuario ",Toast.LENGTH_LONG).show();
                            }

                        }
                        progressDialog.dismiss();
                    }
                });

    }
    private void loguearUsuario(){


        //Obtenemos el email y la contraseña desde las cajas de texto
        final String email = TextEmail.getText().toString().trim();
        String password  = TextPassword.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Falta ingresar la contraseña",Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();

        //loguear usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){

                            Toast.makeText(MainActivity.this,"Bienvenido a TaskBook  " + TextEmail.getText(),Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplication(),NavigationDrawerActivity.class);

                            startActivity(intent);

                        }else{
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(MainActivity.this,"Ese usuario ya existe ",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this,"No se pudo registrar el usuario ",Toast.LENGTH_LONG).show();
                            }

                        }
                        progressDialog.dismiss();
                    }
                });



    }
    @Override
    public void onClick(View view) {


        switch (view.getId()){

            case R.id.button1:
                //Invocamos al método:
                registrarUsuario();
                break;
            case R.id.button2:
                loguearUsuario();
                break;
            case R.id.btnInvitado:
                Intent intent = new Intent(getApplication(),NavigationDrawerActivity.class);
                startActivity(intent);

        }


    }
}
