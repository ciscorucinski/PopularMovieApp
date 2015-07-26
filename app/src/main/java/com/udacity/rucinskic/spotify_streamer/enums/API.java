package com.udacity.rucinskic.spotify_streamer.enums;

import android.net.Uri;

import com.udacity.rucinskic.spotify_streamer.App;
import com.udacity.rucinskic.spotify_streamer.interfaces.Downloadable;
import com.udacity.rucinskic.spotify_streamer.interfaces.Groupable;
import com.udacity.rucinskic.spotify_streamer.ui.support.Movie;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum API implements Downloadable<Movie>, Groupable<API> {

    POPULAR     ("Most Popular",    Uri.parse("http://api.themoviedb.org/3/movie/").buildUpon()),
    TOP_RATED   ("Highest Rated",   Uri.parse("http://api.themoviedb.org/3/movie/").buildUpon()),
    UPCOMING    ("Upcoming",        Uri.parse("http://api.themoviedb.org/3/movie/").buildUpon()),
    SEARCH      ("Search",    Uri.parse("http://api.themoviedb.org/3/search/movie").buildUpon()),

    FAVORITE("Favorites", Uri.parse("http://api.themoviedb.org/3/movie/").buildUpon()),
    WILL_WATCH("Want To Watch", Uri.parse("http://api.themoviedb.org/3/movie/").buildUpon()),
    WATCHED("Watched", Uri.parse("http://api.themoviedb.org/3/movie/").buildUpon());

    public static final EnumSet<API> WEB_GROUP = EnumSet.of(POPULAR, TOP_RATED, UPCOMING);
    public static final EnumSet<API> SEARCH_GROUP = EnumSet.of(SEARCH);
    public static final EnumSet<API> OFFLINE_GROUP = EnumSet.of(FAVORITE, WATCHED, WILL_WATCH);

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
        else if (this.isFrom(OFFLINE_GROUP)) {} // TODO why don't you do something

        uri.appendQueryParameter("api_key", App.getApiKey());

        return uri.build();

    }

    @Override
    public void clearDownloadables() { this.movies.clear(); }

    @Override
    public List<Movie> getDownloadables() { return this.movies; }

    @Override
    public boolean store(final Movie movie) { return this.movies.add(movie);}

    @Override
    public String toString() { return title; }

    @Override
    public boolean isFrom(final EnumSet set) { return this.getGroup().equals(set); }

    @Override
    public EnumSet<API> getGroup() {

        if (WEB_GROUP.contains(this)) return WEB_GROUP;
        if (SEARCH_GROUP.contains(this)) return SEARCH_GROUP;
        if (OFFLINE_GROUP.contains(this)) return OFFLINE_GROUP;

        return EnumSet.noneOf(API.class);

    }

}
