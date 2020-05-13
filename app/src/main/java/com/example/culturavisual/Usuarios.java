package com.example.culturavisual;

import java.io.Serializable;

public class Usuarios implements Serializable {

    private String _usuario;
    private String _contrasena;


    public Usuarios(){

    }

    public Usuarios(String usuario, String contrasena){
        this._usuario = usuario;
        this._contrasena = contrasena;
    }

    public void setUsuario(String usuario){
        this._usuario = usuario;
    }

    public String getUsuario(){
        return this._usuario;
    }

    public void setContrasena(String contrasena){
        this._contrasena = contrasena;
    }

    public String getContrasena(){
        return this._contrasena;
    }
}
