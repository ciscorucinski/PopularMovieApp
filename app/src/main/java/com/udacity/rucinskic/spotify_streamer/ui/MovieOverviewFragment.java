package com.udacity.rucinskic.spotify_streamer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.rucinskic.spotify_streamer.App;
import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.MovieDetailAdapter;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;

public class MovieOverviewFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        Movie movie = Movie.getMovie(App.getMovieID());

        final MovieDetailAdapter adapter = new MovieDetailAdapter(getActivity(), movie);
        final View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        RecyclerView movieDetails;

        movieDetails = (RecyclerView) rootView.findViewById(R.id.movie_detail_list);
        movieDetails.setAdapter(adapter);
        movieDetails.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;

    }

    public static MovieOverviewFragment newInstance() {

        MovieOverviewFragment fragment = new MovieOverviewFragment();
        return fragment;

    }

}

