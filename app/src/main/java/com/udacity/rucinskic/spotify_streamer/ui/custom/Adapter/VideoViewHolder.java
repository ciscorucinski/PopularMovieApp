package com.udacity.rucinskic.spotify_streamer.ui.custom.Adapter;

import android.view.View;
import android.widget.VideoView;

import com.udacity.rucinskic.spotify_streamer.R;

public class VideoViewHolder extends BaseViewHolder {

    public final VideoView vidPrimaryView;

    public VideoViewHolder(View itemView, boolean isHeader) {

        super(itemView, isHeader);

        vidPrimaryView = (VideoView) itemView.findViewById(R.id.movie_detail_primary_view);

    }

}