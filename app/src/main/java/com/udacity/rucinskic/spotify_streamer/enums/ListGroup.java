package com.udacity.rucinskic.spotify_streamer.enums;

import java.util.ArrayList;
import java.util.List;

public enum ListGroup {

    BASIC,
    VIDEO,
    REVIEW;

    private final List<Boolean> isHeaderList;

    ListGroup() { isHeaderList = new ArrayList<>(); }

    public static boolean isHeader(int index) {

        // Index assumes a "flat map" of the Lists, so I have to convert it to represent a jagged list
        for (ListGroup group : values()) {

            int groupSize = group.isHeaderList.size();

            // if the index is larger than the current group, then go to the next group and modify the index correctly
            if (index >= groupSize) {

                index -= groupSize;
                continue;

            }

            return group.isHeaderList.get(index);

        }

        return false; // Outside of bounds

    }

    public static void addViewToGroup(ListGroup group) {

        group.isHeaderList.add(group.isHeaderList.isEmpty());

    }

}
