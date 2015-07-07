package com.udacity.rucinskic.spotify_streamer.ui;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.udacity.rucinskic.spotify_streamer.R;

public class MoviePosterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_poster, container, false);

        ImageView poster = (ImageView) rootView.findViewById(R.id.movie_poster);
        //poster.setImageResource(R.drawable.download);

        CollapsingToolbarLayout toolbar = (CollapsingToolbarLayout)rootView.findViewById(R.id.collapsing_toolbar);
        toolbar.setTitle("Avengers: Age of Ultron");

        return rootView;

    }

}

