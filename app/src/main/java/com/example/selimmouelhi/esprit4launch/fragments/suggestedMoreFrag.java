package com.example.selimmouelhi.esprit4launch.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.selimmouelhi.esprit4launch.Interfaces.BestServicesRes;
import com.example.selimmouelhi.esprit4launch.Interfaces.MostTastyRestaurants;
import com.example.selimmouelhi.esprit4launch.Interfaces.topratedRestaurants;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.adapters.suggestedAdapter;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;

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
public class suggestedMoreFrag extends Fragment {


    private ArrayList<Restaurant> listr ;

    ListView listView ;


    public suggestedMoreFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggested_more, container, false);
        listView = view.findViewById(R.id.moreliste);
        String state =getArguments().getString("state");

        System.out.println(state+ " in frag");
            listr = new ArrayList<Restaurant>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(topratedRestaurants.Base_Url).addConverterFactory(GsonConverterFactory.create())
                .build();
        switch (state){
            case "topratedres":


                topratedRestaurants topratedRestaurants = retrofit.create(com.example.selimmouelhi.esprit4launch.Interfaces.topratedRestaurants.class);
                Call<List<Restaurant>> call = topratedRestaurants.getAllTopratedRes();

                call.enqueue(new Callback<List<Restaurant>>() {
                    @Override
                    public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {

                        System.out.println("imhere too");
                        System.out.println(response.body()+"in suggestedmore");
                        listr = (ArrayList<Restaurant>)response.body();

                        System.out.println(listr.get(0).getAdresse()+"in case 0");
                        suggestedAdapter suggestedAdapter = new suggestedAdapter(getActivity(),listr);
                        listView.setAdapter(suggestedAdapter);

                    }

                    @Override
                    public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                        System.out.println("error");
                    }
                });

                System.out.println(listr+"in first function");

            case "tasty":
                MostTastyRestaurants mostTastyRestaurants = retrofit.create(MostTastyRestaurants.class);

                Call<List<Restaurant>> callT = mostTastyRestaurants.getAllMostTasty();
                callT.enqueue(new Callback<List<Restaurant>>() {
                    @Override
                    public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                        listr = (ArrayList<Restaurant>)response.body();
                        System.out.println(listr.get(0).getAdresse());
                        suggestedAdapter suggestedAdapter = new suggestedAdapter(getActivity(),listr);
                        listView.setAdapter(suggestedAdapter);

                    }

                    @Override
                    public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                        System.out.println("error");
                    }
                });
            case "service":
                BestServicesRes bestServicesRes = retrofit.create(BestServicesRes.class);
                Call<List<Restaurant>> callS = bestServicesRes.getAllBetterServices();
                callS.enqueue(new Callback<List<Restaurant>>() {
                    @Override
                    public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                        listr = (ArrayList<Restaurant>) response.body();
                        System.out.println(listr.get(0).getAdresse());
                        suggestedAdapter suggestedAdapter = new suggestedAdapter(getActivity(),listr);
                        listView.setAdapter(suggestedAdapter);

                    }

                    @Override
                    public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                        System.out.println("error");
                    }
                });
        }
        System.out.println(listr+"in listfrag");


        return  view ;
    }






}
