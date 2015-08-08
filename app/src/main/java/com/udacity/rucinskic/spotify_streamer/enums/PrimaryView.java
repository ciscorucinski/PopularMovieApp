package com.udacity.rucinskic.spotify_streamer.enums;

import android.view.View;

import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder.BaseViewHolder;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder.MultiLineViewHolder;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder.RatingViewHolder;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder.SingleLineViewHolder;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder.VideoViewHolder;

public enum PrimaryView {

    TEXT_SHORT {
        @Override
        public BaseViewHolder getViewHolder(View v, boolean isHeader) {

            return new SingleLineViewHolder(v, isHeader);
        }

    },
    TEXT_LONG {
        @Override
        public BaseViewHolder getViewHolder(View v, boolean isHeader) {

            return new MultiLineViewHolder(v, isHeader);
        }

    },
    RATING {
        @Override
        public BaseViewHolder getViewHolder(View v, boolean isHeader) {

            return new RatingViewHolder(v, isHeader);
        }

    },
    VIDEO {
        @Override
        public BaseViewHolder getViewHolder(View v, boolean isHeader) {

            return new VideoViewHolder(v, isHeader);
        }

    },
    REVIEW {
        @Override
        public BaseViewHolder getViewHolder(View v, boolean isHeader) {

            return new SingleLineViewHolder(v, isHeader);
        }

    },
    MISSING {
        @Override
        public BaseViewHolder getViewHolder(View v, boolean isHeader) {

            return new SingleLineViewHolder(v, isHeader);
        }

    };

    public abstract BaseViewHolder getViewHolder(View view, boolean isHeader);

}
