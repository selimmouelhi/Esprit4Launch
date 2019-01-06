package com.example.selimmouelhi.esprit4launch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.nfc.Tag;
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
import com.example.selimmouelhi.esprit4launch.Services.UserService;
import com.example.selimmouelhi.esprit4launch.entities.User;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.util.Arrays;
import java.util.UUID;

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
    private static final String TAG = "MainActivity";

    public static final String PREFS_NAME = "prefs";
    public static final String PREF_USER_ID = "user_id";
    public static final String PREF_SIGNIN_METHOD = "signin_method";
    public static final String METHOD_FACEBOOK = "FACEBOOK";
    public static final String METHOD_GOOGLE = "GOOGLE";
    public static final String PREF_IMAGE_URL = "image_url";
    public static final String PREF_Name = "name";
    public static final String PREF_prenom = "prenom";
    public static final String PREF_mail = "mail";
    public static final String PREF_phone = "phone";
    public static final String PREF_followers = "followers";
    public static final String PREF_friends = "friends";
    public static final String PREF_favorites = "favorites";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        nameView = findViewById(R.id.name);
        emailView = findViewById(R.id.email);
        urlView = findViewById(R.id.url);
        loginButton = findViewById(R.id.login_button);






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
        loginButton.setOnClickListener(this);


        //printKeyHash();






    ;
}

// sign in facebook and getting data to our user
private void signInFacebook(){

    callbackManager = CallbackManager.Factory.create();
    loginButton.setReadPermissions(Arrays.asList("public_profile","email","user_birthday"));
    loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(final LoginResult loginResult) {
            String accessToken = loginResult.getAccessToken().getToken();
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    Log.d("response ",response.toString());
                    User user = new User();
                    try {
                        user.setId("f_" + object.getString("id"));
                        user.setNom(object.getString("first_name"));
                        user.setPrenom(object.getString("last_name"));
                        user.setMail(object.getString("email"));
                        user.setImage("https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?height=600");
                        Log.d(TAG, "onCompleted: user: " + user);
                        loginOrCreateFromSocialMedia(user, METHOD_FACEBOOK);
                    }catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
            });
            Bundle parametres = new Bundle();
            parametres.putString("fields","id,email,first_name,last_name,birthday,friends");
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
    /*
    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false  );
            }
        });
    }
    */


    // get data from google and insert it to our user
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){

            GoogleSignInAccount account = result.getSignInAccount();
            User user = new User();
            user.setId("g_" + account.getId());
            user.setMail(account.getEmail());
            user.setNom(account.getFamilyName());
            user.setPrenom(account.getGivenName());
            user.setImage(account.getPhotoUrl().toString());
            System.out.println("g_" + account.getId());
            System.out.println(account.getEmail());
            System.out.println(account.getPhotoUrl().toString());
            loginOrCreateFromSocialMedia(user, METHOD_GOOGLE);
            //Glide.with(this).load(img_url).into(image id); how to download image url

        }
        else {
            System.out.println("error in handle sign in result");
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
                if(isLoggedIn()){
                    goToHome();
                    return;
                }
                else{
                signIn();}
                break;


            case R.id.login_button:
                if(isLoggedIn()){
                    goToHome();
                    return;
                }
                else{
                signInFacebook();}
                break;

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void loginOrCreateFromSocialMedia(final User user,  String signInMethod) {


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                Log.d(TAG, "onSuccess: " + token);
                String uuid = getSharedPreferences(getString(R.string.prefs_name), Context.MODE_PRIVATE)
                        .getString(getString(R.string.prefs_uuid), null);
                Log.i(TAG, "onSuccess: uuid = " + uuid);
                if (uuid == null) {
                    uuid = UUID.randomUUID().toString();
                    getSharedPreferences(getString(R.string.prefs_name), Context.MODE_PRIVATE)
                            .edit().putString(getString(R.string.prefs_uuid), uuid).apply();
                }
                user.setmUuid(uuid);
                user.setmToken(token);
                user.setType("android");
                UserService.getInstance().createFromSocialMedia(user, new   UserService.CreateFromSocialMediaCallBack() {
                    @Override
                    public void onCompletion(User user) {

                        if(user == null){
                            System.out.println("error in completion");
                            return;
                        }
                        //add to sharef prefernces profile data
                        System.out.println("user is not null");
                        System.out.println(user.toString() + " in main");
                        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                                .edit()
                                .putString(PREF_SIGNIN_METHOD, signInMethod)
                                .putString(PREF_USER_ID, user.getId())
                                .putString(PREF_IMAGE_URL, user.getImage())
                                .putString(PREF_Name, user.getNom())
                                .putString(PREF_prenom, user.getPrenom())
                                .putString(PREF_mail,user.getMail())
                                .putInt(PREF_phone,user.getPhone())
                                .putInt(PREF_followers, user.getFollowers())
                                .putInt(PREF_favorites,user.getFavorites())
                                .putInt(PREF_friends,user.getFriends())
                                .apply();
                        goToHome();
                    }
                });


            }
        });

    }

    private void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

        private boolean isLoggedIn() {
            //Facebook check
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
            if (isLoggedIn) {
                Log.d(TAG, "isLoggedIn: facebook true");
                return true;
            }

            //Google check
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            isLoggedIn = account != null && !account.isExpired();
            if (isLoggedIn) {
                Log.d(TAG, "isLoggedIn: google true");
                return true;
            }
            return  false;

    }
    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                System.out.println("bye");
            }
        });
    }
}

