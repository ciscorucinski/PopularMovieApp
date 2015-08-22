package com.udacity.rucinskic.spotify_streamer.ui.support.web;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReviewJsonHandlerCallback implements Handler.Callback {

	private final int ID;

	public ReviewJsonHandlerCallback(int ID) { this.ID = ID; }

	@Override
	public boolean handleMessage(final Message msg) {

		// These are the names of the JSON objects that need to be extracted.
		final String RESULTS = "results";
		final String AUTHOR = "author";
		final String CONTENT = "content";
		final String URL = "url";

		try {

			JSONObject movieListJson = (JSONObject) msg.obj;
			JSONArray movieArray = movieListJson.getJSONArray(RESULTS);

			Movie movie = Movie.getMovie(ID);

			for (int i = 0; i < movieArray.length(); i++) {

				JSONObject movieJson = movieArray.getJSONObject(i);

				String author = movieJson.getString(AUTHOR);
				String content = movieJson.getString(CONTENT);
				String urlString = movieJson.getString(URL);

				movie.addReview(author, content, Uri.parse(urlString));

			}

		} catch (JSONException e) { return false; }

		return true;

	}
}
