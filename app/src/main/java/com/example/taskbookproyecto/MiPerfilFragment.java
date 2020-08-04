package com.example.taskbookproyecto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MiPerfilFragment extends Fragment {

    public TextView txtNombre,txtEmail,txtID;
    private ImageView imageUsuario;
    private Button btnCerrarS;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mi_perfil, container, false);

       GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);


        txtNombre =v.findViewById(R.id.txtNom1);
        txtEmail =v.findViewById(R.id.txtEm);
        txtID =v.findViewById(R.id.txtid);
        imageUsuario =v.findViewById(R.id.Imagefoto1);

        btnCerrarS = v.findViewById(R.id.btnCerrarSesion);
        btnCerrarS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.btnCerrarSesion:
                        signOut();
                        break;
                    // ...
                }

            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            txtNombre.setText(personName);
            txtEmail.setText(personEmail);
            txtID.setText(personId);
            Glide.with(getContext()).load(String.valueOf(personPhoto)).into(imageUsuario);

            Toast.makeText(getContext(), "Este es su informacion de usuario", Toast.LENGTH_LONG).show();



        }



        // Inflate the layout for this fragment
        return v;


    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Cerró sesion Exitosamente", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}