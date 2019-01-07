package com.example.selimmouelhi.esprit4launch.adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.example.selimmouelhi.esprit4launch.DialogFragment.PopupUser;
import com.example.selimmouelhi.esprit4launch.Interfaces.FollowerInterface;
import com.example.selimmouelhi.esprit4launch.Interfaces.FriendsInterface;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.activities.HomeActivity;
import com.example.selimmouelhi.esprit4launch.activities.MainActivity;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.selimmouelhi.esprit4launch.fragments.followerfollowingfriendFrag;

public class followunfollowAdapter extends RecyclerView.Adapter<followunfollowAdapter.ViewHolder> {
    public  User user;
    private static final String TAG= "follwersAdapter";
    private static ArrayList<User> followers = new ArrayList<User>();
    private Activity mcontext ;
    followerCallBack followerCallBack;


    public followunfollowAdapter( Activity mcontext,ArrayList<User> followers,followerCallBack callBack) {
        this.followers = followers;
        this.mcontext = mcontext;
        this.followerCallBack = callBack;



    }

    @NonNull
    @Override
    public followunfollowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.followers_row,viewGroup,false);

        followunfollowAdapter.ViewHolder viewHolder = new followunfollowAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


                System.out.println(viewHolder.getAdapterPosition() + "in bind");
                System.out.println(followers.get(viewHolder.getAdapterPosition()).getNom() + "in bind");
                System.out.println(followers.get(viewHolder.getAdapterPosition()).getNom() + "inonbind");
        System.out.println(followers.get(viewHolder.getAdapterPosition()).getImage()+"in adapterrr");

                Picasso.with(mcontext).load(followers.get(viewHolder.getAdapterPosition()).getImage()).into(viewHolder.friendPicture);

                viewHolder.nameView.setText(followers.get(viewHolder.getAdapterPosition()).getNom() + " " + followers.get(viewHolder.getAdapterPosition()).getPrenom());
                System.out.println(followers.size());
        Retrofit retrofit = new Retrofit.Builder().baseUrl(FollowerInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                .build();
        FollowerInterface followerInterface = retrofit.create(FollowerInterface.class);
        String id = mcontext.getSharedPreferences(MainActivity.PREFS_NAME, mcontext.MODE_PRIVATE).getString(MainActivity.PREF_USER_ID, null);


        Call<Boolean> call = followerInterface.isFollowing(id,followers.get(viewHolder.getAdapterPosition()).getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body() == true ){
                    viewHolder.following.setVisibility(View.VISIBLE);
                    viewHolder.follow.setVisibility(View.INVISIBLE);

                }
                else{

                    viewHolder.following.setVisibility(View.INVISIBLE);
                    viewHolder.follow.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

        //follow button logic
        viewHolder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertbuilder =  new AlertDialog.Builder(mcontext);
                alertbuilder.setMessage("Do you really want to follow "+followers.get(viewHolder.getAdapterPosition()).getPrenom() + " " + followers.get(viewHolder.getAdapterPosition()).getNom())
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                            Retrofit retrofit = new Retrofit.Builder().baseUrl(FollowerInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            FollowerInterface followerInterface = retrofit.create(FollowerInterface.class);
                            Call<Void> call = followerInterface.follow(id,followers.get(viewHolder.getAdapterPosition()).getId());
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        viewHolder.following.setVisibility(View.VISIBLE);
                                        viewHolder.follow.setVisibility(View.INVISIBLE);
                                        Toast.makeText( mcontext, "done ", Toast.LENGTH_SHORT).show();

                                        System.out.println(getUser().getNom() + "in beofre followerback");
                                        followerCallBack.onFollowingUser(1,user.getId());


                                        /*followerfollowingfriendFrag.setUser(user);
                                        Bundle b = new Bundle();
                                        b.putString("state","follow");
                                        followerfollowingfriendFrag.setArguments(b);
                                        ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, followerfollowingfriendFrag).commit();*/
                                       // notifyDataSetChanged();



                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText( mcontext, "error following ", Toast.LENGTH_SHORT).show();

                                    }
                                });



                            }
                        })    ;


                AlertDialog alert = alertbuilder.create();
                alert.setTitle("Alert");
                alert.show();


            }
        });


        //following button logic


        viewHolder.following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alertbuilder =  new AlertDialog.Builder(mcontext);
                alertbuilder.setMessage("Do you really want to unfollow "+followers.get(viewHolder.getAdapterPosition()).getPrenom() + " " + followers.get(viewHolder.getAdapterPosition()).getNom())
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                            Retrofit retrofit = new Retrofit.Builder().baseUrl(FollowerInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            FollowerInterface followerInterface = retrofit.create(FollowerInterface.class);
                            Call<Void> call = followerInterface.unfollow(id,followers.get(viewHolder.getAdapterPosition()).getId());
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        viewHolder.following.setVisibility(View.INVISIBLE);
                                        viewHolder.follow.setVisibility(View.VISIBLE);
                                        Toast.makeText( mcontext, "done unfollowing ", Toast.LENGTH_SHORT).show();
                                        notifyItemChanged(viewHolder.getAdapterPosition());
                                        notifyDataSetChanged();
                                        notifyItemRangeChanged(viewHolder.getAdapterPosition(),followers.size());

                                       // viewHolder.itemView.setVisibility(View.GONE);

                                       /*  Bundle b = new Bundle();
                                        b.putString("state","following");
                                        FollowerfollowingfriendFrag.setArguments(b);
                                        ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, FollowerfollowingfriendFrag).commit();

                                            */
                                        System.out.println(getUser().getNom() + "in beofre followerback");

                                        followerCallBack.onunFollowingUser(-1,user.getId());

                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText( mcontext, "error following ", Toast.LENGTH_SHORT).show();

                                    }
                                });



                            }
                        })    ;


                AlertDialog alert = alertbuilder.create();
                alert.setTitle("Alert");
                alert.show();

            }
        });
    }
/*
    @Override
    public void onBindViewHolder(@NonNull final FriendsAdapter.ViewHolder viewHolder, int i) {

        System.out.println(viewHolder.getAdapterPosition()+"in bind");
        System.out.println(friends.get(viewHolder.getAdapterPosition()).getNom()+"in bind");
        System.out.println(friends.get(viewHolder.getAdapterPosition()).getNom()+"inonbind");

        Picasso.with(mcontext).load(friends.get(viewHolder.getAdapterPosition()).getImage()).into(viewHolder.friendPicture);
        viewHolder.nameView.setText(friends.get(viewHolder.getAdapterPosition()).getNom()+" "+friends.get(viewHolder.getAdapterPosition()).getPrenom());
        System.out.println(friends.size());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = friends.get(viewHolder.getAdapterPosition());
                FriendProfile friendProfile = new FriendProfile();
                friendProfile.setUser(user);
                friendProfile.setState("friends");
                FragmentManager manager = ((AppCompatActivity)mcontext).getSupportFragmentManager();

                manager.beginTransaction().replace(R.id.framelayout,friendProfile).commit();

                PopupUser popupUser = new PopupUser();
                popupUser.setUser(user);
                popupUser.show(((HomeActivity)mcontext).getSupportFragmentManager(),"popupDialog");




            }
        });

    }
    */

    @Override
    public int getItemCount() {
        System.out.println(followers.size()+"in getitemcount");
        return this.followers.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        CircleImageView friendPicture;
        TextView nameView ;
        Button follow;
        Button following;
        LinearLayout linearLayoutfollowers;
        LinearLayout linearLayoutfollowings;


        public ViewHolder(View itemView){
            super(itemView);
            nameView =  itemView.findViewById(R.id.nameprenom);
            follow = itemView.findViewById(R.id.follow);
            following = itemView.findViewById(R.id.following);
            friendPicture  = itemView.findViewById(R.id.picture);


        }
    }

    public void setUser(User user){
        this.user= user;
    }
public User getUser(){
        return this.user;
}
public void setfollowers(ArrayList<User> followers){
        this.followers = followers;

}

    public interface followerCallBack {
        void onFollowingUser(int nbr ,String id);
        void onunFollowingUser(int  nbr,String id);


    }

}

