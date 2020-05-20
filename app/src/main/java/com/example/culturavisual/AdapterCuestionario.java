package com.example.culturavisual;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterCuestionario extends RecyclerView.Adapter<AdapterCuestionario.ViewHolder> {

    private Context mContext;
    private ArrayList<CuestionarioObj> cuestionarioList;


    public AdapterCuestionario(Context context, ArrayList<CuestionarioObj> cuestionarioList){
        this.mContext = context;
        this.cuestionarioList = cuestionarioList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cuestionario_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CuestionarioObj currentItem = cuestionarioList.get(position);
        String cuestionarioNombre = currentItem.getNombreCuestionario();
        String imgURL = currentItem.getImagenURL();

        holder.mTextViewCuestionario.setText(cuestionarioNombre);
        Picasso.with(mContext).load(imgURL).fit().centerInside().into(holder.mImageViewCuestionario);

    }

    @Override
    public int getItemCount() {
        return cuestionarioList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageViewCuestionario;
        public TextView mTextViewCuestionario;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageViewCuestionario = itemView.findViewById(R.id.imageViewCuestionario);
            mTextViewCuestionario = itemView.findViewById(R.id.textViewCuestionario);


            /*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Noticia current = mNoticiaList.get(position);
                    String url = current.getNoticiaURL();
                    Intent viewIntent =
                            new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(url));
                    mContext.startActivity(viewIntent);
                }
            });
             */
        }
    }
}
