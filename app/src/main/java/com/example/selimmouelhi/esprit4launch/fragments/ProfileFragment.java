package com.example.selimmouelhi.esprit4launch.fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.activities.HomeActivity;
import com.example.selimmouelhi.esprit4launch.activities.MainActivity;
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
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener{
    private ImageView userImage;
    private TextView methodsignin;
    private TextView nameUser;
    private GoogleSignInClient mGoogleSignInClient;
    private Button signout ;


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



        String image_url = activity.getSharedPreferences(MainActivity.PREFS_NAME, activity.MODE_PRIVATE).getString(MainActivity.PREF_IMAGE_URL, null);
        String name= activity.getSharedPreferences(MainActivity.PREFS_NAME, activity.MODE_PRIVATE).getString(MainActivity.PREF_Name, null);
        String prenom = activity.getSharedPreferences(MainActivity.PREFS_NAME, activity.MODE_PRIVATE).getString(MainActivity.PREF_prenom, null);
        final String method = activity.getSharedPreferences(MainActivity.PREFS_NAME, activity.MODE_PRIVATE).getString(MainActivity.PREF_SIGNIN_METHOD, null);

        nameUser.setText(prenom+" "+name);
        methodsignin.setText("Signed in with "+method);
        Picasso.with(getContext()).load(image_url).into(userImage);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signout();
            }
        });

        return view ;

    }

    private void signout(){

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
        }

        else {
            SharedPreferences sharedPreferences = getContext()  .getSharedPreferences(MainActivity.PREFS_NAME,getActivity().MODE_PRIVATE);
            sharedPreferences.edit().clear().commit();
            goTohome();
            LoginManager.getInstance().logOut();
        }






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
