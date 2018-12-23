package com.example.selimmouelhi.esprit4launch.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.adapters.RestaurantAdapter;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    private String url = "http://10.0.2.2:8888/services/restaurant.php";
    private List<Restaurant> RestList;
    private RestaurantAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_list);
        ListView mList = findViewById(R.id.list);

        RestList = new ArrayList<>();
        adapter = new RestaurantAdapter(RestList,this);
        mList.setAdapter(adapter);
        getData();

    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        Restaurant restaurant = new Restaurant();
                        restaurant.setId(jsonObject.getInt("id"));
                        restaurant.setName(jsonObject.getString("name"));
                        restaurant.setAdresse(jsonObject.getString("adresse"));
                        restaurant.setDescription(jsonObject.getString("description"));
                        restaurant.setRating((float) jsonObject.getDouble("rating"));
                        restaurant.setImageUrl(jsonObject.getString("image"));
                        restaurant.setRadius((float)jsonObject.getDouble("radius"));

                        RestList.add(restaurant);
                        System.out.println(RestList);
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
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}
