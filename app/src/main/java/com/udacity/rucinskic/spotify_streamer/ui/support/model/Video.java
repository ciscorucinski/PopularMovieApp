package com.udacity.rucinskic.spotify_streamer.ui.support.model;

import android.net.Uri;

public class Video {

	private final String type;
	private final String name;
	private final int size;
	private final Uri url;

	public Video(String name, String type, int size, Uri url) {

		this.type = type;
		this.name = name;
		this.size = size;
		this.url = url;

	}

	public String getType() { return this.type; }

	public String getName() { return this.name; }

	public int getVideoSize() { return this.size; }

	public String getVideoSizeFormat() { return this.size + "p"; }

	public Uri getUrl() { return this.url; }

}