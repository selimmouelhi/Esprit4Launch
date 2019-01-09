package com.example.selimmouelhi.esprit4launch.fragments;


import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.selimmouelhi.esprit4launch.Interfaces.FriendsInterface;
import com.example.selimmouelhi.esprit4launch.Interfaces.UserInterface;
import com.example.selimmouelhi.esprit4launch.Interfaces.topratedRestaurants;
import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.activities.MainActivity;
import com.example.selimmouelhi.esprit4launch.adapters.AllUsersAdapter;
import com.example.selimmouelhi.esprit4launch.adapters.FriendsAdapter;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;
import com.example.selimmouelhi.esprit4launch.entities.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFriends extends Fragment {
    private ArrayList<User> friends;
    private RecyclerView listView;
    TextView nofriends;
    Dialog popupDialog;
    ProgressBar progressBar;
    ImageView allusers;
    ImageView myfriends;


    public MyFriends() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_my_friends, container, false);
        friends = new ArrayList<User>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(FriendsInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                .build();
        Retrofit retrofituser = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                .build();
        String user_id = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, getActivity().MODE_PRIVATE).getString(MainActivity.PREF_USER_ID, null);
        allusers = view.findViewById(R.id.allusers);
        myfriends = view.findViewById(R.id.myfriends);
        listView = view.findViewById(R.id.listfriends);
        listView.setVisibility(View.INVISIBLE);
        listView.setHasFixedSize(true);
        FriendsInterface friendsInterface = retrofit.create(FriendsInterface.class);
        UserInterface userInterface = retrofituser.create(UserInterface.class);
        nofriends = view.findViewById(R.id.nofriends);
        nofriends.setVisibility(View.INVISIBLE);

        System.out.println(user_id+"in frag");

        myfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<List<User>> call = friendsInterface.getFriendsList(user_id);

                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        System.out.println(response.body().toString() + "in frag");
                        friends = (ArrayList<User>) response.body();
                        if (friends.size() == 0) {
                            nofriends.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.INVISIBLE);


                        } else {
                            nofriends.setVisibility(View.INVISIBLE);
                            listView.setVisibility(View.VISIBLE);
                            LinearLayoutManager llm = new LinearLayoutManager(getContext());
                            listView.setLayoutManager(llm);
                            FriendsAdapter friendsAdapter = new FriendsAdapter(getActivity(), friends);
                            friendsAdapter.notifyDataSetChanged();
                            listView.setAdapter(friendsAdapter);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        System.out.println("error in friends");
                    }
                });

            }
        });

        allusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<User>> call = userInterface.searchUser("%2500",user_id);

                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        friends = (ArrayList<User>) response.body();
                        if (friends.size() == 0) {
                            nofriends.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.INVISIBLE);


                        } else {
                            nofriends.setVisibility(View.INVISIBLE);
                            listView.setVisibility(View.VISIBLE);
                            LinearLayoutManager llm = new LinearLayoutManager(getContext());
                            listView.setLayoutManager(llm);
                            AllUsersAdapter allUsersAdapter = new AllUsersAdapter(getActivity(), friends);
                            allUsersAdapter.notifyDataSetChanged();
                            listView.setAdapter(allUsersAdapter);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        System.out.println("error in friends");
                    }
                });
            }
        });
        return  view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuInflater inflater1 = getActivity().getMenuInflater();
        inflater.inflate(R.menu.actionbar,menu);
        String user_id = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, getActivity().MODE_PRIVATE).getString(MainActivity.PREF_USER_ID, null);
        SearchManager searchManager =(SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchh).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName())


        );
        System.out.println(searchView.getQuery().toString()+"in homies");
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofituser = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofituser.create(UserInterface.class);
                Call<List<User>> call = userInterface.searchUser(searchView.getQuery().toString(),user_id);

                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        friends = (ArrayList<User>) response.body();
                        if (friends.size() == 0) {
                            nofriends.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.INVISIBLE);


                        } else {
                            nofriends.setVisibility(View.INVISIBLE);
                            listView.setVisibility(View.VISIBLE);
                            LinearLayoutManager llm = new LinearLayoutManager(getContext());
                            listView.setLayoutManager(llm);
                            FriendsAdapter friendsAdapter = new FriendsAdapter(getActivity(), friends);
                            friendsAdapter.notifyDataSetChanged();
                            listView.setAdapter(friendsAdapter);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        System.out.println("error in friends");
                    }
                });
            }
        });
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println(s + " in searchview");

                Retrofit retrofituser = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofituser.create(UserInterface.class);
                Call<List<User>> call = userInterface.searchUser(s,user_id);

                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        friends = (ArrayList<User>) response.body();
                        if (friends.size() == 0) {
                            nofriends.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.INVISIBLE);


                        } else {
                            nofriends.setVisibility(View.INVISIBLE);
                            listView.setVisibility(View.VISIBLE);
                            LinearLayoutManager llm = new LinearLayoutManager(getContext());
                            listView.setLayoutManager(llm);
                            FriendsAdapter friendsAdapter = new FriendsAdapter(getActivity(), friends);
                            friendsAdapter.notifyDataSetChanged();
                            listView.setAdapter(friendsAdapter);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        System.out.println("error in friends");
                    }
                });


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                Retrofit retrofituser = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserInterface userInterface = retrofituser.create(UserInterface.class);
                Call<List<User>> call = userInterface.searchUser(s,user_id);

                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        friends = (ArrayList<User>) response.body();
                        if (friends.size() == 0) {
                            nofriends.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.INVISIBLE);


                        } else {
                            nofriends.setVisibility(View.INVISIBLE);
                            listView.setVisibility(View.VISIBLE);
                            LinearLayoutManager llm = new LinearLayoutManager(getContext());
                            listView.setLayoutManager(llm);
                            FriendsAdapter friendsAdapter = new FriendsAdapter(getActivity(), friends);
                            friendsAdapter.notifyDataSetChanged();
                            listView.setAdapter(friendsAdapter);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        System.out.println("error in friends");
                    }
                });
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.searchh){
            String user_id = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, getActivity().MODE_PRIVATE).getString(MainActivity.PREF_USER_ID, null);


            SearchManager searchManager =(SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getActivity().getComponentName())


            );
            System.out.println(searchView.getQuery().toString()+"in homies");
            searchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Retrofit retrofituser = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                            .build();
                    UserInterface userInterface = retrofituser.create(UserInterface.class);
                    Call<List<User>> call = userInterface.searchUser(searchView.getQuery().toString(),user_id);

                    call.enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            friends = (ArrayList<User>) response.body();
                            if (friends.size() == 0) {
                                nofriends.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.INVISIBLE);


                            } else {
                                nofriends.setVisibility(View.INVISIBLE);
                                listView.setVisibility(View.VISIBLE);
                                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                                listView.setLayoutManager(llm);
                                FriendsAdapter friendsAdapter = new FriendsAdapter(getActivity(), friends);
                                friendsAdapter.notifyDataSetChanged();
                                listView.setAdapter(friendsAdapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            System.out.println("error in friends");
                        }
                    });
                }
            });
            searchView.setIconified(false);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    System.out.println(s + " in searchview");

                    Retrofit retrofituser = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                            .build();
                    UserInterface userInterface = retrofituser.create(UserInterface.class);
                    Call<List<User>> call = userInterface.searchUser(s,user_id);

                    call.enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            friends = (ArrayList<User>) response.body();
                            if (friends.size() == 0) {
                                nofriends.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.INVISIBLE);


                            } else {
                                nofriends.setVisibility(View.INVISIBLE);
                                listView.setVisibility(View.VISIBLE);
                                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                                listView.setLayoutManager(llm);
                                FriendsAdapter friendsAdapter = new FriendsAdapter(getActivity(), friends);
                                friendsAdapter.notifyDataSetChanged();
                                listView.setAdapter(friendsAdapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            System.out.println("error in friends");
                        }
                    });


                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {

                    Retrofit retrofituser = new Retrofit.Builder().baseUrl(UserInterface.Base_Url).addConverterFactory(GsonConverterFactory.create())
                            .build();
                    UserInterface userInterface = retrofituser.create(UserInterface.class);
                    Call<List<User>> call = userInterface.searchUser(s,user_id);

                    call.enqueue(new Callback<List<User>>() {
                        @Override
                        public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                            friends = (ArrayList<User>) response.body();
                            if (friends.size() == 0) {
                                nofriends.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.INVISIBLE);


                            } else {
                                nofriends.setVisibility(View.INVISIBLE);
                                listView.setVisibility(View.VISIBLE);
                                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                                listView.setLayoutManager(llm);
                                FriendsAdapter friendsAdapter = new FriendsAdapter(getActivity(), friends);
                                friendsAdapter.notifyDataSetChanged();
                                listView.setAdapter(friendsAdapter);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<User>> call, Throwable t) {
                            System.out.println("error in friends");
                        }
                    });
                    return false;
                }
            });

            return  true;
        }
        return  false ;
    }
}
