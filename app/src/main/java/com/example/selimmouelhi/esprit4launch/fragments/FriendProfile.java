package com.example.selimmouelhi.esprit4launch.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.selimmouelhi.esprit4launch.Interfaces.FriendsInterface;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.activities.MainActivity;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendProfile extends Fragment {

    TextView nameView;
    ImageView profile_picture;
    Button friendship ;
    private static User user;
    private static String State;
    public FriendProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_friend_profile, container, false);
        nameView = view.findViewById(R.id.name_text);
        profile_picture = view.findViewById(R.id.profile_picture);
        friendship = view.findViewById(R.id.friendunfriend);

        System.out.println(user.getNom()+"in friendprofile");

        nameView.setText(user.getNom()+" "+user.getPrenom());
        Picasso.with(getContext()).load(user.getImage()).into(profile_picture);
        System.out.println(this.State+"in helpme");

        if(this.State.equals("friends")){
            friendship.setText("UNFRIEND");


        }
        else {
            friendship.setText("ADDFRIEND");

        }

        if(friendship.getText().equals("UNFRIEND")){

            friendship.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(FriendsInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                            .build();
                    String user_id = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, getActivity().MODE_PRIVATE).getString(MainActivity.PREF_USER_ID, null);
                    System.out.println(user_id+"user id in friends");
                    System.out.println(user.getId()+"friend id in friends");
                    FriendsInterface friendsInterface = retrofit.create(FriendsInterface.class);
                    Call<Void> call = friendsInterface.deleteFriend(user_id,user.getId());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            System.out.println("done deleting");
                            friendship.setText("ADDfriend");
                            MyFriends myFriends = new MyFriends();
                            getFragmentManager().beginTransaction().replace(R.id.framelayout, myFriends).commit();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });

                }
            });



        }
        else {
            friendship.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(FriendsInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                            .build();
                    String user_id = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, getActivity().MODE_PRIVATE).getString(MainActivity.PREF_USER_ID, null);
                    System.out.println(user_id+"user id in friends");
                    System.out.println(user.getId()+"friend id in friends");
                    FriendsInterface friendsInterface = retrofit.create(FriendsInterface.class);
                    Call<Void> call = friendsInterface.addFriend(user_id,user.getId());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            System.out.println("done adding");
                            friendship.setText("UNFRIEND");
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });

                }
            });

        }


        return view;
    }

    public void setUser(User user){
        this.user = user;

    }


    public void setState(String state){
        this.State = state;

    }

}
