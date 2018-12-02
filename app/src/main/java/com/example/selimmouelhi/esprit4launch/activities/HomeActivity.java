package com.example.selimmouelhi.esprit4launch.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.fragments.HomeFragm;
import com.example.selimmouelhi.esprit4launch.fragments.HomescreenFragment;


public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
