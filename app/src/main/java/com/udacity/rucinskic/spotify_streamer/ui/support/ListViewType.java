package com.udacity.rucinskic.spotify_streamer.ui.support;

import android.view.View;

import com.udacity.rucinskic.spotify_streamer.enums.ListGroup;
import com.udacity.rucinskic.spotify_streamer.enums.PrimaryView;
import com.udacity.rucinskic.spotify_streamer.ui.custom.Adapter.BaseViewHolder;

public class ListViewType {

    private final ListGroup group;
    private final PrimaryView viewType;

    ListViewType(ListGroup group, PrimaryView viewType) {

        this.viewType = viewType;
        this.group = ListGroup.addViewToGroup(group);

    }

    public BaseViewHolder getViewHolder(View view, int position) {

        return viewType.getViewHolder(view, group.isHeader(position));

    }

}