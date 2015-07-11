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

    POPULAR     ("Most Popular",    Uri.parse("http://api.themoviedb.org/3/movie/").buildUpon()),
    TOP_RATED   ("Highest Rated",   Uri.parse("http://api.themoviedb.org/3/movie/").buildUpon()),
    UPCOMING    ("Upcoming",        Uri.parse("http://api.themoviedb.org/3/movie/").buildUpon()),
    SEARCH      ("Search",    Uri.parse("http://api.themoviedb.org/3/search/movie").buildUpon());

    public static final EnumSet<API> WEB_GROUP = EnumSet.of(POPULAR, TOP_RATED, UPCOMING);
    public static final EnumSet<API> SEARCH_GROUP = EnumSet.of(SEARCH);
    // private static final EnumSet<API> OFFLINE_GROUP = EnumSet.noneOf(API.class); // TODO part 2: make public and create enum constants for offline support - like favorites

    private final String title;
    private final Uri.Builder uri;
    private final List<Movie> movies;

    API(final String title, Uri.Builder uri) {

        this.title = title;
        this.uri = uri;
        this.movies = new ArrayList<>(App.getMovieCollectionLimit());

    }

    public Uri getUri(final String...parameters) {

        if (this.isFrom(WEB_GROUP)) { uri.appendPath(name().toLowerCase()); }
        else if (this.isFrom(SEARCH_GROUP)) { uri.appendQueryParameter("query", parameters[0]); }

        uri.appendQueryParameter("api_key", App.getApiKey());

        return uri.build();

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
