package com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.udacity.rucinskic.spotify_streamer.ui.custom.InfoListItemView;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.support.Indexer;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.ReleaseDate;

public class YearViewHolder extends BaseViewHolder {

	public YearViewHolder(View itemView, Indexer.Indexed index) {

		super(itemView, index);
	}

	@Override
	public void onLoadedDataBind(InfoListItemView parent, View customView, Movie data) {

		TextView txtPrimaryText = (TextView) customView;
		ReleaseDate release = data.getReleaseDate();

		txtPrimaryText.setText(release.getYear());
		parent.setSecondaryExtraText(release.getMonth());

	}

	@Override
	public View createNewInitializedView(Context context, FrameLayout layout) {

		TextView txtPrimaryText = new TextView(context, null, android.R.attr.textAppearanceMedium);
		txtPrimaryText.setTextColor(Color.BLACK);
		txtPrimaryText.setSingleLine(true);
		layout.addView(txtPrimaryText);

		return txtPrimaryText;

	}

}