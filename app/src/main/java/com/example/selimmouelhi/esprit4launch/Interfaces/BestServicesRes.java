package com.example.selimmouelhi.esprit4launch.Interfaces;

import com.example.selimmouelhi.esprit4launch.Utils.Constant;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BestServicesRes {


    String Base_Url = Constant.root_server+"web_services/";


    @GET("bestservices")
    Call<List<Restaurant>> getBetterServices();

    @GET("allbestservices")
    Call<List<Restaurant>> getAllBetterServices();
}
