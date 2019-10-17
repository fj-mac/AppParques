package com.example.tuparquej;

import android.util.Log;

import java.security.Key;
import java.util.HashMap;

public class Entidad {
    private String imagen;
    private String nombre;
    private String barrio;
    private String details;
    private int estrellas;
    private double latitud;
    private double longitud;
    private HashMap<Key,String> reviews;

    public Entidad() {
    }

    public Entidad(String imagen, String nombre, String barrio, String details, int estrellas, double latitud, double longitud) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.barrio = barrio;
        this.details = details;
        this.estrellas = estrellas;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        Log.d("numero", "Se ha cargado el parque con nombre: " +nombre);
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public HashMap<Key,String> getReviews() {
        return reviews;
    }

    public void setReviews(HashMap<Key,String> reviews) {
        this.reviews = reviews;
    }
}