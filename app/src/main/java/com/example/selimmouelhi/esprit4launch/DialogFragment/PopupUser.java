package com.example.selimmouelhi.esprit4launch.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.example.selimmouelhi.esprit4launch.fragments.FriendProfile;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PopupUser extends AppCompatDialogFragment {

    User user ;
    TextView textclose ;
    Button btnviewProfile;
    CircleImageView profileImage;
    TextView nameProfile ;
    TextView friendsNumber ;
    TextView favoritesNumber ;
    TextView followersNumber ;





    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.userpopup,null);
        builder.setView(view);
        textclose = view.findViewById(R.id.exit);
        textclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        System.out.println(user.toString()+" in dialog");
        btnviewProfile = view.findViewById(R.id.viewProfile);
        profileImage = view.findViewById(R.id.profile_img);
        nameProfile = view.findViewById(R.id.namePopUp);
        friendsNumber = view.findViewById(R.id.friendsnbr);
        followersNumber = view.findViewById(R.id.followersnbr);

        System.out.println(user.getNom()+"in dialog");
        Picasso.with(getContext()).load(user.getImage()).into(profileImage);
       followersNumber.setText(Integer.toString(user.getFollowers()));
        friendsNumber.setText(Integer.toString(user.getFriends()));

        nameProfile.setText(user.getPrenom() + " " + user.getNom());

        btnviewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendProfile friendProfile = new FriendProfile();
                friendProfile.setUser(user);
                friendProfile.setState("friends");
                FragmentManager manager = ((AppCompatActivity)getContext()).getSupportFragmentManager();

                manager.beginTransaction().replace(R.id.framelayout,friendProfile).commit();
                dismiss();
            }
        });



        return builder.create();
    }



    public  void setUser(User u ){
        this.user = u ;


    }
}
