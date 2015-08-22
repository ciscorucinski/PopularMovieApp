package com.udacity.rucinskic.spotify_streamer.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.rucinskic.spotify_streamer.App;
import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.enums.API;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;

public class SecondaryActivity extends AppCompatActivity {

	private final Movie movie = Movie.getMovie(App.getMovieID());

	@Override
	public void onBackPressed() {

		super.onBackPressed();
		cleanUp();

	}

	private void cleanUp() { App.clearIndexer(); }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu_secondary, menu);

		MenuItem favoriteMenu = menu.findItem(R.id.overflow_favorite);
		MenuItem WatchedMenu = menu.findItem(R.id.overflow_watched);
		MenuItem WillWatchMenu = menu.findItem(R.id.overflow_want_to_watch);

		determineIfChecked(favoriteMenu, API.FAVORITE, movie);
		determineIfChecked(WatchedMenu, API.WATCHED, movie);
		determineIfChecked(WillWatchMenu, API.WILL_WATCH, movie);

		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			cleanUp();
			return super.onOptionsItemSelected(item);

		}

		item.setChecked(!item.isChecked());
		boolean isChecked = item.isChecked();

		switch (item.getItemId()) {

			case R.id.overflow_favorite:
				update(API.FAVORITE, isChecked);
				break;
			case R.id.overflow_watched:
				update(API.WATCHED, isChecked);
				break;
			case R.id.overflow_want_to_watch:
				update(API.WILL_WATCH, isChecked);
				break;
			default: // do nothing

		}

		return true;

	}

	private void update(API collection, boolean isChecked) {

		if (isChecked) collection.store(getApplication(), movie);
		else collection.remove(getApplication(), movie);

	}

	private void determineIfChecked(MenuItem item, API api, Movie movie) {

		item.setChecked(api.has(getApplication(), movie));

	}

	@Override
	protected void onCreate(final Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secondary);

		final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
		final CollapsingToolbarLayout collapsingToolbar =
				(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
		final ImageView headerImage = (ImageView) findViewById(R.id.header);

		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		MovieOverviewFragment.newInstance();

		collapsingToolbar.setTitle(movie.getName());
		//        toolbar.setTitle(movie.getName());

		Uri uri;
		int width;
		int height;

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

			uri = movie.getImageUriLarge();
			width = getResources().getDisplayMetrics().widthPixels;
			height = (int) (getResources().getDisplayMetrics().heightPixels / 1.618);

		} else {

			uri = movie.getImageUri();
			width = (int) (getResources().getDisplayMetrics().widthPixels / 1.618);
			height = getResources().getDisplayMetrics().heightPixels;

		}

		Picasso.with(this)
				.load(uri)
				.centerInside()
				.resize(width, height)
				.into(headerImage);

	}

}
