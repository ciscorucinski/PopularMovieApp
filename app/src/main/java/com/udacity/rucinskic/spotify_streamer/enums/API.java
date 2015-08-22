package com.udacity.rucinskic.spotify_streamer.enums;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.udacity.rucinskic.spotify_streamer.App;
import com.udacity.rucinskic.spotify_streamer.interfaces.Downloadable;
import com.udacity.rucinskic.spotify_streamer.interfaces.Groupable;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum API implements Downloadable<Movie>, Groupable<API> {

	POPULAR     ("Most Popular") {
		@Override Uri.Builder appendUri(Uri.Builder builder, String... params) {
			return builder.appendPath(name().toLowerCase());
		}
	},
	TOP_RATED   ("Highest Rated") {
		@Override Uri.Builder appendUri(Uri.Builder builder, String... params) {
			return builder.appendPath(name().toLowerCase());
		}
	},
	UPCOMING    ("Upcoming") {
		@Override Uri.Builder appendUri(Uri.Builder builder, String... params) {
			return builder.appendPath(name().toLowerCase());
		}
	},
	SEARCH      ("Search") {
		@Override Uri.Builder appendUri(Uri.Builder builder, String... params) {
			return builder.appendQueryParameter("query", params[0]);
		}
	},

	FAVORITE    ("Favorites") {
		@Override Uri.Builder appendUri(Uri.Builder builder, String... params) {
			return builder.appendPath(params[0]);
		}
	},
	WILL_WATCH  ("Want To Watch") {
		@Override Uri.Builder appendUri(Uri.Builder builder, String... params) {
			return builder.appendPath(params[0]);
		}
	},
	WATCHED     ("Watched") {
		@Override Uri.Builder appendUri(Uri.Builder builder, String... params) {
			return builder.appendPath(params[0]);
		}
	},

	VIDEOS      ("Videos") {
		@Override Uri.Builder appendUri(Uri.Builder builder, String... params) {
			return builder.appendPath(params[0]).appendPath(this.title.toLowerCase());
		}
	},
	REVIEWS     ("Reviews") {
		@Override Uri.Builder appendUri(Uri.Builder builder, String... params) {
			return builder.appendPath(params[0]).appendPath(this.title.toLowerCase());
		}
	};

	abstract Uri.Builder appendUri(Uri.Builder builder, String... params);

	private static final Uri BASE_URI =
			Uri.parse("http://api.themoviedb.org/3/movie/").buildUpon()
					.appendQueryParameter("api_key", App.getApiKey()).build();

	private static final Uri BASE_SEARCH_URI =
			Uri.parse("http://api.themoviedb.org/3/search/movie").buildUpon()
					.appendQueryParameter("api_key", App.getApiKey()).build();

	public static final EnumSet<API> WEB_GROUP = EnumSet.of(POPULAR, TOP_RATED, UPCOMING);
	public static final EnumSet<API> SEARCH_GROUP = EnumSet.of(SEARCH);
	public static final EnumSet<API> OFFLINE_GROUP = EnumSet.of(FAVORITE, WATCHED, WILL_WATCH);

	private static final EnumSet<API> MOVIE_DATA = EnumSet.of(VIDEOS, REVIEWS);

	final String title;
	private final List<Movie> movies;

	API(final String title) {

		this.title = title;
		this.movies = new ArrayList<>(App.getMovieCollectionLimit());

	}

	public Uri getUri(final String... parameters) {

		Uri uri = (this.isFrom(SEARCH_GROUP)) ? BASE_SEARCH_URI : BASE_URI;
		return appendUri(uri.buildUpon(), parameters).build();

	}

	@Override
	public boolean store(final Context context, final Movie movie) {

		if (this.isFrom(OFFLINE_GROUP)) {

			SharedPreferences.Editor preference =
					context.getSharedPreferences(this.name(), Context.MODE_PRIVATE).edit();

			int id = movie.getID();
			String name = movie.getName().toString();

			preference.putString(String.valueOf(id), name);
			preference.commit();

		}

		return this.movies.add(movie);

	}

	@Override
	public boolean remove(final Context context, final Movie movie) {

		if (this.isFrom(OFFLINE_GROUP)) {

			SharedPreferences.Editor preference =
					context.getSharedPreferences(this.name(), Context.MODE_PRIVATE).edit();

			int id = movie.getID();

			preference.remove(String.valueOf(id));
			preference.commit();

		}

		return this.movies.remove(movie);

	}

	@Override
	public boolean has(Context context, Movie movie) {

		if (this.isFrom(OFFLINE_GROUP)) {

			SharedPreferences preference =
					context.getSharedPreferences(this.name(), Context.MODE_PRIVATE);

			int id = movie.getID();

			return preference.contains(String.valueOf(id));

		}

		return this.movies.contains(movie);

	}

	@Override
	public void clearDownloadables() { this.movies.clear(); }

	@Override
	public List<Movie> getDownloadables() { return this.movies; }

	@Override
	public EnumSet<API> getGroup() {

		if (WEB_GROUP.contains(this)) return WEB_GROUP;
		if (SEARCH_GROUP.contains(this)) return SEARCH_GROUP;
		if (OFFLINE_GROUP.contains(this)) return OFFLINE_GROUP;
		if (MOVIE_DATA.contains(this)) return MOVIE_DATA;

		return EnumSet.noneOf(API.class);

	}

	@Override
	public boolean isFrom(final EnumSet set) { return this.getGroup().equals(set); }

	@Override
	public String toString() { return title; }

}
