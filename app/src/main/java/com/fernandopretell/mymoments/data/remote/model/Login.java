package com.fernandopretell.mymoments.data.remote.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Fernando Pretell on 26/08/2017.
 */

public class Login {

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("contrasenia")
    private String contrasenia;

    public Login(String usuario, String contrasenia) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
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
