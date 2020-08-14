package com.example.taskbookproyecto.Entidades;

import java.io.Serializable;

public class Actividad implements Serializable {
    private String nombre;
    private String fecha;
    private int imagenId;
    private String Descripcion;
    public Actividad(){

    }
    public Actividad(String nombre, String fecha,String descripcion, int imagenId) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.Descripcion = descripcion;
        this.imagenId = imagenId;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getImagenId() {
        return imagenId;
    }

    public void setImagenId(int imagenId) {
        this.imagenId = imagenId;
    }

    public  String getDescripcion(){ return Descripcion;}
    public  void setDescripcion(String descripcion){this.Descripcion = descripcion;}
}
