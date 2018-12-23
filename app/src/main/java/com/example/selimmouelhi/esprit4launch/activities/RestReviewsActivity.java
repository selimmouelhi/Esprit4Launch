package com.example.selimmouelhi.esprit4launch.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.adapters.RestReviewsAdapter;
import com.example.selimmouelhi.esprit4launch.entities.Reviews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestReviewsActivity extends AppCompatActivity {
    private String urlDisplay= "http://10.0.2.2:8888/services/restReviews.php";
    private String urlSave= "http://10.0.2.2:8888/services/saveReviews.php";
    private List<Reviews> ReviewsList;
    private RestReviewsAdapter adapter;
    private static Integer restId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_reviews);
        Intent i = getIntent();
        Integer restId1 =i.getIntExtra("id",0);
        Button saveReview = findViewById(R.id.save);
       // System.out.println(restId1);

        ListView mList = findViewById(R.id.reviewslist);
         getData();
        System.out.println(restId);
        //if(restId1 == restId)


        ReviewsList = new ArrayList<>();
        adapter = new RestReviewsAdapter(ReviewsList,getApplicationContext());


mList.setAdapter(adapter);






saveReview.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


saveData();

    }
});



    }






    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlDisplay, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Reviews review = new Reviews();
                        review.setRest_id(jsonObject.getInt("rest_id"));


                        //System.out.println(restId);
                        review.setComment(jsonObject.getString("comment"));
                        review.setRating((float) jsonObject.getDouble("rating"));

                        ReviewsList.add(review);
                        restId = jsonObject.getInt("rest_id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

private void saveData(){

    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
    StringRequest request = new StringRequest(Request.Method.POST, urlSave, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

            Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
            Log.i("My success",""+response);

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(getApplicationContext(), "my error :"+error, Toast.LENGTH_LONG).show();
            Log.i("My error",""+error);
        }
    }){
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {

            Map<String,String> map = new HashMap<String, String>();
            RatingBar rate =findViewById(R.id.Addrating);
            EditText comnt =findViewById(R.id.addCmnt);
            String comment =comnt.getText().toString();
            float rating=rate.getRating();
            map.put("comment",comment);
            map.put("rating",String.valueOf(rating));
            map.put("rest_id","1");


            return map;
        }
    };
    queue.add(request);

}


    }








