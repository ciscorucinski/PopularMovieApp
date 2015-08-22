package com.udacity.rucinskic.spotify_streamer;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.udacity.rucinskic.spotify_streamer.enums.API;
import com.udacity.rucinskic.spotify_streamer.ui.MovieListFragment;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.support.Indexer;
import com.udacity.rucinskic.spotify_streamer.ui.support.web.MovieIDJsonHandlerCallback;
import com.udacity.rucinskic.spotify_streamer.ui.support.web.WebService;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

	private static final String API_KEY = "49bbbeee4fa205e483b690e221eee29d";
	private static final int MOVIE_COLLECTION_LIMIT = 20;
	private static final int NUMBER_WEB_API_CALLS = API.WEB_GROUP.size() + API.SEARCH_GROUP.size();
	private static MovieListFragment searchFragment;
	private static int movieID;
	private static Indexer indexer;

	public static String getApiKey() { return API_KEY; }

	public static int getMovieCollectionLimit() { return MOVIE_COLLECTION_LIMIT; }

	public static int getNumberOfApiCalls() { return NUMBER_WEB_API_CALLS; }

	public static MovieListFragment getSearchFragment() { return searchFragment; }

	public static void setSearchFragment(final MovieListFragment fragment) {

		if (fragment == null) return;
		if (searchFragmentExists()) return;

		App.searchFragment = fragment;

	}

	private static boolean searchFragmentExists() { return App.searchFragment != null; }

	public static void notifySearchFragmentAdapter() {

		searchFragment.getAdapter().notifyDataSetChanged();

	}

	public static void clearData() { API.SEARCH.clearDownloadables(); }

	public static void saveMovieID(int ID) { movieID = ID; }

	public static int getMovieID() { return movieID; }

	public static Indexer getMovieIndexer() { return indexer; }

	public static void setMovieIndexer(@NonNull Indexer indexer) { App.indexer = indexer; }

	public static void clearIndexer() {

		if (indexer == null) return;

		indexer.clear();
		indexer = null;

	}

	public static void loadSharedPreferences(Context context) {

		// Logging messages left in to view Shared Preferences. I filter out all logs except for ERROR; hence why I am printing error messages.

		API[] prefs = new API[] {API.FAVORITE, API.WATCHED, API.WILL_WATCH};
		List<Integer> movieIDs = new ArrayList<>();

		Log.i("Loading Shared Prefs", "-----------------------------------");
		Log.i("----------------", "---------------------------------------");

		for (API api : prefs) {

			String pref_name = api.name();

			SharedPreferences preference = context.getSharedPreferences(pref_name, MODE_PRIVATE);
			for (String key : preference.getAll().keySet()) {

				movieIDs.add(Integer.parseInt(key));
				Log.i(String.format("Shared Preference : %s - %s", pref_name, key),
				      preference.getString(key, "error!"));

			}

			Log.i("----------------", "---------------------------------------");

			Integer[] ids = movieIDs.toArray(new Integer[movieIDs.size()]);
			new GetMoviesByIDAsync(context, api).execute(ids);
			movieIDs.clear();

		}

		Log.i("Finished Shared Prefs", "----------------------------------");


	}

	private static class GetMoviesByIDAsync extends AsyncTask<Integer, Void, Void> {

		private final Handler.Callback getMovieByIdFromJSON;
		private final API api;

		public GetMoviesByIDAsync(Context context, API api) {

			this.api = api;
			getMovieByIdFromJSON = new MovieIDJsonHandlerCallback(context, api);

		}

		@Override
		protected Void doInBackground(final Integer... params) {

			try {

				for (int id : params) {
					// This will get the JSON from the webservice, and will use the CallBack to assign the List of movies.
					new WebService(api.getUri(String.valueOf(id)))
							.acquireData(getMovieByIdFromJSON);

				}

			} catch (JSONException ignored) {
			}

			return null;

		}
	}
}
