package com.example.selimmouelhi.esprit4launch.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.fragments.FriendsFrag;
import com.example.selimmouelhi.esprit4launch.fragments.HomeFragm;
import com.example.selimmouelhi.esprit4launch.fragments.HomescreenFragment;
import com.example.selimmouelhi.esprit4launch.fragments.MyFriends;
import com.example.selimmouelhi.esprit4launch.fragments.ProfileFragment;


public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.appbottom);
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
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Holy Moly");
        toolbar.setSubtitle("welcome");

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

        return  super.onOptionsItemSelected(item);
    }


}
