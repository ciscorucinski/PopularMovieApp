package com.udacity.rucinskic.spotify_streamer.ui.support.model;

import android.net.Uri;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

public class Movie {

	private static final SparseArray<Movie> movies = new SparseArray<>();
	private final int ID;
	private final Uri imageUri;
	private final List<Video> videos;
	private final List<Review> reviews;
	private String name;
	private Uri imageUriLarge;
	private ReleaseDate releaseDate;
	private Rating rating;
	private String overview;

	public Movie(final String name, final Uri imageUri, int ID) {

		this.ID = ID;
		this.name = name;
		this.imageUri = imageUri;

		this.imageUriLarge = imageUri;
		this.overview = "";

		this.videos = new ArrayList<>();
		this.reviews = new ArrayList<>();

		movies.put(ID, this);

	}

	public static Movie getMovie(int ID) {

		return movies.get(ID);

	}

	public void addReview(String author, String content, Uri url) {

		reviews.add(new Review(author, content, url));

	}

	public void addVideo(String name, String type, int size, Uri url) {

		videos.add(new Video(name, type, size, url));

	}

	public List<Video> getVideos() { return this.videos; }

	public List<Review> getReviews() { return this.reviews; }

	public CharSequence getName() { return this.name; }

	public void setName(final String name) { this.name = name; }

	public Uri getImageUri() { return this.imageUri; }

	public Uri getImageUriLarge() { return this.imageUriLarge; }

	public void setImageUriLarge(final Uri imageUri) { this.imageUriLarge = imageUri; }

	public ReleaseDate getReleaseDate() { return this.releaseDate; }

	public Rating getRating() { return this.rating; }

	public void setRating(final Rating rating) { this.rating = rating; }

	public CharSequence getOverview() { return this.overview; }

	public void setOverview(final String overview) {

		if (overview.equals("null")) return;

		this.overview = overview;

	}

	public void setDateRelease(final String date) {

		if (date.equals("null")) {
			return;
		}

		try {

			int year = Integer.parseInt(date.substring(0, 4));
			int month = Integer.parseInt(date.substring(5, 7));

			this.releaseDate = new ReleaseDate(year, month);

		} catch (Exception ignore) {
		}

	}

	public int getID() { return ID; }
}
