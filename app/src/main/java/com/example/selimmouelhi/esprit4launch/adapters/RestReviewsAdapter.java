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

import com.example.selimmouelhi.esprit4launch.R;
import com.example.selimmouelhi.esprit4launch.entities.Reviews;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RestReviewsAdapter extends ArrayAdapter<Reviews> implements View.OnClickListener {
    Context mContext;

    public  RestReviewsAdapter(List<Reviews> data, Context context) {
        super(context, R.layout.rest_reviews_list,data);
        ArrayList<Reviews> dataSet = (ArrayList<Reviews>) data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

    }
    private static class ViewHolder {

        //TextView txtName;
        TextView name;
        TextView comment;
        RatingBar rating;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Reviews dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        RestReviewsAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new RestReviewsAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.rest_reviews_list, parent, false);
            // viewHolder.txtName = (TextView) convertView.findViewById(R.id.textView);

            viewHolder.comment = convertView.findViewById(R.id.comment);

            viewHolder.rating = convertView.findViewById(R.id.ratingBar);




            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RestReviewsAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        /*Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;*/


        viewHolder.comment.setText(dataModel.getComment());
        viewHolder.rating.setStepSize((float) 0.1);
        viewHolder.rating.setNumStars(5);
        viewHolder.rating.setRating((float) dataModel.getRating());



        viewHolder.comment.setOnClickListener(this);
        viewHolder.comment.setTag(position);
        return convertView;

    }


}
