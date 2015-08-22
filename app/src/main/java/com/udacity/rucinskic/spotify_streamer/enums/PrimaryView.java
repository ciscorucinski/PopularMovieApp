package com.udacity.rucinskic.spotify_streamer.enums;

import android.support.annotation.DrawableRes;
import android.view.View;

import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder.BaseViewHolder;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder.OverviewViewHolder;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder.RatingViewHolder;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder.ReviewViewHolder;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder.VideoViewHolder;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder.YearViewHolder;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.support.Indexer;

public enum PrimaryView {

	RELEASE("Release", -1) {
		@Override
		public BaseViewHolder getViewHolder(View v, Indexer.Indexed index) {

			return new YearViewHolder(v, index);
		}
	},
	OVERVIEW("Overview", R.drawable.ic_more_black_24dp) {
		@Override
		public BaseViewHolder getViewHolder(View v, Indexer.Indexed index) {

			return new OverviewViewHolder(v, index);
		}
	},
	RATING("Rating", -1) {
		@Override
		public BaseViewHolder getViewHolder(View v, Indexer.Indexed index) {

			return new RatingViewHolder(v, index);
		}
	},
	VIDEO("Video", R.drawable.ic_youtube_black_24dp) {
		@Override
		public BaseViewHolder getViewHolder(View v, Indexer.Indexed index) {

			return new VideoViewHolder(v, index);
		}
	},
	REVIEW("Review", R.drawable.ic_more_black_24dp) {
		@Override
		public BaseViewHolder getViewHolder(View v, Indexer.Indexed index) {

			return new ReviewViewHolder(v, index);
		}
	};

	private static final int NO_ICON = -1;
			// Cannot assign "NO_ICON" above. Enums don't allow it, so I have to assign the number directly

	private final String description;
	private final @DrawableRes int icon;
	private final boolean hasIcon;

	PrimaryView(String description, @DrawableRes int icon) {

		this.description = description;
		this.icon = icon;
		this.hasIcon = icon != NO_ICON;

	}

	public abstract BaseViewHolder getViewHolder(View view, Indexer.Indexed index);

	public String getDescription() { return this.description; }

	public
	@DrawableRes
	int getIconResource() { return this.icon; }

	public boolean hasIcon() { return this.hasIcon; }


}
