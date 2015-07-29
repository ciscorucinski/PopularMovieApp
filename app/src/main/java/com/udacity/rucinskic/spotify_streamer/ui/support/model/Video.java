package com.udacity.rucinskic.spotify_streamer.ui.support.model;

import android.net.Uri;

/**
 * Created by Christopher on 7/27/2015.
 */
public class Video {

    private String type;
    private String name;
    private Uri url;

    public Video(String name, String type, Uri url) {

        this.type = type;
        this.name = name;
        this.url = url;

    }

    public String getType() { return this.type; }

    public String getName() { return this.name; }

    public Uri getUrl() { return this.url; }

}