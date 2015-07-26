package com.udacity.rucinskic.spotify_streamer.ui.support;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.udacity.rucinskic.spotify_streamer.R;

/**
 * Created by Christopher on 7/24/2015.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

    }

}