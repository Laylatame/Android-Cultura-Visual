package com.example.culturavisual;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
        Arrays.fill(chosenAnswers,new Integer(-1));
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
            //Picasso.with(mContext).load(currentItem.getR1()).fit().centerInside().into((Target) holder.mr1);

            Drawable image = LoadImageFromWebURL(currentItem.getR1());


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
                    mr1.setBackgroundResource(R.color.darkGray);
                    mr2.setBackgroundResource(R.color.lightGray);
                    mr3.setBackgroundResource(R.color.lightGray);
                    mr4.setBackgroundResource(R.color.lightGray);

                }
            });

            mr2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    preguntasObj current = mPreguntasList.get(position);

                    chosenAnswers[position] = 1;
                    mr1.setBackgroundResource(R.color.lightGray);
                    mr2.setBackgroundResource(R.color.darkGray);
                    mr3.setBackgroundResource(R.color.lightGray);
                    mr4.setBackgroundResource(R.color.lightGray);

                }
            });

            mr3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    preguntasObj current = mPreguntasList.get(position);

                    chosenAnswers[position] = 2;
                    mr1.setBackgroundResource(R.color.lightGray);
                    mr2.setBackgroundResource(R.color.lightGray);
                    mr3.setBackgroundResource(R.color.darkGray);
                    mr4.setBackgroundResource(R.color.lightGray);

                }
            });

            mr4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    preguntasObj current = mPreguntasList.get(position);

                    chosenAnswers[position] = 3;
                    mr1.setBackgroundResource(R.color.lightGray);
                    mr2.setBackgroundResource(R.color.lightGray);
                    mr3.setBackgroundResource(R.color.lightGray);
                    mr4.setBackgroundResource(R.color.darkGray);

                }
            });

        }
    }


    public static Drawable LoadImageFromWebURL(String url) {
        try {
            InputStream iStream = (InputStream) new URL(url).getContent();
            Drawable drawable = Drawable.createFromStream(iStream, "src name");
            return drawable;
        } catch (Exception e) {
            return null;
        }}


    public Integer[] getChosenAnswers(){
        return chosenAnswers;
    }
}
