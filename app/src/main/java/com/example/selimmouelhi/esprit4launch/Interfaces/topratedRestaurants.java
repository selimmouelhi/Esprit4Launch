package com.example.selimmouelhi.esprit4launch.Interfaces;

import com.example.selimmouelhi.esprit4launch.Utils.Constant;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface topratedRestaurants {

    String Base_Url = Constant.root_server+"web_services/";


     @GET("topratedRestaurants")
     Call<List<Restaurant>> getTopratedRes();


    @GET("alltopratedRestaurants")
    Call<List<Restaurant>> getAllTopratedRes();
}
