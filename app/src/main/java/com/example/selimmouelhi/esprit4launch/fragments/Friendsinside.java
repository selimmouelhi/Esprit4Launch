package com.example.selimmouelhi.esprit4launch.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.selimmouelhi.esprit4launch.adapters.friendsInsideAdapter;
import com.example.selimmouelhi.esprit4launch.Interfaces.FriendsInterface;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.entities.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class Friendsinside extends Fragment {
    private User user;

    TextView nameprof;
    TextView nbrfriends;
    RecyclerView recyclerView;


    public Friendsinside() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_friendsinside, container, false);

        nameprof = view.findViewById(R.id.nameProf);
        nbrfriends = view.findViewById(R.id.numberfriends);
        recyclerView = view.findViewById(R.id.recyclerViewfollowings);

        nameprof.setText(this.user.getPrenom() + " " + this.user.getNom());
        nbrfriends.setText(Integer.toString(user.getFriends()));


        Retrofit retrofit = new Retrofit.Builder().baseUrl(FriendsInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                .build();
        FriendsInterface friendsInterface = retrofit.create(FriendsInterface.class);
        Call<List<User>> call = friendsInterface.getFriendsList(user.getId());
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                ArrayList<User> friends = (ArrayList<User>)response.body();
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(llm);
                friendsInsideAdapter  friendsInsideAdapter = new friendsInsideAdapter(getActivity(),friends);
                recyclerView.setAdapter(friendsInsideAdapter);

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
        return view;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
