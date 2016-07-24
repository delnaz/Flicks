package com.codepath.flicks.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flicks.R;
import com.codepath.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieAdapter extends ArrayAdapter<Movie> {


    static class ViewHolder {
       TextView title;
        TextView overview;

    }

    public MovieAdapter(Context context, List<Movie> movies){
        super(context, android.R.layout.simple_list_item_1,movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        ImageView ivImage;
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie,parent,false);
            ivImage = (ImageView) convertView.findViewById(R.id.imageView);
            ivImage.setImageResource(0);

            viewHolder.title = (TextView)convertView.findViewById(R.id.tvTitle);
            viewHolder.overview = (TextView)convertView.findViewById(R.id.tvOverview);
            convertView.setTag(viewHolder);
        } else {
            ivImage = (ImageView) convertView.findViewById(R.id.imageView);

            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.overview.setText(movie.getOverview());
        String imageUrl = "";
        int orientation = getContext().getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            imageUrl = "https://image.tmdb.org/t/p/w320/" + movie.getPosterPath();
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageUrl = "https://image.tmdb.org/t/p/w500/" + movie.getBackdropPath();
        }

        Picasso.with(getContext())
                .load(imageUrl)
                .transform(new RoundedCornersTransformation(10, 10))
                .placeholder(R.drawable.default_movie_icon)
                .error(R.drawable.default_movie_icon)
                .into(ivImage);

        // Return the completed view to render on screen
        return convertView;
    }
}
