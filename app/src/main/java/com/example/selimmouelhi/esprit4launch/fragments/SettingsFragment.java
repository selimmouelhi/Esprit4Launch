package com.example.selimmouelhi.esprit4launch.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.selimmouelhi.esprit4launch.Interfaces.FollowerInterface;
import com.example.selimmouelhi.esprit4launch.Interfaces.UserInterface;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.activities.HomeActivity;
import com.example.selimmouelhi.esprit4launch.activities.MainActivity;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    ImageView imageView;
   private User user ;
   TextView delete;
   TextView nameprof;
    private GoogleApiClient googleApiClient;
    private GoogleSignInClient mGoogleSignInClient;




    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient =((HomeActivity) getActivity()).googleApiClient;

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        imageView = view.findViewById(R.id.testcouverture);
        nameprof = view.findViewById(R.id.nameprof);
        delete = view.findViewById(R.id.delete);
        System.out.println(user.getCouverture_url());
        //Picasso.get().load(user.getCouverture_url()).into(imageView);
        Glide.with(view).load(user.getImage()).into(imageView);
        nameprof.setText(user.getPrenom() + " " + user.getNom());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertbuilder = new AlertDialog.Builder(getContext());
                alertbuilder.setMessage("Do you really want to delete your account")
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        })
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                Retrofit retrofitcall = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                UserInterface userInterfacedelete = retrofitcall.create(UserInterface.class);
                                Call<Void> calldelete = userInterfacedelete.deleteUser(user.getId());
                                calldelete.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        goTohome();

                                        //signoutGoogle
                                        if (googleApiClient.isConnected()) {
                                            System.out.println("connected google");
                                            Auth.GoogleSignInApi.signOut(googleApiClient);
                                            googleApiClient.disconnect();
                                            googleApiClient.connect();
                                            goTohome();
                                        }


                                        //facebooksignout
                                        AccessToken token = AccessToken.getCurrentAccessToken();

                                        if (AccessToken.getCurrentAccessToken() == null) {
                                            return; // already logged out
                                        } else {
                                            SharedPreferences sharedPreferences = getContext().getSharedPreferences(MainActivity.PREFS_NAME, getActivity().MODE_PRIVATE);
                                            sharedPreferences.edit().clear().commit();
                                            System.out.println("connected fb");
                                            goTohome();
                                            LoginManager.getInstance().logOut();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });




                            }
                        });


                AlertDialog alert = alertbuilder.create();
                alert.setTitle("Alert");
                alert.show();
            }
        });



        return  view;
    }

    public void setUser(User user) {
        this.user = user;
    }
    private void goTohome(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
    /*
    *
*/

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }
}
