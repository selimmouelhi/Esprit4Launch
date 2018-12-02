package com.example.selimmouelhi.esprit4launch.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selimmouelhi.esprit4launch.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomescreenFragment extends Fragment {


    public HomescreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_homescreen, container, false);

        topratedRes topratedRes = new topratedRes();
        getFragmentManager().beginTransaction().replace(R.id.home_toprated_restaurants, topratedRes).commit();

        topratedCaf topratedCaf = new topratedCaf();
        getFragmentManager().beginTransaction().replace(R.id.caftoprated, topratedCaf).commit();

        tastyRes tastyRes = new tastyRes();
        getFragmentManager().beginTransaction().replace(R.id.tastyres, tastyRes).commit();

        servicefrag servicefrag = new servicefrag();
        getFragmentManager().beginTransaction().replace(R.id.serviceres, servicefrag).commit();

        serviceCaf serviceCaf = new serviceCaf();
        getFragmentManager().beginTransaction().replace(R.id.Catfservice, serviceCaf).commit();

        return  view ;
    }

}
