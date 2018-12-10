package com.example.selimmouelhi.esprit4launch.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.selimmouelhi.esprit4launch.Interfaces.FriendsInterface;
import com.example.selimmouelhi.esprit4launch.Interfaces.topratedRestaurants;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.activities.MainActivity;
import com.example.selimmouelhi.esprit4launch.adapters.FriendsAdapter;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;
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
public class MyFriends extends Fragment {
    private ArrayList<User> friends;
    private RecyclerView listView;
    TextView nofriends;


    public MyFriends() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_my_friends, container, false);
        friends = new ArrayList<User>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(FriendsInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                .build();
        String user_id = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, getActivity().MODE_PRIVATE).getString(MainActivity.PREF_USER_ID, null);

        listView = view.findViewById(R.id.listfriends);
        FriendsInterface friendsInterface = retrofit.create(FriendsInterface.class);
        nofriends = view.findViewById(R.id.nofriends);
        System.out.println(user_id+"in frag");
        Call<List<User>> call = friendsInterface.getFriendsList(user_id);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                System.out.println(response.body().toString() + "in frag");
                friends = (ArrayList<User>) response.body();
                if (friends.size() == 0) {
                    nofriends.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);


                } else {
                    nofriends.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    listView.setLayoutManager(llm);
                    FriendsAdapter friendsAdapter = new FriendsAdapter(getContext(), friends);
                    friendsAdapter.notifyDataSetChanged();
                    listView.setAdapter(friendsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                System.out.println("error in friends");
            }
        });
        return  view;
    }

}
