package com.udacity.rucinskic.spotify_streamer.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.rucinskic.spotify_streamer.App;
import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.enums.API;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.MovieDetailAdapter;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;
import com.udacity.rucinskic.spotify_streamer.ui.support.web.ReviewJsonHandlerCallback;
import com.udacity.rucinskic.spotify_streamer.ui.support.web.VideoJsonHandlerCallback;
import com.udacity.rucinskic.spotify_streamer.ui.support.web.WebService;

import org.json.JSONException;

public class MovieOverviewFragment extends Fragment {

	private MovieDetailAdapter adapter;

	public static MovieOverviewFragment newInstance() { return new MovieOverviewFragment(); }

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
	                         final Bundle savedInstanceState) {

		RecyclerView movieDetails;

		final View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
		int ID = App.getMovieID();
		Movie movie = Movie.getMovie(ID);

		adapter = new MovieDetailAdapter(getActivity(), movie);

		new GetExtraMovieDataAsync().execute(ID);

		movieDetails = (RecyclerView) rootView.findViewById(R.id.movie_detail_list);
		movieDetails.setAdapter(adapter);
		movieDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
		movieDetails.setItemAnimator(new DefaultItemAnimator());
		movieDetails.setClickable(true);
		movieDetails.setHasFixedSize(true);

		return rootView;

	}

	private class GetExtraMovieDataAsync extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(final Integer... params) {

			int ID = params[0];
			String ID_String = String.valueOf(ID);

			Handler.Callback getVideosFromJSON = new VideoJsonHandlerCallback(ID);
			Handler.Callback getReviewsFromJSON = new ReviewJsonHandlerCallback(ID);

			try {

				// This will get the JSON from the webservice, and will use the CallBack to assign the List of movies.
				new WebService(API.VIDEOS.getUri(ID_String)).acquireData(getVideosFromJSON);
				new WebService(API.REVIEWS.getUri(ID_String)).acquireData(getReviewsFromJSON);

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(final Void ignore) {

			adapter.index(Movie.getMovie(App.getMovieID()));
			adapter.notifyDataSetChanged();

		}
	}
}

