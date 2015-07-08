package com.udacity.rucinskic.spotify_streamer.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.enums.API;
import com.udacity.rucinskic.spotify_streamer.interfaces.NetworkCacheable;
import com.udacity.rucinskic.spotify_streamer.ui.support.Movie;

public class SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);    // TODO fix the landscape layout file in part 2. No need for CollapsingToolbarLayout and related items as I don't use them in landscape
                                                        // TODO Use a recyclerView instead.

        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        final TextView releaseYear = (TextView) findViewById(R.id.movie_release_year);
        final TextView overview = (TextView) findViewById(R.id.movie_overview);
        final RatingBar rating = (RatingBar) findViewById(R.id.movie_rating);
        final ImageView headerImage = (ImageView) findViewById(R.id.header);

        Movie movie;

        Intent movieData = getIntent();

        NetworkCacheable<Movie> api = (API) movieData.getSerializableExtra("movie");    // TODO Move the movie data to the Application (App). OnClick in first activity can cause a network retrieval of extra movie data before second activities OnCreate is called.
        int position = movieData.getIntExtra("position", -1);

        if (position == -1) startActivity(new Intent(this, MainActivity.class));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movie = api.getCache().get(position);

        collapsingToolbar.setTitle(movie.getName());
        toolbar.setTitle(movie.getName());
        releaseYear.setText(movie.getDateRelease());
        overview.setText(movie.getOverview());
        rating.setRating(movie.getRating() / 2);

        Uri uri;
        int width;  // TODO Part 2: test using headerImage's width and height
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


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_secondary, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);

    }

}
