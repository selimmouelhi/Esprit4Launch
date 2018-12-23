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

public interface UserInterface {

    String Base_Url = Constant.root_server_user+"/";

    @POST("check")
    Call<User> createFromSocialMedia(@Body User user);

    @POST("insert")
    Call<JsonObject> createFromEmail(@Body User user);
}
