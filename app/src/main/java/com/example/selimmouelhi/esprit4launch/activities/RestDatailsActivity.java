package com.example.selimmouelhi.esprit4launch.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.selimmouelhi.esprit4launch.DataHelper.FeedReaderDbHelper;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.Utils.ImageLoader;
import com.squareup.picasso.Picasso;

public class RestDatailsActivity extends AppCompatActivity {
    Integer restid;
    String name;
    String imageurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_datails);
        ImageView RestImg = findViewById(R.id.RestImg);
        ImageView clock = findViewById(R.id.clock);
        clock.setImageResource(R.drawable.watch);
        ImageView heart = findViewById(R.id.heart);
        heart.setImageResource(R.drawable.heart);
        ImageView location = findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),MapActivity.class);
                startActivity(i);
            }
        });

        location.setImageResource(R.drawable.marker);
        TextView RestName = findViewById(R.id.name);
        TextView RestDesc = findViewById(R.id.Description);
        TextView RestAdresse= findViewById(R.id.adresse);
        TextView Restradius= findViewById(R.id.radius);
        Intent i =getIntent();
        restid =i.getIntExtra("id",0);
         imageurl =i.getStringExtra("image");
         name =i.getStringExtra("name");
        String description =i.getStringExtra("description");
        String adresse =i.getStringExtra("adresse");
        double rating =i.getDoubleExtra("rating",0.0);
        double radius =i.getDoubleExtra("radius",0.0);
        System.out.println(restid);
        Picasso.with(this).load(ImageLoader.url_image+imageurl+ImageLoader.API_Key).into(RestImg);
        RestName.setText(name);
        Restradius.setText(String.valueOf(radius));
        RestAdresse.setText(adresse);
        RestDesc.setText(description);
        Button reviews = findViewById(R.id.review);
        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),RestReviewsActivity.class);
                i.putExtra("id",restid);
                startActivity(i);
            }
        });

    Button AddFavorite =findViewById(R.id.favorite);
        AddFavorite.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(mDbHelper.COLUMN_NAME_TITLE, name);
        values.put(mDbHelper.COLUMN_NAME_ID, restid.toString());
        values.put(mDbHelper.COLUMN_NAME_IMAGE,imageurl);

        long newRowId = db.insert(mDbHelper.TABLE_NAME, null, values);
        System.out.println("Here"+values);

    }
});


    }
}
