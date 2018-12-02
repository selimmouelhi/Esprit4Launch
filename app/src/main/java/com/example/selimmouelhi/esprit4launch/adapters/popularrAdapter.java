package com.example.selimmouelhi.esprit4launch.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;

import java.util.ArrayList;

public class popularrAdapter extends RecyclerView.Adapter<popularrAdapter.ViewHolder> {

    private static final String TAG= "popularRecycler";
    private static ArrayList<Restaurant> lr = new ArrayList<Restaurant>();
    private Context mcontext ;


    public popularrAdapter( Context mcontext,ArrayList<Restaurant> listr) {
        lr = listr;
        this.mcontext = mcontext;
        System.out.println(lr.get(0).getName()+"in constructor");


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_row,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {

        System.out.println(viewHolder.getAdapterPosition()+"in bind");
        System.out.println(lr.get(viewHolder.getAdapterPosition()).getAdresse()+"in bind");

        viewHolder.adresseView.setText(lr.get(viewHolder.getAdapterPosition()).getAdresse());
        viewHolder.nameView.setText(lr.get(viewHolder.getAdapterPosition()).getName());
        System.out.println(lr.size());


    }



    @Override
    public int getItemCount() {
        return lr.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{


        ImageView restImage;
        TextView nameView ;
        TextView adresseView ;

        public ViewHolder(View itemView){
            super(itemView);
            nameView =  itemView.findViewById(R.id.nameR);
            adresseView  =  itemView.findViewById(R.id.adresseR);

            restImage  = itemView.findViewById(R.id.imageR);

        }
    }
}
