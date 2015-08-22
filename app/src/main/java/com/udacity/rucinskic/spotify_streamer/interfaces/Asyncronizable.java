package com.udacity.rucinskic.spotify_streamer.interfaces;

import android.os.AsyncTask;

import com.udacity.rucinskic.spotify_streamer.enums.API;

public interface Asyncronizable<X, Y, Z> {

	AsyncTask<X, Y, Z> getAsyncTask(API api);

}
