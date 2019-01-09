package com.example.selimmouelhi.esprit4launch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selimmouelhi.esprit4launch.Interfaces.UserInterface;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.example.selimmouelhi.esprit4launch.fragments.FriendsFrag;
import com.example.selimmouelhi.esprit4launch.fragments.followernotif;
import com.example.selimmouelhi.esprit4launch.fragments.friendnotif;
import com.example.selimmouelhi.esprit4launch.fragments.HomescreenFragment;
import com.example.selimmouelhi.esprit4launch.fragments.MyFriends;
import com.example.selimmouelhi.esprit4launch.fragments.ProfileFragment;
import com.example.selimmouelhi.esprit4launch.fragments.SettingsFragment;
import com.example.selimmouelhi.esprit4launch.fragments.followerfollowingfriendFrag;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeActivity extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener{

    private GoogleSignInClient mGoogleSignInClient;
    Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    TextView nameprenomprof;
    TextView emailprof;
    CircleImageView profpicture;
    ImageView facebooklog;
    ImageView googlelog;
    public GoogleApiClient googleApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.appbottom);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Holy Moly");
        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setTitleTextColor(Color.parseColor("white"));
        //toolbar.setSubtitleTextColor(Color.parseColor("white"));
        //toolbar.setSubtitle("welcome");
        navigationView = findViewById(R.id.navigation);
        View header = navigationView.getHeaderView(0);
        profpicture = header.findViewById(R.id.profpicture);
        nameprenomprof = header.findViewById(R.id.nameprof);
        emailprof = header.findViewById(R.id.emailprof);
        facebooklog = header.findViewById(R.id.facebook);
        googlelog = header.findViewById(R.id.google);
        String id = this.getSharedPreferences(MainActivity.PREFS_NAME, this.MODE_PRIVATE).getString(MainActivity.PREF_USER_ID, null);

        setupDrawerContent(navigationView,id);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        //get data from sharedpreferences


        //get user id notification sender
        String follower_notif_id = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE).getString("idfollowernotif", null);
        String friend_notif_id = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE).getString("idfriendnotif", null);
        if (follower_notif_id != null)
        {
            System.out.println(follower_notif_id + "in fkin hell");
            followernotif  followernotif = new followernotif();

            followernotif.setId(follower_notif_id);
            SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE).edit();
            editor.remove("idfollowernotif");
            editor.commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, followernotif).commit();


        }

        else if (friend_notif_id != null)
        {
            System.out.println(friend_notif_id + "in fkin hell");
            friendnotif  friendnotif = new friendnotif();

            friendnotif.setId(friend_notif_id);
            SharedPreferences.Editor editor = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE).edit();
            editor.remove("idfriendnotif");
            editor.commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, friendnotif).commit();


        }
        else {


            String image_url = this.getSharedPreferences(MainActivity.PREFS_NAME, this.MODE_PRIVATE).getString(MainActivity.PREF_IMAGE_URL, null);
            String name = this.getSharedPreferences(MainActivity.PREFS_NAME, this.MODE_PRIVATE).getString(MainActivity.PREF_Name, null);
            String prenom = this.getSharedPreferences(MainActivity.PREFS_NAME, this.MODE_PRIVATE).getString(MainActivity.PREF_prenom, null);
            String method = this.getSharedPreferences(MainActivity.PREFS_NAME, this.MODE_PRIVATE).getString(MainActivity.PREF_SIGNIN_METHOD, null);
            String email = this.getSharedPreferences(MainActivity.PREFS_NAME, this.MODE_PRIVATE).getString(MainActivity.PREF_mail, null);

            Picasso.with(this).load(image_url).into(profpicture);
            nameprenomprof.setText(prenom + " " + name);
            emailprof.setText(email);

            if (method.equals("GOOGLE")) {
                googlelog.setVisibility(View.VISIBLE);
                facebooklog.setVisibility(View.INVISIBLE);


            } else {
                googlelog.setVisibility(View.INVISIBLE);
                facebooklog.setVisibility(View.VISIBLE);

            }


            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.home:
                            HomescreenFragment lf = new HomescreenFragment();
                            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.framelayout, lf).commit();

                            return true;
                        case R.id.profile:
                            ProfileFragment pf = new ProfileFragment();
                            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.framelayout, pf).commit();
                            return true;
                        case R.id.restaurant:
                            Intent intent = new Intent(getApplicationContext(), RestaurantActivity.class);
                            startActivity(intent);

                            return true;

                        case R.id.friends:

                            MyFriends ff = new MyFriends();
                            getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.framelayout, ff).commit();


                            return true;

                        default:
                            break;


                    }
                    return false;
                }
            });
            HomescreenFragment lf = new HomescreenFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.framelayout, lf).commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.favoritee){
            Toast.makeText(this,"favorites lets go",Toast.LENGTH_SHORT).show();
        }
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return  true;
        }

        return  super.onOptionsItemSelected(item);
    }

    private void selectItemDrawer(MenuItem menuItem,String id){

        switch (menuItem.getItemId()){
            case R.id.Homeid:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                break;
            case R.id.settings:
                Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofit.create(UserInterface.class);
                Call<User> call = userInterface.getUserById(id);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {


                        System.out.println(response.body().getNom()+"in profile frag");
                        SettingsFragment settingsFragment = new SettingsFragment();
                        settingsFragment.setUser(response.body());
                        FragmentManager manager = getSupportFragmentManager();

                        manager.beginTransaction().replace(R.id.framelayout,settingsFragment).commit();
                        drawerLayout.closeDrawers();


                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println("failureee");

                    }
                });


                break;

            case R.id.logout:
               signout();
                drawerLayout.closeDrawers();
                break;

        }

    }
    private void setupDrawerContent(NavigationView navigationView,String id){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectItemDrawer(menuItem,id);
                return true;
            }
        });
    }

    private void gotonotiffragmentfollower(Bundle b ,String id ){

        if(id != null){
            followernotif  followernotif = new followernotif();
            System.out.println(id + " if not bundle and not null");
            followernotif.setId(b.getString("id_follower"));
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, followernotif).commit();

        }

    }


    private void signout() {

        String uuid = getSharedPreferences(getString(R.string.prefs_name), Context.MODE_PRIVATE)
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
                    SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googleApiClient.stopAutoManage(this);
        googleApiClient.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(this);
        googleApiClient.disconnect();
    }
}
