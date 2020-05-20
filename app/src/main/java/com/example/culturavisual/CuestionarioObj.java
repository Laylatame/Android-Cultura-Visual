package com.example.culturavisual;

import java.io.Serializable;

public class CuestionarioObj implements Serializable {

    private String _mNombreCuestionario;
    private String _mImagenURL;
    private String _mCuestionarioID;

    public CuestionarioObj(){

    }

    public CuestionarioObj(String nombreCuestionario, String imagenURL, String cuestionarioID){
        this._mNombreCuestionario = nombreCuestionario;
        this._mImagenURL = imagenURL;
        this._mCuestionarioID = cuestionarioID;
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

    public void setCuestionarioID(String id){
        this._mCuestionarioID = id;
    }

    public String getCuestionarioID(){
        return _mCuestionarioID;
    }

}
