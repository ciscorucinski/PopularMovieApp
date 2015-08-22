package com.udacity.rucinskic.spotify_streamer.ui.support.web;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoJsonHandlerCallback implements Handler.Callback {

	private final int ID;

	public VideoJsonHandlerCallback(int ID) { this.ID = ID; }

	@Override
	public boolean handleMessage(final Message msg) {

		Log.e("VideoJson handleMessage", "Start");
		// These are the names of the JSON objects that need to be extracted.
		final String RESULTS = "results";
		final String YOUTUBE_ID = "key";
		final String NAME = "name";
		final String TYPE = "type";
		final String SIZE = "size";

		try {

			JSONObject movieListJson = (JSONObject) msg.obj;
			JSONArray movieArray = movieListJson.getJSONArray(RESULTS);

			Movie movie = Movie.getMovie(ID);

			Log.e("VideoJson handleMessage", "Start looping Movie Array");
			for (int i = 0; i < movieArray.length(); i++) {

				JSONObject movieJson = movieArray.getJSONObject(i);

				String youTubeID = movieJson.getString(YOUTUBE_ID);
				String name = movieJson.getString(NAME);
				String type = movieJson.getString(TYPE);
				int size = movieJson.getInt(SIZE);

				Uri youTubeUri = new Uri.Builder()
						.scheme("https")
						.authority("www.youtube.com")
						.appendPath("watch")
						.appendQueryParameter("v", youTubeID)
						.build();

				Log.e("VideoJson handleMessage", "Add video to Movie Array");
				movie.addVideo(name, type, size, youTubeUri);

			}

		} catch (JSONException e) {
			return false;
		}

		return true;

	}
}
