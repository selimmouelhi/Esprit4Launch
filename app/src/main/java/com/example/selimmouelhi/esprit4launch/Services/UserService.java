package com.example.selimmouelhi.esprit4launch.Services;

import android.telecom.Call;
import android.util.Log;

import com.example.selimmouelhi.esprit4launch.Interfaces.UserInterface;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {


    private static final String TAG = "UserService";

    private static UserService instance;

    private UserInterface userInterface;

    public static UserService getInstance(){
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }

    private UserService(){

        Gson gson = new Gson().newBuilder().setLenient().create();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3003/users/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        userInterface = retrofit.create(UserInterface.class);
    }

    public void createFromSocialMedia(User user, final CreateFromSocialMediaCallBack callBack){
        System.out.println(user.getNom()+" lenna");
        retrofit2.Call<User> call = userInterface.createFromSocialMedia(user);
        call.enqueue(new Callback<User>() {


            @Override
            public void onResponse(retrofit2.Call<User> call, Response<User> response) {

                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: Error " + response.errorBody());
                    callBack.onCompletion(null);
                    System.out.println("error in serviceuser");
                    return;
                }
                Log.d(TAG, "onResponse: body: " + response.body());
                System.out.println(response.body().getNom()+"in t3adina ");
                System.out.println(response.body().toString()+"helpp");
                callBack.onCompletion(response.body());
                System.out.println("c bon t3adina");


            }


            @Override
            public void onFailure(retrofit2.Call<User> call, Throwable t) {


                Log.e(TAG, "onFailure: ", t);
                callBack.onCompletion(null);

            }
        });
    }


    public interface CreateFromSocialMediaCallBack{
        void onCompletion(User user);
    }


}
