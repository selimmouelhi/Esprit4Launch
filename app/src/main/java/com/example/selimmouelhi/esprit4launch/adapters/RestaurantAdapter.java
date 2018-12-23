package com.example.selimmouelhi.esprit4launch.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.selimmouelhi.esprit4launch.Utils.ImageLoader;
import com.example.selimmouelhi.esprit4launch.activities.RestDatailsActivity;
import com.example.selimmouelhi.esprit4launch.entities.Restaurant;
import com.example.selimmouelhi.esprit4launch.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> implements View.OnClickListener {


    Context mContext;

    public RestaurantAdapter(List<Restaurant> data, Context context) {
        super(context, R.layout.rest_list,data);
        ArrayList<Restaurant> dataSet = (ArrayList<Restaurant>) data;
        this.mContext = context;

    }

    // View lookup cache
    private static class ViewHolder {

        //TextView txtName;
        TextView name;
        TextView radius;
        RatingBar rating;
        ImageView image;
        Button detail;
    }


    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Restaurant dataModel = (Restaurant) object;

      /*  switch (v.getId())
        {
            case R.id.list:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }*/
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
       final Restaurant dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.rest_list, parent, false);
            // viewHolder.txtName = (TextView) convertView.findViewById(R.id.textView);

            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.radius = convertView.findViewById(R.id.radius);
            viewHolder.rating = convertView.findViewById(R.id.ratingBar);
            viewHolder.image = convertView.findViewById(R.id.imageView);
            viewHolder.detail = convertView.findViewById(R.id.details);


            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        /*Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;*/


        viewHolder.name.setText(dataModel.getName());
        viewHolder.rating.setStepSize((float) 0.1);
        viewHolder.rating.setNumStars(5);
        viewHolder.rating.setRating((float) dataModel.getRating());
        viewHolder.radius.setText(dataModel.getRadius()+"");
        System.out.println(dataModel.getRadius());
        Picasso.with(getContext()).load(ImageLoader.url_image+dataModel.getImageUrl()+ImageLoader.API_Key).into(viewHolder.image);

        viewHolder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), RestDatailsActivity.class);

                i.putExtra("image",dataModel.getImageUrl());
                i.putExtra("id",dataModel.getId());
                i.putExtra("name",dataModel.getName());
                i.putExtra("description",dataModel.getDescription());
                System.out.println(dataModel.getName());
                i.putExtra("adresse",dataModel.getAdresse());
                i.putExtra("rating",dataModel.getRating());
                i.putExtra("radius",dataModel.getRadius());
                mContext.startActivity(i);


            }
        });

        viewHolder.name.setOnClickListener(this);
        viewHolder.name.setTag(position);
        return convertView;

    }


}
