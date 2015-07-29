package com.udacity.rucinskic.spotify_streamer.ui.support.model;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private String name;
    private Uri imageUri;
    private Uri imageUriLarge;

    private ReleaseDate releaseDate;
    private Rating rating;
    private String overview;

    private List<Video> videos;
    private List<Review> reviews;

    public Movie(final String name, final Uri imageUri) {

        this.name = name;
        this.imageUri = imageUri;

        // Default values
        this.imageUriLarge = imageUri;
        this.overview = "";

        this.videos = new ArrayList<>();
        this.reviews = new ArrayList<>();

    }

    public void addReview(String author, String content, Uri url) {

        reviews.add(new Review(author, content, url));

    }

    public void addVideo(String name, String type, Uri url) {

        videos.add(new Video(name, type, url));

    }

    public List<Video> getVideos() { return this.videos; }

    public List<Review> getReviews() { return this.reviews; }

    public CharSequence getName() { return this.name; }
    public Uri getImageUri() { return this.imageUri; }
    public Uri getImageUriLarge() { return this.imageUriLarge; }

    public ReleaseDate getReleaseDate() { return this.releaseDate; }

    public Rating getRating() { return this.rating; }
    public CharSequence getOverview() { return this.overview; }

    public void setName(final String name) { this.name = name; }
    public void setImageUriLarge(final Uri imageUri) { this.imageUriLarge = imageUri; }

    public void setRating(final Rating rating) { this.rating = rating; }
    public void setOverview(final String overview) {

        if (overview.equals("null")) return;

        this.overview = overview;

    }
    public void setDateRelease(final String date) {

        if (date.equals("null")) { return; }

        try {

            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 6));

            releaseDate = new ReleaseDate(year, month);

        } catch (Exception ignore) {}

    }

}
