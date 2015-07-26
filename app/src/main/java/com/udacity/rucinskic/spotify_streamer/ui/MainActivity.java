package com.udacity.rucinskic.spotify_streamer.ui;

import android.os.Bundle;

import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.enums.API;

import java.util.EnumSet;

public class MainActivity extends BaseSearchTabbedActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeToolbar();

    }

    @Override
    EnumSet<API> getDefaultTabs() { return API.WEB_GROUP; }

}
