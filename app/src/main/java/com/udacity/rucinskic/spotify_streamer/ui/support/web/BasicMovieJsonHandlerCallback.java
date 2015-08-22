package com.udacity.rucinskic.spotify_streamer.ui.support.web;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.udacity.rucinskic.spotify_streamer.enums.API;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Rating;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BasicMovieJsonHandlerCallback implements Handler.Callback {

	private final API storage;
	private final Context context;

	public BasicMovieJsonHandlerCallback(Context context, API storage) {

		this.storage = storage;
		this.context = context;

	}

	@Override
	public boolean handleMessage(final Message msg) {

		// These are the names of the JSON objects that need to be extracted.
		final String ID_STRING = "id";
		final String RESULTS = "results";
		final String TITLE = "title";
		final String POSTER = "poster_path";
		final String RELEASE = "release_date";
		final String VOTE = "vote_average";
		final String NUM_VOTES = "vote_count";
		final String OVERVIEW = "overview";

		try {

			JSONObject movieListJson = (JSONObject) msg.obj;
			JSONArray movieArray = movieListJson.getJSONArray(RESULTS);

			Movie movie;

			for (int i = 0; i < movieArray.length(); i++) {

				JSONObject movieJson = movieArray.getJSONObject(i);

				int ID = movieJson.getInt(ID_STRING);
				String releaseDate = movieJson.getString(RELEASE);
				String title = movieJson.getString(TITLE);
				double voteAverage = movieJson.getDouble(VOTE);
				int voteCount = movieJson.getInt(NUM_VOTES);
				String overview = movieJson.getString(OVERVIEW);
				String filePath = movieJson.getString(POSTER);

				// Change filepath to URL
				String BASE_URL = "http://image.tmdb.org/t/p/";

				Uri uri = Uri.parse(BASE_URL).buildUpon()
						.appendPath("w300")
						.appendEncodedPath(filePath).build();

				Uri uriLarge = Uri.parse(BASE_URL).buildUpon()
						.appendPath("w500")
						.appendEncodedPath(filePath).build();

				movie = new Movie(title, uri, ID);

				movie.setOverview(overview);
				movie.setRating(new Rating(voteAverage, voteCount));
				movie.setDateRelease(releaseDate);
				movie.setImageUriLarge(uriLarge);

				storage.store(context, movie);

			}

		} catch (JSONException e) {
			return false;
		}

		return true;

	}
}
