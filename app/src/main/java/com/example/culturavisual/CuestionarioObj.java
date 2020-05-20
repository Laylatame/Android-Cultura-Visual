package com.example.culturavisual;

public class CuestionarioObj {

    private String _mNombreCuestionario;
    private String _mImagenURL;

    public CuestionarioObj(){

    }

    public CuestionarioObj(String nombreCuestionario, String imagenURL){
        this._mNombreCuestionario = nombreCuestionario;
        this._mImagenURL = imagenURL;
    }

    public void setNombreCuestionario(String nombre){
        this._mNombreCuestionario = nombre;
    }

    public String getNombreCuestionario(){
        return _mNombreCuestionario;
    }

    public void setImagenURL(String imagenURL){
        this._mImagenURL = imagenURL;
    }

    public String getImagenURL(){
        return _mImagenURL;
    }
}
