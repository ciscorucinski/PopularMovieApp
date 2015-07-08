package com.udacity.rucinskic.spotify_streamer.enums;

import android.net.Uri;

import com.udacity.rucinskic.spotify_streamer.App;
import com.udacity.rucinskic.spotify_streamer.interfaces.Groupable;
import com.udacity.rucinskic.spotify_streamer.interfaces.NetworkCacheable;
import com.udacity.rucinskic.spotify_streamer.ui.support.Movie;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum API implements NetworkCacheable<Movie>, Groupable<API> {

    POPULAR     ("Most Popular"),
    TOP_RATED   ("Highest Rated"),
    UPCOMING    ("Upcoming"),
    SEARCH      ("Search");

    public static final EnumSet<API> WEB_GROUP = EnumSet.of(POPULAR, TOP_RATED, UPCOMING);
    public static final EnumSet<API> SEARCH_GROUP = EnumSet.of(SEARCH);
    // private static final EnumSet<API> OFFLINE_GROUP = EnumSet.noneOf(API.class); // TODO part 2: make public and create enum constants for offline support - like favorites

    private final String title;
    private final List<Movie> movies;

    API(final String title) {

        this.title = title;
        this.movies = new ArrayList<>(App.getMovieCollectionLimit());

    }

    public Uri getUri(final String...parameters) {   // TODO refactor this for part 2. It appears messy

        if (this == SEARCH) {

            if (parameters.length == 0) return null;

            return Uri.parse("http://api.themoviedb.org/3/search/movie?").buildUpon()
                    .appendQueryParameter("api_key", App.getApiKey())
                    .appendQueryParameter("query", parameters[0])
                    .build();

        }

        return Uri.parse("http://api.themoviedb.org/3/movie/").buildUpon()
                .appendPath(this.name().toLowerCase())
                .appendQueryParameter("api_key", App.getApiKey())
                .build();

    }

    @Override
    public void clearCache() { this.movies.clear(); }

    @Override
    public List<Movie> getCache() { return this.movies; }

    @Override
    public boolean add(final Movie movie) { return this.movies.add(movie);}

    @Override
    public String toString() { return title; }

    @Override
    public boolean isFrom(final EnumSet set) { return this.getParentGroup().equals(set); }

    @Override
    public EnumSet<API> getParentGroup() {

        if (WEB_GROUP.contains(this)) return WEB_GROUP;
        if (SEARCH_GROUP.contains(this)) return SEARCH_GROUP;

        return EnumSet.noneOf(API.class);

    }

}
