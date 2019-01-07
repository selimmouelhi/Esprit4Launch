package com.example.selimmouelhi.esprit4launch.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selimmouelhi.esprit4launch.Interfaces.FollowerInterface;
import com.example.selimmouelhi.esprit4launch.Interfaces.UserInterface;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.activities.HomeActivity;
import com.example.selimmouelhi.esprit4launch.activities.MainActivity;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener{
    private ImageView userImage;
    private TextView methodsignin;
    private TextView nameUser;
    private GoogleSignInClient mGoogleSignInClient;
    private Button signout ;
    TextView emailProf;
    TextView phoneProfile;
    TextView numberofFriends;
    TextView numberofFollowers;
    TextView numberofFavorites;
    LinearLayout followers;





    private GoogleApiClient googleApiClient;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        Activity activity = getActivity();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(getContext()).enableAutoManage(getActivity(),this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);

        userImage = view.findViewById(R.id.profile_picture);
        nameUser = view.findViewById(R.id.name_text);
        methodsignin = view.findViewById(R.id.method_text);
        signout = view.findViewById(R.id.signoutProf);
        emailProf = view.findViewById(R.id.emailProfile);
        phoneProfile = view.findViewById(R.id.phone_profile);
        numberofFavorites = view.findViewById(R.id.numberFavorites);
        numberofFriends = view.findViewById(R.id.numberFriends);
        numberofFollowers = view.findViewById(R.id.numberFollowers);
        followers = view.findViewById(R.id.followerslinear);

        String id = activity.getSharedPreferences(MainActivity.PREFS_NAME, activity.MODE_PRIVATE).getString(MainActivity.PREF_USER_ID, null);
        System.out.println(id+"in proffrag");

        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = ProgressDialog.show(getContext(),"blabla","blala");

                Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofit.create(UserInterface.class);
                Call<User> call = userInterface.getUserById(id);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        progressDialog.dismiss();

                        System.out.println(response.body().getNom()+"in profile frag");
                        followerfollowingfriendFrag followerfollowingfriendFrag = new followerfollowingfriendFrag();
                        followerfollowingfriendFrag.setUser(response.body());
                        FragmentManager manager = getActivity().getSupportFragmentManager();

                        manager.beginTransaction().replace(R.id.framelayout,followerfollowingfriendFrag).commit();

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), "failure in getting user by id", Toast.LENGTH_SHORT).show();

                    }
                });



            }

        });


        String image_url = activity.getSharedPreferences(MainActivity.PREFS_NAME, activity.MODE_PRIVATE).getString(MainActivity.PREF_IMAGE_URL, null);
        String name= activity.getSharedPreferences(MainActivity.PREFS_NAME, activity.MODE_PRIVATE).getString(MainActivity.PREF_Name, null);
        String prenom = activity.getSharedPreferences(MainActivity.PREFS_NAME, activity.MODE_PRIVATE).getString(MainActivity.PREF_prenom, null);
        final String method = activity.getSharedPreferences(MainActivity.PREFS_NAME, activity.MODE_PRIVATE).getString(MainActivity.PREF_SIGNIN_METHOD, null);
        String email =  activity.getSharedPreferences(MainActivity.PREFS_NAME, activity.MODE_PRIVATE).getString(MainActivity.PREF_mail, null);
        int phone =  activity.getSharedPreferences(MainActivity.PREFS_NAME, activity.MODE_PRIVATE).getInt(MainActivity.PREF_phone, 0);
        int friends =  activity.getSharedPreferences(MainActivity.PREFS_NAME, activity.MODE_PRIVATE).getInt(MainActivity.PREF_friends, 0);
        int favorites =  activity.getSharedPreferences(MainActivity.PREFS_NAME, activity.MODE_PRIVATE).getInt(MainActivity.PREF_favorites, 0);
        int followers =  activity.getSharedPreferences(MainActivity.PREFS_NAME, activity.MODE_PRIVATE).getInt(MainActivity.PREF_followers, 0);


        //get current user

        Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                .build();
        UserInterface userInterface = retrofit.create(UserInterface.class);
        Call<User> call = userInterface.getUserById(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> user, Response<User> response) {




                emailProf.setText(response.body().getMail());
                phoneProfile.setText(Integer.toString(response.body().getPhone()));

                numberofFollowers.setText(Integer.toString(response.body().getFollowers()));
                numberofFriends.setText(Integer.toString(response.body().getFriends()));
                numberofFavorites.setText(Integer.toString(response.body().getFavorites()));
                nameUser.setText(response.body().getPrenom()+" "+response.body().getNom());
                methodsignin.setText("Signed in with "+method);
                Picasso.with(getContext()).load(response.body().getImage()).into(userImage);






            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "failure in getting user by id", Toast.LENGTH_SHORT).show();

            }
        });






        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });




        return view ;

    }

    private void signout() {

        String uuid = getActivity().getSharedPreferences(getString(R.string.prefs_name), Context.MODE_PRIVATE)
                .getString(getString(R.string.prefs_uuid), null);
        JsonObject body = new JsonObject();
        body.addProperty("uuid", uuid);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                .build();
        UserInterface userInterface = retrofit.create(UserInterface.class);
        Call<Void> logoutcall = userInterface.logout(body);
        logoutcall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {


                //signoutGoogle
                if (googleApiClient.isConnected()) {
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
                    goTohome();
                    LoginManager.getInstance().logOut();
                }


            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }








    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {



    }
    private void goTohome(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }
}
