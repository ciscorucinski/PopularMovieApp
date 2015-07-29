package com.udacity.rucinskic.spotify_streamer.ui.support.model;

/**
 * Created by Christopher on 7/27/2015.
 */
public class Rating {

    private double rating;
    private int quantity;

    public Rating(double averageRating, int numberOfRatings) {

        this.rating = averageRating;
        this.quantity = numberOfRatings;

    }

    public double getValue() { return this.rating; }

    public float getFloatValue() { return (float) this.rating; }

    public String getNumberOfRatings() { return String.valueOf(this.quantity); }

}