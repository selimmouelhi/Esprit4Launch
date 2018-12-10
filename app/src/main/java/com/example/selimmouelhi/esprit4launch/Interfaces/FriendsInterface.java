package com.example.selimmouelhi.esprit4launch.Interfaces;

import com.example.selimmouelhi.esprit4launch.Utils.Constant;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;
import com.example.selimmouelhi.esprit4launch.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FriendsInterface {


    String Base_Url = Constant.root_server_friends+"web_services/";

    @GET("friends/{id}")
    Call<List<User>> getFriendsList(@Path("id") String id_user);

    @DELETE("deletefriend/{id_user}/{id_friend}")
    Call<Void> deleteFriend(@Path("id_user")String id_user,@Path("id_friend")String id_friend);


    @POST("deletefriend/{id_user}/{id_friend}")
    Call<Void> addFriend(@Path("id_user")String id_user,@Path("id_friend")String id_friend);



}
