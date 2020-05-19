package com.example.culturavisual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterPreguntas extends RecyclerView.Adapter <AdapterPreguntas.ViewHolder>{

    private Context mContext;
    private ArrayList<preguntasObj> mPreguntasList;
    private int mSize;



    public  AdapterPreguntas(Context context,ArrayList<preguntasObj> preguntasObjsL, int size) {
        mContext = context;
        mSize = size;
        mPreguntasList = preguntasObjsL;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.preguntas_item,parent,false);

        return  new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        preguntasObj currentItem = mPreguntasList.get(position);
        String preguntas = currentItem.get_pregunta();

        holder.mr1.setText(preguntas);
        holder.mpregunta.setText(preguntas);
}

    @Override
    public int getItemCount() {
        return mSize;
       // return mPreguntasList.size();
    }

    // como se ven los items individuales
    public class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView mpregunta;
        private Button mr1;
        private Button mr2;
        private Button mr3;
        private Button mr4;
       // private  Button mrCorrecta;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mpregunta = itemView.findViewById(R.id.txt_pregunta);
            mr1 = itemView.findViewById(R.id.btn_r1);
            mr2 = itemView.findViewById(R.id.btn_r2);
            mr3 = itemView.findViewById(R.id.btn_r3);
            mr4 = itemView.findViewById(R.id.btn_r4);
           // mrCorrecta = itemView.findViewById(R.id.btn_correct);

        }

        // aqui le pasamos la el layout a todos los card view generados
      /*  void bind (int listaIndex){

            mpregunta.setText(String.valueOf(listaIndex));
        }*/


    }
}
