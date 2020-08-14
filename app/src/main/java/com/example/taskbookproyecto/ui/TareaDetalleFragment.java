package com.example.taskbookproyecto.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskbookproyecto.Entidades.Actividad;
import com.example.taskbookproyecto.NavigationDrawerActivity;
import com.example.taskbookproyecto.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import id.zelory.compressor.Compressor;

import static android.app.Activity.RESULT_OK;

public class TareaDetalleFragment extends Fragment {

    TextView nombreDetalle, fechaDetalle, DescripcionDetalle;
    ImageView imagenDetalle;
    Button btnSubirImagen, btnSeleccionar, btnCargar;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    Bitmap  thumb_bitmap =  null;
    Uri path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tarea_detalle, container, false);

        nombreDetalle = v.findViewById(R.id.txtTareadetalle);
        fechaDetalle =v.findViewById(R.id.txtfechaDetalle);
      DescripcionDetalle =v.findViewById(R.id.txtDescripcionDetalle);
        imagenDetalle = v.findViewById(R.id.ImgDetalleTarea);


        /*

        btnSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.startPickImageActivity(getActivity());

            }
        });
*/

        btnSubirImagen = v.findViewById(R.id.btnSeleccionarImagen);
        btnSubirImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();

            }
        });

        //Crear Objeto bundle para recibir el  objeto enviado  pora rguemteos

        Bundle objetoTarea = getArguments();
        Actividad actividad = null;
        //validacion apra verifficar si existen arguemtneos envaidos para mostrar

        if( objetoTarea != null){
             actividad = (Actividad) objetoTarea.getSerializable("objeto");

             //establecer los datos en las vistas

            nombreDetalle.setText( actividad.getNombre());
            fechaDetalle.setText( actividad.getFecha());
            DescripcionDetalle.setText( actividad.getDescripcion());
            imagenDetalle.setImageResource(actividad.getImagenId());

        }

        /*
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Fotos subidas");
        storageReference = FirebaseStorage.getInstance().getReference().child("Img comprimido");
        progressDialog = new ProgressDialog(getContext());
*/



     return  v;
    } //Fin OncreateView

    /*

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("prueba de subirimagen","llega");

        if( requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri imageuri = CropImage.getPickImageResultUri(getActivity(),data);

            //recortar imagen
            CropImage.activity(imageuri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setRequestedSize(640,480)
                    .setAspectRatio(2,1).start(getActivity());

            Log.e("prueba de subirimagen","llega");
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            Log.e("prueba 2 de subirimagen","llega");
            if( resultCode == RESULT_OK){
                    Uri  resultUri = result.getUri();

                    File url = new File(resultUri.getPath());
                Picasso.with(getActivity()).load(url).into(imagenDetalle);

                    //Comprimiendo imagen

                try{
                    thumb_bitmap = new Compressor(getContext()).setMaxWidth(640)
                            .setMaxHeight(400)
                            .setQuality(90)
                            .compressToBitmap(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);
                final byte[] thumb_byte =  byteArrayOutputStream.toByteArray();

                //fin del compresor

                final String aleatorio = "fotosubidacomprimida.jpg";

                btnCargar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.setTitle("Subiendo foto");
                        progressDialog.setMessage("Espere porfavor");
                        progressDialog.show();


                        final StorageReference ref = storageReference.child(aleatorio);
                        UploadTask uploadTask = ref.putBytes(thumb_byte);

                        //suibir imagen en storage

                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if( !task.isSuccessful()){
                                    throw Objects.requireNonNull(task.getException());
                                }
                                return ref.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Uri downloaduri = task.getResult();
                                databaseReference.push().child("urlfoto").setValue(downloaduri.toString());
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Imagen subida con exito", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }


    }

     */

    public void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicacion"),10);



    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode == RESULT_OK){
             path = data.getData();
            imagenDetalle.setImageURI(path);
        }
    }


}