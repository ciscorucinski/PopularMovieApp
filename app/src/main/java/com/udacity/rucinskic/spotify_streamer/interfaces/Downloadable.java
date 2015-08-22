package com.udacity.rucinskic.spotify_streamer.interfaces;

import android.content.Context;
import android.net.Uri;

import java.util.List;

public interface Downloadable<T> {

	boolean store(Context context, T entity);

	boolean remove(Context context, T entity);

	boolean has(Context context, T entity);

	void clearDownloadables();

	List<T> getDownloadables();

	Uri getUri(String... parameters);

}
