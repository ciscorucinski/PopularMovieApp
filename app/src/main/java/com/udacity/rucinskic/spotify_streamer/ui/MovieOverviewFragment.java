package com.udacity.rucinskic.spotify_streamer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.rucinskic.spotify_streamer.R;

public class MovieOverviewFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie_detail, container, false);

    }

}

