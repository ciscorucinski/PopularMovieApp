package com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RatingBar;

import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.ui.custom.InfoListItemView;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.support.Indexer;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Rating;

public class RatingViewHolder extends BaseViewHolder {

	public RatingViewHolder(View itemView, Indexer.Indexed index) {

		super(itemView, index);
	}

	@Override
	public void onLoadedDataBind(InfoListItemView parent, View customView, Movie data) {

		RatingBar ratePrimaryView = (RatingBar) customView;
		Rating rating = data.getRating();

		float halfRating = rating.getFloatValue() / 2; // I use 5 stars with a 10pt rating
		int numberOfRatings = rating.getNumberOfRatings();
		String formattedRatings = getContext().getResources()
				.getQuantityString(R.plurals.number_of_ratings, numberOfRatings, numberOfRatings);

		ratePrimaryView.setRating(halfRating);
		parent.setSecondaryExtraText(formattedRatings);

	}

	@Override
	public View createNewInitializedView(Context context, FrameLayout layout) {

		RatingBar ratePrimaryView =
				new RatingBar(context, null, android.R.attr.ratingBarStyleSmall);
		layout.addView(ratePrimaryView);

		return ratePrimaryView;

	}

}