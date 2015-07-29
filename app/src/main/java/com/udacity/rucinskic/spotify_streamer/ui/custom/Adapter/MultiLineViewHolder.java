package com.udacity.rucinskic.spotify_streamer.ui.custom.Adapter;

import android.view.View;
import android.widget.TextView;

import com.udacity.rucinskic.spotify_streamer.R;

public class MultiLineViewHolder extends BaseViewHolder {

    public final TextView txtPrimaryText;

    public MultiLineViewHolder(View itemView, boolean isHeader) {

        super(itemView, isHeader);

        txtPrimaryText = (TextView) itemView.findViewById(R.id.movie_detail_primary_view);

    }

}