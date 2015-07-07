package com.udacity.rucinskic.spotify_streamer.interfaces;

import android.net.Uri;

import java.util.List;

public interface NetworkCacheable<T> { // TODO not the best name. Rename for part 2

    boolean setWholeCache(List<T> entities);
    boolean add(T entity);
    void clearCache();

    List<T> getCache();

    Uri getUri(String...parameters);

}
