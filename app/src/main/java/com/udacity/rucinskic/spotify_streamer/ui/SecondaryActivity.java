package com.udacity.rucinskic.spotify_streamer.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.rucinskic.spotify_streamer.App;
import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;

public class SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        final ImageView headerImage = (ImageView) findViewById(R.id.header);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MovieOverviewFragment.newInstance();
        Movie movie = Movie.getMovie(App.getMovieID());

        collapsingToolbar.setTitle(movie.getName());
        toolbar.setTitle(movie.getName());

        Uri uri;
        int width;
        int height;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            uri = movie.getImageUriLarge();
            width = getResources().getDisplayMetrics().widthPixels;
            height =  (int) (getResources().getDisplayMetrics().heightPixels / 1.618);

        } else {

            uri = movie.getImageUri();
            width = (int) (getResources().getDisplayMetrics().widthPixels / 1.618);
            height =  getResources().getDisplayMetrics().heightPixels;

        }

        Picasso.with(this)
                .load(uri)
                .centerInside()
                .resize(width, height)
                .into(headerImage);

    }

}
