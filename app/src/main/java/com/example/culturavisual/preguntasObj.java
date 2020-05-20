package com.example.culturavisual;

import android.widget.Button;

public class preguntasObj {
 // r por respuesta
    private String _pregunta;
    private String _r1Contenido;
    private boolean _r1isImage;
    private String _r2Contenido;
    private boolean _r2isImage;
    private String _r3Contenido;
    private boolean _r3isImage;
    private String _r4Contenido;
    private boolean _r4isImage;
    private  String _rCorrecta;

    public preguntasObj(){

    }

    public  preguntasObj (String pregunta, String r1, boolean r1Image, String r2, boolean r2Image, String r3, boolean r3Image, String r4, boolean r4Image, String correcta){
        this._pregunta = pregunta;
        this._r1Contenido= r1;
        this._r2Contenido= r2;
        this._r3Contenido= r3;
        this._r4Contenido= r4;
        this._r1isImage= r1Image;
        this._r2isImage= r2Image;
        this._r3isImage= r3Image;
        this._r4isImage= r4Image;
        this._rCorrecta= correcta;
    }

    public String getPregunta() {
        return _pregunta;
    }

    public void setPregunta(String pregunta){
        this._pregunta = pregunta;
    }
    public String getR1(){
        return _r1Contenido;
    }

    public void setR1(String r1){
        this._r1Contenido = r1;
    }

    public boolean isImageR1(){
        return _r1isImage;
    }

    public void setImageR1(boolean r1){
        this._r1isImage = r1;
    }

    public String getR2(){
        return _r2Contenido;
    }

    public void setR2(String r2){
        this._r2Contenido = r2;
    }

    public boolean isImageR2(){
        return _r2isImage;
    }

    public void setImageR2(boolean r2){
        this._r2isImage = r2;
    }

    public String getR3(){
        return _r3Contenido;
    }

    public void setR3(String r3){
        this._r3Contenido = r3;
    }

    public boolean isImageR3(){
        return _r3isImage;
    }

    public void setImageR3(boolean r3){
        this._r3isImage = r3;
    }

    public String getR4(){
        return _r4Contenido;
    }

    public void setR4(String r4){
        this._r4Contenido = r4;
    }

    public boolean isImageR4(){
        return _r4isImage;
    }

    public void setImageR4(boolean r4){
        this._r4isImage = r4;
    }

    public String getCorrecta(){
        return _rCorrecta;
    }

    public void setCorrecta(String correcta){
        this._rCorrecta = correcta;
    }

}
