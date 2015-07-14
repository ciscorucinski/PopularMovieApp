package com.udacity.rucinskic.spotify_streamer.ui.support;

import android.net.Uri;

public class Movie {

    private String name;
    private Uri imageUri;
    private Uri imageUriLarge;
    private Uri imageUriSmall;
    private String release;
    private double rating;
    private String overview;

    public Movie(final String name, final Uri imageUri) {

        this.name = name;
        this.imageUri = imageUri;

        // Default values
        this.imageUriLarge = imageUri;
        this.imageUriSmall = imageUri;
        this.release = "";
        this.rating = 0;
        this.overview = "N / A";

    }

    public CharSequence getName() { return this.name; }
    public Uri getImageUri() { return this.imageUri; }
    public Uri getImageUriLarge() { return this.imageUriLarge; }
    public Uri getImageUriSmall() { return this.imageUriSmall; }
    public CharSequence getDateRelease() { return this.release; }
    public float getRating() { return (float)this.rating; }
    public CharSequence getOverview() { return this.overview; }

    public void setName(final String name) { this.name = name; }
    public void setImageUri(final Uri imageUri) { this.imageUri = imageUri; }
    public void setImageUriLarge(final Uri imageUri) { this.imageUriLarge = imageUri; }
    public void setImageUriSmall(final Uri imageUri) { this.imageUriSmall = imageUri; }
    public void setRating(final double rating) { this.rating = rating; }
    public void setOverview(final String overview) {

        if (overview.equals("null")) return;

        this.overview = overview;

    }
    public void setDateRelease(final String date) {

        if (date.equals("null")) { return; }

        try {

            int year = Integer.parseInt(date.substring(0, 4));
            release = String.valueOf(year);

        } catch (Exception ignore) {}

    }

}
