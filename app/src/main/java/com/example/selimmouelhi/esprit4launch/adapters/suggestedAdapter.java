package com.example.selimmouelhi.esprit4launch.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.Utils.ImageLoader;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class suggestedAdapter extends ArrayAdapter<Restaurant> {

    static class viewHolder{
        TextView nameView ;
        TextView adresseView ;
        TextView streetView ;
        ImageView imageView;


    }

    private ArrayList<Restaurant> listsuggestion;
    private Activity context ;

    public suggestedAdapter(Activity context , ArrayList<Restaurant> listsuggestion){
        super(context,R.layout.viewmore_row,listsuggestion);
        this.context = context;
        this.listsuggestion = listsuggestion;


    }

    @NonNull
    @Override
    public View getView(int position,  View convertView, @NonNull ViewGroup parent) {

        viewHolder viewHolder;
        if (convertView == null)
        {

            LayoutInflater lf = context.getLayoutInflater();
            viewHolder = new viewHolder();
            convertView = lf.inflate(R.layout.viewmore_row, null,true);
            viewHolder.adresseView = convertView.findViewById(R.id.adresserestaurant);
            viewHolder.nameView = convertView.findViewById(R.id.nomrestaurant);
            viewHolder.streetView  = convertView.findViewById(R.id.streetrest);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);




        }else{

            viewHolder = (viewHolder) convertView.getTag();

            System.out.println(listsuggestion.get(0).getName()+" in adapter");
        Picasso.with(convertView.getContext()).load(ImageLoader.url_image+listsuggestion.get(position).getImageUrl()+ImageLoader.API_Key).into(viewHolder.imageView);

        viewHolder.nameView.setText(listsuggestion.get(position).getName());
        viewHolder.adresseView.setText(listsuggestion.get(position).getAdresse()+"");
        viewHolder.streetView.setText(listsuggestion.get(position).getAdresse()+"");


        }







        return convertView;
    }
}
