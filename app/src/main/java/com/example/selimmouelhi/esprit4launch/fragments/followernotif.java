package com.example.selimmouelhi.esprit4launch.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selimmouelhi.esprit4launch.Interfaces.UserInterface;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class followernotif extends Fragment {

    private ImageView profpicture ;
    private TextView profname ;
    private Button viewprof;
    private  String id ;


    public followernotif() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_followernotif, container, false);

        profname = view.findViewById(R.id.namenotif);
        profpicture = view.findViewById(R.id.imagenotif);
        viewprof = view.findViewById(R.id.viewprofilenotif);


        //get user who sent


        Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                .build();
        UserInterface userInterface = retrofit.create(UserInterface.class);
        Call<User> call = userInterface.getUserById(this.id);
        System.out.println(this.id+" in fragment follower");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {




               profname.setText(response.body().getPrenom()+" " + response.body().getNom());
                Picasso.with(getContext()).load(response.body().getImage()).into(profpicture);


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "failure in getting user by id", Toast.LENGTH_SHORT).show();

            }
        });

        viewprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofit.create(UserInterface.class);
                Call<User> call = userInterface.getUserById(id);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {




                       FriendProfile friendProfile = new FriendProfile();
                       friendProfile.setUser(response.body());
                       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,friendProfile).commit();


                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), "failure in getting user by id", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        return  view ;

    }

    public void setId(String id) {
        this.id = id;
    }

}
