package com.example.selimmouelhi.esprit4launch.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.fragments.suggestedMoreFrag;

public class ViewMore extends AppCompatActivity {

    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = findViewById(R.id.titlee);
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle extras = getIntent().getExtras();
        String state = extras.getString("state");

        switch (state){
            case "topratedres":
                title.setText("Top Rated Restaurants");
                toolbar.setTitle("Top rated Restaurants");
                break;
            case "tasty":
                title.setText("Most Tasty Restaurants");
                toolbar.setTitle("Most Tasty Restaurants");
                break;
            case "service":
                title.setText("Best Service Restaurants");
                toolbar.setTitle("Best Service Restaurants");
                break;




        }

        System.out.println(state+" in activity");

        Bundle bundle=new Bundle();
        bundle.putString("state",state);
        //set Fragmentclass Arguments
        suggestedMoreFrag fragobj=new suggestedMoreFrag();
        fragobj.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.fragview,fragobj).commit();
    }


}
