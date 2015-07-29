package com.udacity.rucinskic.spotify_streamer.ui.custom.Adapter;

import android.view.View;
import android.widget.RatingBar;

import com.udacity.rucinskic.spotify_streamer.R;

public class RatingViewHolder extends BaseViewHolder {

    public final RatingBar ratePrimaryView;

    public RatingViewHolder(View itemView, boolean isHeader) {

        super(itemView, isHeader);

        ratePrimaryView = (RatingBar) itemView.findViewById(R.id.movie_detail_primary_view);


    }

}