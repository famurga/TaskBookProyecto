package com.example.taskbookproyecto.Entidades;

public class Actividad {
    private String nombre;
    private String fecha;
    private int imagenId;
    private String descripcion;
    public Actividad(){

    }
    public Actividad(String nombre, String fecha,String descripcion, int imagenId) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.descripcion = descripcion;
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

    public  String getDescripcion(){ return descripcion;}
    public  void setDescripcion(String descripcion){this.descripcion = descripcion;}
}
