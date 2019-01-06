package com.example.selimmouelhi.esprit4launch.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
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

import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.fragments.FriendsFrag;
import com.example.selimmouelhi.esprit4launch.fragments.HomeFragm;
import com.example.selimmouelhi.esprit4launch.fragments.HomescreenFragment;
import com.example.selimmouelhi.esprit4launch.fragments.MyFriends;
import com.example.selimmouelhi.esprit4launch.fragments.ProfileFragment;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.appbottom);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Holy Moly");
        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.parseColor("white"));
        toolbar.setSubtitleTextColor(Color.parseColor("white"));
        toolbar.setSubtitle("welcome");
        navigationView = findViewById(R.id.navigation);
        View header = navigationView.getHeaderView(0);
        profpicture = header.findViewById(R.id.profpicture);
        nameprenomprof = header.findViewById(R.id.nameprof);
        emailprof = header.findViewById(R.id.emailprof);
        facebooklog = header.findViewById(R.id.facebook);
        googlelog = header.findViewById(R.id.google);
        setupDrawerContent(navigationView);



        //get data from sharedpreferences



        String image_url = this.getSharedPreferences(MainActivity.PREFS_NAME, this.MODE_PRIVATE).getString(MainActivity.PREF_IMAGE_URL, null);
        String name= this.getSharedPreferences(MainActivity.PREFS_NAME, this.MODE_PRIVATE).getString(MainActivity.PREF_Name, null);
        String prenom = this.getSharedPreferences(MainActivity.PREFS_NAME, this.MODE_PRIVATE).getString(MainActivity.PREF_prenom, null);
         String method = this.getSharedPreferences(MainActivity.PREFS_NAME, this.MODE_PRIVATE).getString(MainActivity.PREF_SIGNIN_METHOD, null);
        String email =  this.getSharedPreferences(MainActivity.PREFS_NAME, this.MODE_PRIVATE).getString(MainActivity.PREF_mail, null);

        Picasso.with(this).load(image_url).into(profpicture);
        nameprenomprof.setText(prenom+" "+ name);
        emailprof.setText(email);

        if(method.equals("GOOGLE"))
        {
            googlelog.setVisibility(View.VISIBLE);
            facebooklog.setVisibility(View.INVISIBLE);


        }
        else{
            googlelog.setVisibility(View.INVISIBLE);
            facebooklog.setVisibility(View.VISIBLE);

        }


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        HomescreenFragment lf = new HomescreenFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,lf).commit();

                        return  true;
                    case R.id.profile:
                        ProfileFragment pf = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,pf).commit();
                        return  true;
                    case R.id.restaurant:
                        Intent intent =  new Intent(getApplicationContext(),RestaurantActivity.class);
                        startActivity(intent);

                        return  true;
                    case R.id.Cafe:
                        System.out.println("here cafe");


                        return  true;
                    case R.id.friends:

                        MyFriends ff = new MyFriends();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,ff).commit();


                        return  true;

                    default:
                        break;



                }
                return  false;
            }
        });
        HomescreenFragment lf = new HomescreenFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout,lf).commit();


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

    private void selectItemDrawer(MenuItem menuItem){

        switch (menuItem.getItemId()){
            case R.id.Homeid:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                break;
            case R.id.settings:
                Toast.makeText(this, "pressing on logout", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                break;
            case R.id.notifications:
                Toast.makeText(this, "pressing on logout", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                break;
            case R.id.logout:
                Toast.makeText(this, "pressing on logout", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawers();
                break;

        }

    }
    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                selectItemDrawer(menuItem);
                return true;
            }
        });
    }


}
