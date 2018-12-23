package com.example.selimmouelhi.esprit4launch.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.adapters.popularrAdapter;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragm extends Fragment {

    RecyclerView popularRestaurents;
    private ArrayList<Restaurant> lr = new ArrayList<Restaurant>();


    public HomeFragm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);

        getData();
        System.out.println(lr.get(0).getName()+"here in oncreate");
        System.out.println(lr.get(0).getName()+"here in fragment");
        popularRestaurents = rootview.findViewById(R.id.liste1);
        popularRestaurents.setLayoutManager(new LinearLayoutManager(rootview.getContext(),LinearLayout.HORIZONTAL,false));
        popularrAdapter customAdapter = new popularrAdapter(this.getContext(),lr);
        popularRestaurents.setAdapter(customAdapter);




        return  rootview;
    }


    private void getData(){
        lr.add( new Restaurant("lablebli","Ariana"));
        lr.add( new Restaurant("monofood","Ariana"));
        lr.add( new Restaurant("hafood","Ariana"));
        lr.add( new Restaurant("lablebli","Ariana"));
        lr.add( new Restaurant("monofood","Ariana"));
        lr.add( new Restaurant("hafood","Ariana"));

        System.out.println();
    }

    private void initRecycler(View rootview){









    }

}
