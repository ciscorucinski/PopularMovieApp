package com.udacity.rucinskic.spotify_streamer.interfaces;

import java.util.EnumSet;

public interface Groupable<T extends Enum<T>> {

    EnumSet<T> getParentGroup();
    boolean isFrom(EnumSet set);

}
