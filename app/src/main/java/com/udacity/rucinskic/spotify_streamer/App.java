package com.udacity.rucinskic.spotify_streamer;

import android.app.Application;

import com.udacity.rucinskic.spotify_streamer.enums.API;
import com.udacity.rucinskic.spotify_streamer.ui.MovieListFragment;

public class App extends Application {

    private static MovieListFragment fragment;

    private static final String API_KEY = "49bbbeee4fa205e483b690e221eee29d";

    private static final int MOVIE_COLLECTION_LIMIT = 20;
    private static final int NUMBER_WEB_API_CALLS = API.WEB_GROUP.size() + API.SEARCH_GROUP.size();

    public static String getApiKey() { return API_KEY; }

    public static int getMovieCollectionLimit() { return MOVIE_COLLECTION_LIMIT; }
    public static int getNumberOfApiCalls() { return NUMBER_WEB_API_CALLS; }

    public static MovieListFragment getSearchFragment() { return fragment; }
    public static void setSearchFragment(final MovieListFragment fragment) {

        if (fragment == null) return;
        if (searchFragmentExists()) return;

        App.fragment = fragment;

    }

    private static boolean searchFragmentExists() { return App.fragment != null; }

    public static void notifySearchFragmentAdapter() { fragment.getAdapter().notifyDataSetChanged(); }

    public static void clearData() {

        API.SEARCH.clearDownloadables();

    }
}
