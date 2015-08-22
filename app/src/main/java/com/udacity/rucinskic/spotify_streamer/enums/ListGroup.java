package com.udacity.rucinskic.spotify_streamer.enums;

import android.support.annotation.DrawableRes;

import com.udacity.rucinskic.spotify_streamer.R;

import java.util.ArrayList;
import java.util.List;

public enum ListGroup {

	BASIC(R.drawable.ic_information_black_24dp),
	VIDEO(R.drawable.ic_video_library_black_24dp),
	REVIEW(R.drawable.ic_rate_review_black_24dp);

	private @DrawableRes final int icon;

	private final List<Boolean> listHeaders;
	private final List<PrimaryView> listViews;
	private final List<Boolean> listIsAvailable;

	private int size;


	ListGroup(@DrawableRes int icon) {

		this.icon = icon;
		listHeaders = new ArrayList<>();
		listViews = new ArrayList<>();
		listIsAvailable = new ArrayList<>();

	}

	public static void addViewToGroup(ListGroup group, PrimaryView view, boolean isAvailable) {

		// Create jagged Lists where the first item (the header) is true. False otherwise
		// It is easier to determine headers here rather than while flattening
		group.listHeaders.add(group.listHeaders.isEmpty());
		group.listViews.add(view);
		group.listIsAvailable.add(isAvailable);
		group.size++;

	}

	public
	@DrawableRes
	int getIconResource() { return this.icon; }

	public List<Boolean> getHeaderList() { return this.listHeaders; }

	public List<PrimaryView> getViewTypeList() { return this.listViews; }

	public List<Boolean> getIsAvailableList() { return this.listIsAvailable; }

	public void clearData() {

		listHeaders.clear();
		listViews.clear();
		listIsAvailable.clear();
		size = 0;

	}

	public int getSize() { return this.size; }


}
