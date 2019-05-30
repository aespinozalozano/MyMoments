package com.fernandopretell.mymoments.data.remote.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Fernando Pretell on 26/08/2017.
 */

public class Register {


    @SerializedName("nombre")
    private String nombre;

    @SerializedName("nacimiento")
    private String nacimniento;

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("contrasenia")
    private String contrasenia;


    public Register(String usuario, String contrasenia) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    public Register(String nombre, String nacimniento, String usuario, String contrasenia) {
        this.nombre = nombre;
        this.nacimniento = nacimniento;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacimniento() {
        return nacimniento;
    }

    public void setNacimniento(String nacimniento) {
        this.nacimniento = nacimniento;
    }



    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

}
