package com.example.selimmouelhi.esprit4launch.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.Utils.ImageLoader;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    private static final String TAG= "FriendsAdapter";
    private static ArrayList<User> friends = new ArrayList<User>();
    private Context mcontext ;


    public FriendsAdapter( Context mcontext,ArrayList<User> friends) {
        this.friends = friends;
        this.mcontext = mcontext;
        System.out.println(friends.get(0).getNom()+"in constructor");
        System.out.println(friends.size()+"in constructor");


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.friends_row,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        System.out.println(viewHolder.getAdapterPosition()+"in bind");
        System.out.println(friends.get(viewHolder.getAdapterPosition()).getNom()+"in bind");
        System.out.println(friends.get(viewHolder.getAdapterPosition()).getNom()+"inonbind");

        Picasso.with(mcontext).load(friends.get(viewHolder.getAdapterPosition()).getImage()).into(viewHolder.friendPicture);
        viewHolder.nameView.setText(friends.get(viewHolder.getAdapterPosition()).getNom()+" "+friends.get(viewHolder.getAdapterPosition()).getPrenom());
        System.out.println(friends.size());

    }

    @Override
    public int getItemCount() {
        System.out.println(friends.size()+"in getitemcount");
        return friends.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        ImageView friendPicture;
        TextView nameView ;


        public ViewHolder(View itemView){
            super(itemView);
            nameView =  itemView.findViewById(R.id.friend_name);

            friendPicture  = itemView.findViewById(R.id.friend_picture);

        }
    }
}
