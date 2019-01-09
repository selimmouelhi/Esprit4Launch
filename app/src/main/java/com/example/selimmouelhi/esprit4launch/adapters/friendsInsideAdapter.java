package com.example.selimmouelhi.esprit4launch.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.selimmouelhi.esprit4launch.DialogFragment.PopupUser;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.activities.HomeActivity;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class friendsInsideAdapter extends RecyclerView.Adapter<friendsInsideAdapter.ViewHolder> {

    private static final String TAG= "FriendsAdapter";
    private static ArrayList<User> friends = new ArrayList<User>();
    private Activity mcontext ;


    public friendsInsideAdapter( Activity mcontext,ArrayList<User> friends) {
        this.friends = friends;
        this.mcontext = mcontext;



    }

    @NonNull
    @Override
    public friendsInsideAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.friends_row,viewGroup,false);

        friendsInsideAdapter.ViewHolder viewHolder = new friendsInsideAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final friendsInsideAdapter.ViewHolder viewHolder, int i) {

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
               /* FriendProfile friendProfile = new FriendProfile();
                friendProfile.setUser(user);
                friendProfile.setState("friends");
                FragmentManager manager = ((AppCompatActivity)mcontext).getSupportFragmentManager();

                manager.beginTransaction().replace(R.id.framelayout,friendProfile).commit();*/

                PopupUser popupUser = new PopupUser();
                popupUser.setUser(user);
                popupUser.show(((HomeActivity)mcontext).getSupportFragmentManager(),"popupDialog");




            }
        });

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