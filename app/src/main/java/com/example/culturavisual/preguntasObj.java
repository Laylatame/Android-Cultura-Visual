package com.example.culturavisual;

import android.widget.Button;

public class preguntasObj {
 // r por respuesta
    private String _pregunta;
    private Button _r1;
    private Button _r2;
    private Button _r3;
    private Button _r4;
    private  Button _rCorrecta;

    public  preguntasObj (String pregunta, Button r1, Button r2, Button r3, Button r4/*,Button rCorrecta*/){
        this._pregunta = pregunta;
        this._r1= r1;
        this._r2= r2;
        this._r3= r3;
        this._r4= r4;
        //this._rCorrecta= rCorrecta;
    }





    public String get_pregunta() {
        return _pregunta;
    }
    public void set_pregunta(String _pregunta) {
        this._pregunta = _pregunta;
    }



    public Button get_r1() {
        return _r1;
    }

    public void set_r1(Button _r1) {
        this._r1 = _r1;
    }

    public Button get_r2() {
        return _r2;
    }

    public void set_r2(Button _r2) {
        this._r2 = _r2;
    }

    public void set_r3(Button _r3) {
        this._r3 = _r3;
    }

    public Button get_r3() {
        return _r3;
    }

    public void set_r4(Button _r4) {
        this._r4 = _r4;
    }

    public Button get_r4() {
        return _r4;
    }

    /*public void set_rCorrecta(Button _rCorrecta) {
        this._rCorrecta = _rCorrecta;
    }

    public Button get_rCorrecta() {
        return _rCorrecta;
    }
*/

}
