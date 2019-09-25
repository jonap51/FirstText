package com.app.scilicet.movble;

public class PojoListaUsuario {
    private String nombres;
    private String apellido;
    private String localidad;
    private float estrellas;


    public PojoListaUsuario() {
    }

    public PojoListaUsuario(String nombres, String apellido, String localidad, float estrellas) {
        this.nombres = nombres;
        this.apellido = apellido;
        this.localidad = localidad;
        this.estrellas = estrellas;
    }


    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public float getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(float estrellas) {
        this.estrellas = estrellas;
    }
}
