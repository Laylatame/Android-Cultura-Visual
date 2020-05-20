package com.example.culturavisual;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

public class AdapterPreguntas extends RecyclerView.Adapter <AdapterPreguntas.ViewHolder>{

    private Context mContext;
    private ArrayList<preguntasObj> mPreguntasList;
    private Usuarios loggedUser;
    Integer chosenAnswers[];



    public  AdapterPreguntas(Context context,ArrayList<preguntasObj> preguntasObjsL, Usuarios user) {
        this.mContext = context;
        this.mPreguntasList = preguntasObjsL;
        this.loggedUser = user;
        chosenAnswers = new Integer[preguntasObjsL.size()];
        Arrays.fill(chosenAnswers,new Integer(0));
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

        holder.mpregunta.setText(currentItem.getPregunta());

        //Set answer 1
        if(currentItem.isImageR1()){
            Picasso.with(mContext).load(currentItem.getR1()).fit().centerInside().into((Target) holder.mr1);
        } else{
            holder.mr1.setText(currentItem.getR1());
        }

        //Set answer 2
        if(currentItem.isImageR2()){
            Picasso.with(mContext).load(currentItem.getR2()).fit().centerInside().into((Target) holder.mr2);
        } else{
            holder.mr2.setText(currentItem.getR2());
        }

        //Set answer 3
        if(currentItem.isImageR3()){
            Picasso.with(mContext).load(currentItem.getR3()).fit().centerInside().into((Target) holder.mr3);
        } else{
            holder.mr3.setText(currentItem.getR3());
        }

        //Set answer 4
        if(currentItem.isImageR4()){
            Picasso.with(mContext).load(currentItem.getR4()).fit().centerInside().into((Target) holder.mr4);
        } else{
            holder.mr4.setText(currentItem.getR4());
        }

}

    @Override
    public int getItemCount() {
        return mPreguntasList.size();
    }

    // como se ven los items individuales
    public class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView mpregunta;
        private TextView mr1;
        private TextView mr2;
        private TextView mr3;
        private TextView mr4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mpregunta = itemView.findViewById(R.id.txt_pregunta);
            mr1 = itemView.findViewById(R.id.r1);
            mr2 = itemView.findViewById(R.id.r2);
            mr3 = itemView.findViewById(R.id.r3);
            mr4 = itemView.findViewById(R.id.r4);


            mr1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    preguntasObj current = mPreguntasList.get(position);

                    chosenAnswers[position] = 0;

                    Toast.makeText(mContext, String.valueOf(chosenAnswers[position]), Toast.LENGTH_SHORT).show();

                    /*
                    for(int i=0; i<chosenAnswers.length; i++){
                        Toast.makeText(mContext, String.valueOf(chosenAnswers[i]), Toast.LENGTH_SHORT).show();
                    }

                     */
                }
            });

            mr2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    preguntasObj current = mPreguntasList.get(position);

                    chosenAnswers[position] = 1;

                    Toast.makeText(mContext, String.valueOf(chosenAnswers[position]), Toast.LENGTH_SHORT).show();
                }
            });

            mr3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    preguntasObj current = mPreguntasList.get(position);

                    chosenAnswers[position] = 2;

                    Toast.makeText(mContext, String.valueOf(chosenAnswers[position]), Toast.LENGTH_SHORT).show();
                }
            });

            mr4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    preguntasObj current = mPreguntasList.get(position);

                    chosenAnswers[position] = 3;

                    Toast.makeText(mContext, String.valueOf(chosenAnswers[position]), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
