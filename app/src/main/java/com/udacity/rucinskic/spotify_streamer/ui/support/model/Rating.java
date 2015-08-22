package com.udacity.rucinskic.spotify_streamer.ui.support.model;

public class Rating {

	private final double rating;
	private final int quantity;

	public Rating(double averageRating, int numberOfRatings) {

		this.rating = averageRating;
		this.quantity = numberOfRatings;

	}

	public double getValue() { return this.rating; }

	public float getFloatValue() { return (float) this.rating; }

	public int getNumberOfRatings() { return this.quantity; }

}