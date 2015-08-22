package com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.udacity.rucinskic.spotify_streamer.ui.custom.InfoListItemView;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.support.Indexer;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Review;

public class ReviewViewHolder extends BaseViewHolder {

	public ReviewViewHolder(View itemView, Indexer.Indexed index) {

		super(itemView, index);
	}

	@Override
	public void onLoadedDataBind(InfoListItemView parent, View customView, Movie data) {

		TextView txtPrimaryText = (TextView) customView;

		int reviewIndex = getIndex().getReviewIndex();
		final Review review = data.getReviews().get(reviewIndex);

		txtPrimaryText.setText(review.getContent());
		parent.setSecondaryExtraText(review.getAuthor());

		parent.setActionIcon(getIndex().getActionIconResource());
		parent.setMainActionOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				new AlertDialog.Builder(getContext()).setTitle("Review")
						.setMessage(review.getContent() + "\n\nBy " + review.getAuthor())
						.setNeutralButton("OK", null)
						.create().show();

			}
		});

	}

	@Override
	public View createNewInitializedView(Context context, FrameLayout layout) {

		TextView txtPrimaryText = new TextView(context, null, android.R.attr.textAppearanceSmall);
		txtPrimaryText.setTextColor(Color.BLACK);
		txtPrimaryText.setSingleLine(true);
		txtPrimaryText.setEllipsize(TextUtils.TruncateAt.END);
		layout.addView(txtPrimaryText);

		return txtPrimaryText;

	}

}