package com.udacity.rucinskic.spotify_streamer.interfaces;

import android.view.View;

public interface DataBinder<V, D> {

	void onLoadedDataBind(V parent, View customView, D data);

	void onMissingDataBind(V parent);

	void sharedDataBinding(V parent);

}