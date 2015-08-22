package com.udacity.rucinskic.spotify_streamer.ui;

import android.os.Bundle;

import com.udacity.rucinskic.spotify_streamer.App;
import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.enums.API;

import java.util.EnumSet;

public class MainActivity extends BaseSearchTabbedActivity {

	private static final String LOAD_PREFS = "SHOULD_LOAD_SHARED_PREFS";
	boolean needToLoadSharedPreferences = true;

	@Override
	EnumSet<API> getDefaultTabs() { return API.WEB_GROUP; }

	@Override
	protected void onCreate(final Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeToolbar();

		if (savedInstanceState == null) {

			App.loadSharedPreferences(getApplication());

		}

	}

}
