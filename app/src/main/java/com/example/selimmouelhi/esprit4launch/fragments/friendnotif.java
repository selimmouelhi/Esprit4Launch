package com.example.selimmouelhi.esprit4launch.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selimmouelhi.esprit4launch.Interfaces.FriendsInterface;
import com.example.selimmouelhi.esprit4launch.Interfaces.UserInterface;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.activities.HomeActivity;
import com.example.selimmouelhi.esprit4launch.activities.MainActivity;
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
public class friendnotif extends Fragment {
    private ImageView profpicture ;
    private TextView profname ;
    private Button acceptnotif;
    private Button declinenotif;
    private Button viewprof;
    private  String id ;

    public friendnotif() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_friendnotif, container, false);
        profname = view.findViewById(R.id.namenotif);
        profpicture = view.findViewById(R.id.imagenotif);
        viewprof = view.findViewById(R.id.viewprofilenotif);
        acceptnotif = view.findViewById(R.id.acceptnotif);
        declinenotif = view.findViewById(R.id.declinenotif);


        //get user who sent

        String id_current = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, getActivity().MODE_PRIVATE).getString(MainActivity.PREF_USER_ID, null);

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

        acceptnotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofit.create(UserInterface.class);
                Call<User> call = userInterface.getUserById(id);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        //get current user
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                .build();
                        UserInterface userInterface = retrofit.create(UserInterface.class);
                        Call<User> currentuser = userInterface.getUserById(id_current);
                        currentuser.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                //accept friend

                                Retrofit retrofitaccept = new Retrofit.Builder().baseUrl(FriendsInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                FriendsInterface friendsInterface = retrofitaccept.create(FriendsInterface.class);


                                Call<Void> acceptcall = friendsInterface.addFriendNotif(response.body().getId(),id);
                                acceptcall.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast.makeText(getContext(), "done accept sumé", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });




                                //add friend sumeltanément




                                Call<Void> acceptsumelta = friendsInterface.addFriend(id,response.body().getId());
                                acceptsumelta.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Toast.makeText(getContext(), "done accept sumélta", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                                Intent i = new Intent(getActivity(),HomeActivity.class);
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });






                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), "failure in getting user by id", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        declinenotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),HomeActivity.class);
                startActivity(i);
            }
        });
        return  view ;

    }


    public void setId(String id) {
        this.id = id;
    }
}
