package com.udacity.rucinskic.spotify_streamer.enums;

import java.util.ArrayList;
import java.util.List;

public enum ListGroup {

    BASIC,
    VIDEO,
    REVIEW;

    private final List<Boolean> isHeader;

    ListGroup() { isHeader = new ArrayList<>(); }

    public boolean isHeader(int index) { return isHeader.get(index); }

    public static ListGroup addViewToGroup(ListGroup group) {

        group.isHeader.add(group.isHeader.isEmpty());
        return group;

    }

}
