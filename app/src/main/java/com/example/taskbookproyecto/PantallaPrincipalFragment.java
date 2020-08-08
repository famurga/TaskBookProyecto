package com.example.taskbookproyecto;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.taskbookproyecto.ui.home.TareasFragment;

public class PantallaPrincipalFragment extends Fragment implements View.OnClickListener {
    FragmentTransaction  transaction;
    Fragment FragmentActividades, FragmentTareas,FragmentMiperfil;

    CardView cardViewActividades,cardviewTareas,cardviewMiperfil,cardviewinformacion,
            cardviewadd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pantalla_principal, container, false);





        cardViewActividades = v.findViewById(R.id.ActicardId);
        cardviewTareas = v.findViewById(R.id.TaskCardId);
        cardviewMiperfil = v.findViewById(R.id.PerfilCardId);
        cardviewinformacion = v.findViewById(R.id.AcercaCardId);
        cardviewadd = v.findViewById(R.id.AddCardId);

        cardViewActividades.setOnClickListener((View.OnClickListener) this);
        cardviewTareas.setOnClickListener((View.OnClickListener) this);
        cardviewMiperfil.setOnClickListener((View.OnClickListener) this);
        cardviewinformacion.setOnClickListener((View.OnClickListener) this);
        cardviewadd.setOnClickListener((View.OnClickListener) this);

        FragmentActividades = new Actividades();
        FragmentTareas = new TareasFragment();
        FragmentMiperfil = new MiPerfilFragment();

        return v;

    }


    public void onClick(View v) {
        transaction = getFragmentManager().beginTransaction();

        switch (v.getId()){

            case R.id.ActicardId:
                transaction.replace(R.id.nav_host_fragment,FragmentActividades);
                transaction.addToBackStack(null);
                break;
            case R.id.TaskCardId:
                transaction.replace(R.id.nav_host_fragment,FragmentTareas);
                transaction.addToBackStack(null);
                break;
            case R.id.PerfilCardId:
                transaction.replace(R.id.nav_host_fragment,FragmentMiperfil);
                transaction.addToBackStack(null);
                break;


        }
        transaction.commit();

    }
}