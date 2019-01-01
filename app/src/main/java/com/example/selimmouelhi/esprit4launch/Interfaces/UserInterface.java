package com.example.selimmouelhi.esprit4launch.Interfaces;

import com.example.selimmouelhi.esprit4launch.Utils.Constant;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserInterface {

    String Base_Url = Constant.root_server_user+"/";

    @POST("check")
    Call<User> createFromSocialMedia(@Body User user);

    @GET("/{id}")
    Call<User> getFriendsList(@Path("id") String id_user);




}
