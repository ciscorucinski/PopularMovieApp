package com.udacity.rucinskic.spotify_streamer.interfaces;

import android.net.Uri;

import java.util.List;

public interface Downloadable<T> { // TODO not the best name. Rename for part 2

    boolean store(T entity);
    void clearDownloadables();
    List<T> getDownloadables();

    Uri getUri(String...parameters);

}
