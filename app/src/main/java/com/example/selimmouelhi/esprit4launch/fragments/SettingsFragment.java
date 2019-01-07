package com.example.selimmouelhi.esprit4launch.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    ImageView imageView;
   private User user ;


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        imageView = view.findViewById(R.id.testcouverture);
        System.out.println(user.getCouverture_url());
        //Picasso.get().load(user.getCouverture_url()).into(imageView);
        Glide.with(view).load(user.getCouverture_url()).into(imageView);


        return  view;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
