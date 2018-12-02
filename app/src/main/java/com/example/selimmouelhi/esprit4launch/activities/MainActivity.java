package com.example.selimmouelhi.esprit4launch.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.selimmouelhi.esprit4launch.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN =9001;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient googleApiClient;
    private SignInButton signInButton;
    private TextView nameView;
    private TextView emailView;
    private TextView urlView;
    private Button log_out;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        nameView = findViewById(R.id.name);
        emailView = findViewById(R.id.email);
        urlView = findViewById(R.id.url);
        log_out = findViewById(R.id.log_out);
        loginButton = findViewById(R.id.login_button);
        button2 = findViewById(R.id.button2);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        System.out.println(account);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);
        log_out.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        button2.setOnClickListener(this);


        printKeyHash();






    ;
}

private void signInFacebook(){

    callbackManager = CallbackManager.Factory.create();
    loginButton.setReadPermissions(Arrays.asList("public_profile","email","user_birthday"));
    loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            String accessToken = loginResult.getAccessToken().getToken();
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    Log.d("response ",response.toString());
                    getData(object);

                }
            });
            Bundle parametres = new Bundle();
            parametres.putString("fields","id,email,birthday,friends");
            request.setParameters(parametres);
            request.executeAsync();

        }





        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    });

    if (AccessToken.getCurrentAccessToken()!= null){

        nameView.setText(AccessToken.getCurrentAccessToken().getUserId());
    }



}

    private void getData(JSONObject object) {
        try {
            /*URL profile_picture = new URL("htttps://graph.facebook.com/"+object.getString("id")+"/picture?width=250&height=250");
            Picasso.with(this).load(profile_picture.toString())into();*/
            emailView.setText(object.getString("email"));
            nameView.setText(object.getString("first_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void printKeyHash() {
        try {
            PackageInfo info =getPackageManager().getPackageInfo("com.example.selimmouelhi.esprit4launch",PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature :info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyBash",Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }
        else
        {
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    private void signIn(){
    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
    startActivityForResult(signInIntent,RC_SIGN_IN);
    }
    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false  );
            }
        });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){

            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            nameView.setText(name);
            emailView.setText(email);
            urlView.setText("blabla");
            //Glide.with(this).load(img_url).into(image id); how to download image url
            System.out.println(name+email);
            updateUI(true);
        }
        else {
            updateUI(false);
        }
    }



    private void updateUI(boolean islogin) {
    if(islogin){

        signInButton.setVisibility(View.GONE);
        log_out.setVisibility(View.VISIBLE);
    }
        else
    {
        signInButton.setVisibility(View.VISIBLE);
    }

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.log_out:
                signOut();
            break;
            case R.id.login_button:
                signInFacebook();
                break;
            case R.id.button2:
                Intent intent = new Intent(this,HomeActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

