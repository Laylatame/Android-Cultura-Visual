package com.example.culturavisual;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.widget.Toast;

public class AdapterRanking extends RecyclerView.Adapter<AdapterRanking.ViewHolder>{

    private Context mContext;
    private ArrayList<UsuarioCuestionario> mRankingList;

    public AdapterRanking(Context context, ArrayList<UsuarioCuestionario> rankingList){

        this.mContext = context;
        this.mRankingList = rankingList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.ranking_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        UsuarioCuestionario currentItem = mRankingList.get(position);
        holder.textViewRanking.setText(currentItem.getUser());
        holder.textViewScore.setText(String.valueOf(currentItem.getScore()));
    }

    @Override
    public int getItemCount() {
        return mRankingList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView textViewRanking;
        public TextView textViewScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewRanking = itemView.findViewById(R.id.textViewRanking);
            textViewScore = itemView.findViewById(R.id.textViewScore);

        }
    }
}
