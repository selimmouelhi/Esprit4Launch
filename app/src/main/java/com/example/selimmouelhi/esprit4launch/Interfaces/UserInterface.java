package com.example.selimmouelhi.esprit4launch.Interfaces;

import com.example.selimmouelhi.esprit4launch.Utils.Constant;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserInterface {

    String Base_Url = Constant.root_server_user+"/";

    @POST("check")
    Call<User> createFromSocialMedia(@Body User user);

    @GET("/{id}")
    Call<User> getFriendsList(@Path("id") String id_user);


    @GET("{id}")
    Call<User> getUserById(@Path("id") String id_user);


    @DELETE("delete/{id}")
    Call<Void> deleteUser(@Path("id") String id_user);

    @GET("all/{key}/{id}")
    Call<List<User>> searchUser(@Path("key") String prenom,@Path("id") String id);


    @POST("logout")
    Call<Void> logout(@Body JsonObject body);

    @Multipart
    @POST("update_photo")
    Call<String> updatePhoto(@Part MultipartBody.Part image,
                             @Part("user_id") RequestBody userId);


}
