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
import android.widget.Toast;

import com.example.selimmouelhi.esprit4launch.Interfaces.UserInterface;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.example.selimmouelhi.esprit4launch.fragments.FriendProfile;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        favoritesNumber = view.findViewById(R.id.favoritesnbr);


        System.out.println(user.getNom()+"in dialog");


        //set data


        Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                .build();
        UserInterface userInterface = retrofit.create(UserInterface.class);
        Call<User> call = userInterface.getUserById(user.getId());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Picasso.with(getContext()).load(response.body().getImage()).into(profileImage);
                followersNumber.setText(Integer.toString(response.body().getFollowers()));
                friendsNumber.setText(Integer.toString(response.body().getFriends()));

                nameProfile.setText(response.body().getPrenom() + " " + response.body().getNom());




            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "failure in getting user by id", Toast.LENGTH_SHORT).show();

            }
        });


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
