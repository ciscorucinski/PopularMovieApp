package com.udacity.rucinskic.spotify_streamer.ui.custom.Adapter;

import android.text.TextUtils;
import android.view.View;

public class SingleLineViewHolder extends MultiLineViewHolder {

    public SingleLineViewHolder(View itemView, boolean isHeader) {

        super(itemView, isHeader);

        txtPrimaryText.setSingleLine(true);
        txtPrimaryText.setEllipsize(TextUtils.TruncateAt.END);

    }

}