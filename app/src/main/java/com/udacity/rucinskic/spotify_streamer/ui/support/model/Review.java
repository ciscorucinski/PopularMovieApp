package com.udacity.rucinskic.spotify_streamer.ui.support.model;

import android.net.Uri;

/**
 * Created by Christopher on 7/27/2015.
 */
public class Review {

    private String author;
    private String content;
    private Uri url;

    public Review(String author, String content, Uri url) {

        this.author = author;
        this.content = content;
        this.url = url;

    }

    public String getAuthor() { return this.author; }

    public String getContent() { return this.content; }

    public Uri getUrl() { return this.url; }

}