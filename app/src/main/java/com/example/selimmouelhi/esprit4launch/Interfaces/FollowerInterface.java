package com.example.selimmouelhi.esprit4launch.Interfaces;

import com.example.selimmouelhi.esprit4launch.Utils.Constant;
import com.example.selimmouelhi.esprit4launch.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FollowerInterface {


    String Base_Url = Constant.root_server_followers+"/";

    @GET("following_list/{id}")
    Call<List<User>> getFollowing(@Path("id") String id);

    @GET("follower_list/{id}")
    Call<List<User>> getFollowers(@Path("id") String id);

    @GET("is_following/{id1}/{id2}")
    Call<Boolean> isFollowing(@Path("id1") String followerId, @Path("id2") String followedId);

    @POST("follow/{id1}/{id2}")
    Call<Void> follow(@Path("id1") String followerId, @Path("id2") String followedId);

    @DELETE("unfollow/{id1}/{id2}")
    Call<Void> unfollow(@Path("id1") String followerId, @Path("id2") String followedId);

}
