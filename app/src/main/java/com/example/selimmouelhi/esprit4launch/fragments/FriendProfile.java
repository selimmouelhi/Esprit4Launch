package com.example.selimmouelhi.esprit4launch.fragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.selimmouelhi.esprit4launch.Interfaces.FollowerInterface;
import com.example.selimmouelhi.esprit4launch.Interfaces.FriendsInterface;
import com.example.selimmouelhi.esprit4launch.Interfaces.UserInterface;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.activities.MainActivity;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendProfile extends Fragment {
    private boolean isopen=false ;
    private ConstraintSet layout1,layout2;
    private ConstraintLayout constraintLayout;

    TextView nameView;
    ImageView profile_picture;
    ImageView unfriend ;
    Button follow;
    Button unfollow;
    TextView add;
    TextView unadd;
    TextView email ;
    TextView phone ;
    TextView followingsnbr;
    TextView friendsnbr;
    TextView followersnbr;
    ImageView friend;
    TextView requestfriend ;
    private static User user;
    private static String State;
    public FriendProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_friend_profile, container, false);
        nameView = view.findViewById(R.id.name_text);
        profile_picture = view.findViewById(R.id.profile_picture);
        unfriend = view.findViewById(R.id.unfriend);
        friend = view.findViewById(R.id.friend);
        follow = view.findViewById(R.id.follow);
        unfollow = view.findViewById(R.id.unfollow);
        add = view.findViewById(R.id.add);
        unadd = view.findViewById(R.id.unadd);
        email = view.findViewById(R.id.emailProfile);
        phone = view.findViewById(R.id.phone_profile);
        followingsnbr = view.findViewById(R.id.numberfollwings);
        friendsnbr= view.findViewById(R.id.numberFriends);
        followersnbr = view.findViewById(R.id.numberFollowers);
        requestfriend = view.findViewById(R.id.request);

        String id = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, getActivity().MODE_PRIVATE).getString(MainActivity.PREF_USER_ID, null);



        //set data

        //go from number following to following frag

        friendsnbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofit.create(UserInterface.class);
                Call<User> call = userInterface.getUserById(user.getId());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {


                        System.out.println(response.body().getNom()+"in profile frag");
                        Friendsinside friendsinside = new Friendsinside();
                        friendsinside.setUser(response.body());
                        FragmentManager manager = getActivity().getSupportFragmentManager();


                        manager.beginTransaction().addToBackStack(null).replace(R.id.framelayout,friendsinside).commit();

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), "failure in getting user by id", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        followingsnbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofit.create(UserInterface.class);
                Call<User> call = userInterface.getUserById(user.getId());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        System.out.println(response.body().getNom()+"in profile frag");
                        followerfollowingfriendFrag followerfollowingfriendFrag = new followerfollowingfriendFrag();
                        followerfollowingfriendFrag.setUser(response.body());
                        FragmentManager manager = getActivity().getSupportFragmentManager();

                        manager.beginTransaction().replace(R.id.framelayout,followerfollowingfriendFrag).commit();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

            }
        });


        //go from number following to following frag

        followersnbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofit.create(UserInterface.class);
                Call<User> call = userInterface.getUserById(user.getId());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        System.out.println(response.body().getNom()+"in profile frag");
                        followerfollowingfriendFrag followerfollowingfriendFrag = new followerfollowingfriendFrag();
                        followerfollowingfriendFrag.setUser(response.body());
                        FragmentManager manager = getActivity().getSupportFragmentManager();

                        manager.beginTransaction().replace(R.id.framelayout,followerfollowingfriendFrag).commit();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

            }
        });

        Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                .build();
        UserInterface userInterface = retrofit.create(UserInterface.class);
        Call<User> call = userInterface.getUserById(user.getId());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User u = response.body();

                followingsnbr.setText(Integer.toString(response.body().getFollowing()));
                followersnbr.setText(Integer.toString(response.body().getFollowers()));
                friendsnbr.setText(Integer.toString(response.body().getFriends()));
                email.setText(response.body().getMail());
                phone.setText(Integer.toString(response.body().getPhone()));

                nameView.setText(response.body().getNom()+" "+response.body().getPrenom());
                Picasso.with(getContext()).load(response.body().getImage()).into(profile_picture);



                //get current user
                Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofit.create(UserInterface.class);
                Call<User> currentuser = userInterface.getUserById(id);
                    currentuser.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            //test is following or not

                            Retrofit retrofit = new Retrofit.Builder().baseUrl(FollowerInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            FollowerInterface followerInterface = retrofit.create(FollowerInterface.class);

                            Retrofit retrofitf = new Retrofit.Builder().baseUrl(FriendsInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                    .build();

                            FriendsInterface friendsInterface = retrofitf.create(FriendsInterface.class);



                            Call<Boolean> followingornot = followerInterface.isFollowing(response.body().getId(),u.getId());
                            followingornot.enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                    if (response.body() == true ){
                                        follow.setVisibility(View.INVISIBLE);
                                        unfollow.setVisibility(View.VISIBLE);




                                    }
                                    else{

                                        follow.setVisibility(View.VISIBLE);
                                        unfollow.setVisibility(View.INVISIBLE);



                                    }

                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {

                                }
                            });

                            //test friends or not


                            Call<Boolean> friendsornot = friendsInterface.is_friend(response.body().getId(),u.getId());
                            friendsornot.enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                    if (response.body() == true ){
                                        friend.setVisibility(View.INVISIBLE);
                                        unfriend.setVisibility(View.VISIBLE);
                                        add.setVisibility(View.INVISIBLE);
                                        requestfriend.setVisibility(View.INVISIBLE);
                                        unadd.setVisibility(View.VISIBLE);




                                    }
                                    else{
                                        friend.setVisibility(View.VISIBLE);
                                        unfriend.setVisibility(View.INVISIBLE);
                                        unadd.setVisibility(View.INVISIBLE);
                                        add.setVisibility(View.VISIBLE);
                                        requestfriend.setVisibility(View.INVISIBLE);




                                    }

                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {

                                }
                            });

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });








            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "failure in getting user by id", Toast.LENGTH_SHORT).show();

            }
        });







        layout1 = new ConstraintSet();
        layout2 = new ConstraintSet();
        constraintLayout = view.findViewById(R.id.extended);
        layout2.clone(getContext(),R.layout.fragment_friend_profile_extended);
        layout1.clone(constraintLayout);

        profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isopen) {

                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout2.applyTo(constraintLayout);
                    isopen = !isopen;
                } else {

                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout1.applyTo(constraintLayout);
                    isopen = !isopen;


                }
            }
        });



     //logic follow

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofit.create(UserInterface.class);
                Call<User> call = userInterface.getUserById(user.getId());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User u = response.body();




                        //get current user
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                .build();
                        UserInterface userInterface = retrofit.create(UserInterface.class);
                        Call<User> currentuser = userInterface.getUserById(id);
                        currentuser.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                //test is following or not

                                Retrofit retrofit = new Retrofit.Builder().baseUrl(FollowerInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                FollowerInterface followerInterface = retrofit.create(FollowerInterface.class);
                                Call<Void> followcall = followerInterface.follow(response.body().getId(),u.getId());
                                followcall.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        follow.setVisibility(View.INVISIBLE);
                                        unfollow.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });










                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });








                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), "failure in getting user by id", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
     //logic unfollow

        unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofit.create(UserInterface.class);
                Call<User> call = userInterface.getUserById(user.getId());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User u = response.body();




                        //get current user
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                .build();
                        UserInterface userInterface = retrofit.create(UserInterface.class);
                        Call<User> currentuser = userInterface.getUserById(id);
                        currentuser.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                //test is following or not

                                Retrofit retrofit = new Retrofit.Builder().baseUrl(FollowerInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                FollowerInterface followerInterface = retrofit.create(FollowerInterface.class);
                                Call<Void> followcall = followerInterface.unfollow(response.body().getId(),u.getId());
                                followcall.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        follow.setVisibility(View.INVISIBLE);
                                        unfollow.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });










                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });








                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), "failure in getting user by id", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
     //logic friend


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofit.create(UserInterface.class);
                Call<User> call = userInterface.getUserById(user.getId());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User u = response.body();




                        //get current user
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                .build();
                        UserInterface userInterface = retrofit.create(UserInterface.class);
                        Call<User> currentuser = userInterface.getUserById(id);
                        currentuser.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                //test is following or not

                                Retrofit retrofit = new Retrofit.Builder().baseUrl(FriendsInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                FriendsInterface friendsInterface = retrofit.create(FriendsInterface.class);
                                Call<Void> addcallfriend = friendsInterface.requestFriend(response.body().getId(),u.getId());
                                addcallfriend.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        friend.setVisibility(View.INVISIBLE);
                                        unfriend.setVisibility(View.INVISIBLE);
                                        requestfriend.setVisibility(View.VISIBLE);
                                        add.setVisibility(View.INVISIBLE);
                                        unadd.setVisibility(View.INVISIBLE);


                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });








                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });








                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), "failure in getting user by id", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
     //logic unfriend


        unadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofit.create(UserInterface.class);
                Call<User> call = userInterface.getUserById(user.getId());
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User u = response.body();




                        //get current user
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                .build();
                        UserInterface userInterface = retrofit.create(UserInterface.class);
                        Call<User> currentuser = userInterface.getUserById(id);
                        currentuser.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                //test is following or not

                                Retrofit retrofit = new Retrofit.Builder().baseUrl(FriendsInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                FriendsInterface friendsInterface = retrofit.create(FriendsInterface.class);
                                Call<Void> addcallfriend = friendsInterface.deleteFriend(response.body().getId(),u.getId());
                                addcallfriend.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        friend.setVisibility(View.VISIBLE);
                                        unfriend.setVisibility(View.INVISIBLE);
                                        requestfriend.setVisibility(View.INVISIBLE);
                                        add.setVisibility(View.VISIBLE);
                                        unadd.setVisibility(View.INVISIBLE);


                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });








                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });








                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getContext(), "failure in getting user by id", Toast.LENGTH_SHORT).show();

                    }
                });



            }
        });


        // logic request

        requestfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "friend request already sent", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public void setUser(User user){
        this.user = user;

    }


    public void setState(String state){
        this.State = state;

    }

}
