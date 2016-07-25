package com.codepath.flicks.models;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flicks.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by delnaz on 7/24/2016.
 */
public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.tvDetailTitle)
    TextView detailTitle;
    @BindView(R.id.tvDetailOverview) TextView detailOverview;
    @BindView(R.id.ivMovieDetail)
    ImageView ivdetailImage;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    int mMovieId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Bundle intent = getIntent().getExtras();
        if(intent.containsKey("movieID"))
            mMovieId = intent.getInt("movieID");
        fetchDataFromUrlDetail(mMovieId);

    }

    public void fetchDataFromUrlDetail(int mMovieId){

        String url = "https://api.themoviedb.org/3/movie/" + mMovieId + "?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                  Movie movie = new Movie(response);
                    String imageUrl = "";
                    imageUrl = "https://image.tmdb.org/t/p/w780/" + movie.getBackdropPath();
                    detailOverview.setText(movie.getOverview());
                    detailTitle.setText(movie.getOriginalTitle());
                    ratingBar.setRating(movie.getVoteAverage());
                    ratingBar.setMax(10);
                    ratingBar.setStepSize(0.1f);
                    ratingBar.setEnabled(false);
                    Picasso.with(MovieDetailActivity.this)
                            .load(imageUrl)
                            .transform(new RoundedCornersTransformation(10, 10))
                            .placeholder(R.drawable.default_movie_icon)
                            .error(R.drawable.default_movie_icon)
                            .into(ivdetailImage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }
}
