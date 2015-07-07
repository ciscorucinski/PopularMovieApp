package com.udacity.rucinskic.spotify_streamer;

import android.app.Application;

import com.udacity.rucinskic.spotify_streamer.enums.API;
import com.udacity.rucinskic.spotify_streamer.ui.MovieListFragment;

public class App extends Application {

    private static App singleton;
    private static MovieListFragment fragment;

    private static final String API_KEY = "49bbbeee4fa205e483b690e221eee29d";

    public static App getInstance(){ return singleton; }

    public static String getApiKey() { return API_KEY; }

    @Override
    public void onCreate() {

        super.onCreate();
        singleton = this;

    }

    public MovieListFragment getSearchFragment() { return fragment; }
    public void setSearchFragment(MovieListFragment fragment) {

        if (fragment == null) return;
        if (App.fragment != null) return;

        App.fragment = fragment;

    }

    public void notifySearchFragmentAdapter() { fragment.getAdapter().notifyDataSetChanged(); }

    public void clearData() {

        API.SEARCH.clearCache();

    }
}
