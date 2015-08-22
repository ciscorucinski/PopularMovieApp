package com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.udacity.rucinskic.spotify_streamer.ui.custom.InfoListItemView;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.support.Indexer;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Video;

public class VideoViewHolder extends BaseViewHolder {

	public VideoViewHolder(View itemView, Indexer.Indexed index) {

		super(itemView, index);
	}

	@Override
	public void onLoadedDataBind(final InfoListItemView parent, View customView, Movie data) {

		TextView txtVideoName = (TextView) customView;

		int videoIndex = getIndex().getVideoIndex();
		final Video video = data.getVideos().get(videoIndex);

		txtVideoName.setText(video.getName());

		parent.setSecondaryText(video.getType());
		parent.setSecondaryExtraText(video.getVideoSizeFormat());

		parent.setActionIcon(getIndex().getActionIconResource());
		parent.setMainActionOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent openYoutubeVideo = new Intent(Intent.ACTION_VIEW, video.getUrl());
				parent.getContext().startActivity(openYoutubeVideo);

			}
		});

	}

	@Override
	public View createNewInitializedView(Context context, FrameLayout layout) {

		TextView textView = new TextView(context, null, android.R.attr.textAppearanceSmall);
		textView.setTextColor(Color.BLACK);
		layout.addView(textView);

		return textView;

	}

}