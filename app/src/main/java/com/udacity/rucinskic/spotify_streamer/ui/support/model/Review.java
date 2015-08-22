package com.udacity.rucinskic.spotify_streamer.ui.support.model;

import android.net.Uri;

public class Review {

	private final String author;
	private final String content;
	private final Uri url;

	public Review(String author, String content, Uri url) {

		this.author = author;
		this.content = content;
		this.url = url;

	}

	public String getAuthor() { return this.author; }

	public String getContent() { return this.content; }

	public Uri getUrl() { return this.url; }

}