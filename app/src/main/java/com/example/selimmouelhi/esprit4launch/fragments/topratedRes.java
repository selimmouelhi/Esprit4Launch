package com.example.selimmouelhi.esprit4launch.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selimmouelhi.esprit4launch.Interfaces.topratedRestaurants;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.Utils.ImageLoader;
import com.example.selimmouelhi.esprit4launch.activities.ViewMore;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class topratedRes extends Fragment {

    Button viewMore;


    ImageView imaget ;
    ImageView imaget1 ;
    ImageView imaget2 ;
    ImageView imaget3 ;
    ImageView imaget4 ;
    ImageView imaget5 ;

    TextView nom;
    TextView nom1;
    TextView nom2;
    TextView nom3;
    TextView nom4;
    TextView nom5;


    TextView adresse;
    TextView adresse1;
    TextView adresse2;
    TextView adresse3;
    TextView adresse4;
    TextView adresse5;



    TextView street;
    TextView street1;
    TextView street2;
    TextView street3;
    TextView street4;
    TextView street5;


    TextView rating;
    TextView rating1;
    TextView rating2;
    TextView rating3;
    TextView rating4;
    TextView rating5;




    public topratedRes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_toprated_res, container, false);

        viewMore = view.findViewById(R.id.view_more);
        imaget = view.findViewById(R.id.imaget1);
        imaget1 = view.findViewById(R.id.imaget2);
        imaget2 = view.findViewById(R.id.imaget3);
        imaget3 = view.findViewById(R.id.imaget4);
        imaget4 = view.findViewById(R.id.imaget5);
        imaget5 = view.findViewById(R.id.imaget6);

        nom = view.findViewById(R.id.nomRes);
        nom1 = view.findViewById(R.id.nomRes1);
        nom2 = view.findViewById(R.id.nomRes2);
        nom3 = view.findViewById(R.id.nomRes3);
        nom4 = view.findViewById(R.id.nomRes4);
        nom5 = view.findViewById(R.id.nomRes5);

        adresse = view.findViewById(R.id.adresseRes);
        adresse1 = view.findViewById(R.id.adresseRes1);
        adresse2 = view.findViewById(R.id.adresseRes2);
        adresse3 = view.findViewById(R.id.adresseRes3);
        adresse4 = view.findViewById(R.id.adresseRes4);
        adresse5 = view.findViewById(R.id.adresse5);


        street = view.findViewById(R.id.streetRes);
        street1 = view.findViewById(R.id.streetRes1);
        street2 = view.findViewById(R.id.streetRes2);
        street3 = view.findViewById(R.id.street3);
        street4 = view.findViewById(R.id.streetRes4);
        street5 = view.findViewById(R.id.streetRes5);

        rating = view.findViewById(R.id.rate);
        rating1 = view.findViewById(R.id.rate1);
        rating2 = view.findViewById(R.id.rate2);
        rating3 = view.findViewById(R.id.rate3);
        rating4 = view.findViewById(R.id.rate4);
        rating5 = view.findViewById(R.id.rate5);




        Retrofit retrofit = new Retrofit.Builder().baseUrl(topratedRestaurants.Base_Url).addConverterFactory(GsonConverterFactory.create())
                .build();


        topratedRestaurants topratedRestaurants = retrofit.create(com.example.selimmouelhi.esprit4launch.Interfaces.topratedRestaurants.class);
        Call<List<Restaurant>> call = topratedRestaurants.getTopratedRes();

        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                List<Restaurant> restaurants = response.body();

                System.out.println(response.body());


                //item 1
                Picasso.with(view.getContext()).load(ImageLoader.url_image+restaurants.get(0).getImageUrl()+ImageLoader.API_Key).into(imaget);
                nom.setText(restaurants.get(0).getName());
                adresse.setText(restaurants.get(0).getAdresse());
                street.setText(restaurants.get(0).getAdresse());
                rating.setText(Float.toString(restaurants.get(0).getRating()));

                //item 2 loading
                Picasso.with(view.getContext()).load(ImageLoader.url_image+restaurants.get(1).getImageUrl()+ImageLoader.API_Key).into(imaget1);
                nom1.setText(restaurants.get(1).getName());
                adresse1.setText(restaurants.get(1).getAdresse());
                street1.setText(restaurants.get(1).getAdresse());
                rating1.setText(Float.toString(restaurants.get(1).getRating()));

                //item 3
                Picasso.with(view.getContext()).load(ImageLoader.url_image+restaurants.get(2).getImageUrl()+ImageLoader.API_Key).into(imaget2);
                nom2.setText(restaurants.get(2).getName());
                adresse2.setText(restaurants.get(2).getAdresse());
                street2.setText(restaurants.get(2).getAdresse());
                rating2.setText(Float.toString(restaurants.get(2).getRating()));

                //item 4
                Picasso.with(view.getContext()).load(ImageLoader.url_image+restaurants.get(3).getImageUrl()+ImageLoader.API_Key).into(imaget3);
                nom3.setText(restaurants.get(3).getName());
                adresse3.setText(restaurants.get(3).getAdresse());
                street3.setText(restaurants.get(3).getAdresse());
                rating3.setText(Float.toString(restaurants.get(3).getRating()));

                //item 5
                Picasso.with(view.getContext()).load(ImageLoader.url_image+restaurants.get(4).getImageUrl()+ImageLoader.API_Key).into(imaget4);
                nom4.setText(restaurants.get(4).getName());
                adresse4.setText(restaurants.get(4).getAdresse());
                street4.setText(restaurants.get(4).getAdresse());
                rating4.setText(Float.toString(restaurants.get(4).getRating()));

                //item 6
                Picasso.with(view.getContext()).load(ImageLoader.url_image+restaurants.get(5).getImageUrl()+ImageLoader.API_Key).into(imaget5);
                nom5.setText(restaurants.get(5).getName());
                adresse5.setText(restaurants.get(5).getAdresse());
                street5.setText(restaurants.get(5).getAdresse());
                rating5.setText(Float.toString(restaurants.get(5).getRating()));








            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                System.out.println(t.getMessage());

                Toast.makeText(view.getContext(), "failure", Toast.LENGTH_SHORT).show();

            }
        });


        viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ViewMore.class);
                intent.putExtra("state","topratedres");
                startActivity(intent);

            }
        });
        return  view;
    }



}
