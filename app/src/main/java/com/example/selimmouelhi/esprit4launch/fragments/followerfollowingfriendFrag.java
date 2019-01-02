package com.example.selimmouelhi.esprit4launch.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selimmouelhi.esprit4launch.Interfaces.FollowerInterface;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.adapters.FriendsAdapter;
import com.example.selimmouelhi.esprit4launch.entities.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.selimmouelhi.esprit4launch.adapters.followunfollowAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class followerfollowingfriendFrag extends Fragment {
    User user ;
    TextView nameProf ;
    TextView numberfollowers;
    TextView numberfollowings ;
    TextView nofollowersorfollowings;
    LinearLayout linearLayoutfollower;
    LinearLayout linearLayoutfollowing;
    LinearLayout linearLayoutfollowernbr;
    LinearLayout linearLayoutfollowingnbr;
    RecyclerView recyclerView;
    ArrayList<User> followers = new ArrayList<User>();
    ArrayList<User> followings = new ArrayList<User>();


    public followerfollowingfriendFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_followerfollowingfriend, container, false);

        nameProf = view.findViewById(R.id.nameProf);
        numberfollowers = view.findViewById(R.id.numberfollowers);
        numberfollowings = view.findViewById(R.id.numberfollowings);
        nofollowersorfollowings = view.findViewById(R.id.nofollow);
        linearLayoutfollower = view.findViewById(R.id.linearfollowers);
        linearLayoutfollowing = view.findViewById(R.id.linearfollowings);
        linearLayoutfollowernbr = view.findViewById(R.id.linearfollowersnumber);
        linearLayoutfollowingnbr = view.findViewById(R.id.linearfollowingssnumber);
        recyclerView = view.findViewById(R.id.recyclerViewfollowings);

        nameProf.setText(this.user.getPrenom()+" "+this.user.getNom());
        numberfollowers.setText(Integer.toString(user.getFollowers()));
        numberfollowings.setText(Integer.toString(user.getFollowing()));
        nofollowersorfollowings.setVisibility(View.INVISIBLE);
        linearLayoutfollowing.setVisibility(View.INVISIBLE);
        linearLayoutfollower.setVisibility(View.INVISIBLE);


        linearLayoutfollowernbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(FollowerInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                FollowerInterface followerInterface = retrofit.create(FollowerInterface.class);
                Call<List<User>> call = followerInterface.getFollowers(user.getId());
                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                        followers = (ArrayList<User>)response.body();
                        if(followers.size()==0){
                            nofollowersorfollowings.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.INVISIBLE);
                            linearLayoutfollowing.setVisibility(View.INVISIBLE);
                            linearLayoutfollower.setVisibility(View.VISIBLE);

                        }
                        else {
                            nofollowersorfollowings.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            linearLayoutfollowing.setVisibility(View.INVISIBLE);
                            linearLayoutfollower.setVisibility(View.VISIBLE);

                            System.out.println(followers.get(1).getNom() + "in followunfollow frag");
                            LinearLayoutManager llm = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(llm);
                            followunfollowAdapter followunfollowAdapter = new followunfollowAdapter(getActivity(), followers);
                            followunfollowAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(followunfollowAdapter);
                            System.out.println("done");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(getContext(), "failure ", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        linearLayoutfollowingnbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(FollowerInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                FollowerInterface followerInterface = retrofit.create(FollowerInterface.class);
                Call<List<User>> call = followerInterface.getFollowing(user.getId());
                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        followings = (ArrayList<User>)response.body();
                        if(followings.size() == 0){
                            nofollowersorfollowings.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.INVISIBLE);
                            linearLayoutfollowing.setVisibility(View.VISIBLE);
                            linearLayoutfollower.setVisibility(View.INVISIBLE);

                        }
                        else{
                            nofollowersorfollowings.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            linearLayoutfollowing.setVisibility(View.VISIBLE);
                            linearLayoutfollower.setVisibility(View.INVISIBLE);
                        System.out.println(followings.get(0).getNom()+"in followunfollow frag");
                        LinearLayoutManager llm = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(llm);
                        followunfollowAdapter followunfollowAdapter = new followunfollowAdapter(getActivity(),followings);
                        followunfollowAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(followunfollowAdapter);
                        System.out.println("done");
                        }

                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(getContext(), "failure ", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


        return  view ;
    }

    public void setUser (User user){
        this.user = user ;


    }
}
