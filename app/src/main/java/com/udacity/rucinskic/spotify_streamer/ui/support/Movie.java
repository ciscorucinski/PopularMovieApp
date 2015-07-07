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

    public Movie(String name, Uri imageUri) {

        this.name = name;
        this.imageUri = imageUri;

        // Default values
        this.imageUriLarge = imageUri;
        this.imageUriSmall = imageUri;
        this.release = "";
        this.rating = 0;
        this.overview = "N / A";

    }

    public String getName() { return this.name; }
    public Uri getImageUri() { return this.imageUri; }
    public Uri getImageUriLarge() { return this.imageUriLarge; }
    public Uri getImageUriSmall() { return this.imageUriSmall; }
    public String getDateRelease() { return this.release; }
    public float getRating() { return (float)this.rating; }
    public String getOverview() { return this.overview; }

    public void setName(String name) { this.name = name; }
    public void setImageUri(Uri imageUri) { this.imageUri = imageUri; }
    public void setImageUriLarge(Uri imageUri) { this.imageUriLarge = imageUri; }
    public void setImageUriSmall(Uri imageUri) { this.imageUriSmall = imageUri; }
    public void setRating(double rating) { this.rating = rating; }
    public void setOverview(String overview) {

        if (overview == "null") return;

        this.overview = overview;

    }
    public void setDateRelease(String date) {

        if (date.equals("null")) { return; }

        try {

            int year = Integer.parseInt(date.substring(0, 4));
//            int month = Integer.parseInt(date.substring(5, 7));
//            int day = Integer.parseInt(date.substring(8, 10));

            release = String.valueOf(year);

        } catch (Exception ignore) {} // Default date of 0000 / 00 / 00 will be created instead

    }

}
