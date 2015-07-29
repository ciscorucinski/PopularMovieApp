package com.udacity.rucinskic.spotify_streamer.ui.support;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.enums.ListGroup;
import com.udacity.rucinskic.spotify_streamer.enums.PrimaryView;
import com.udacity.rucinskic.spotify_streamer.ui.custom.Adapter.BaseViewHolder;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Rating;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.ReleaseDate;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Review;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Video;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private final Movie movie;
    private final Context context;
    private final List<ListViewType> viewTypes;

    public MovieDetailAdapter(final Context context, final Movie movie) {

        this.context = context;
        this.movie = movie;

        this.viewTypes = new ArrayList<>();

    }

    @Override
    public int getItemViewType(
            int position) { return position; } // Pass through. I have other ways to determine viewType

    @Override
    public BaseViewHolder onCreateViewHolder(final ViewGroup parent, final int position) {

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.movie_list_item, parent, false);

        ListViewType viewPosition = viewTypes.get(position);

        return viewPosition.getViewHolder(view, position);

    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {

        // TODO add this needed code for this

    }

    @Override
    public int getItemCount() {

        // I assume that Videos and Reviews can be of any size no less than 1 each. For basic information, such as date, rating and overview, if they are missing, those 3 views will still be there
        ReleaseDate date = movie.getReleaseDate();
        Rating rating = movie.getRating();
        CharSequence overview = movie.getOverview();
        List<Video> videos = movie.getVideos();
        List<Review> reviews = movie.getReviews();

        addViewTypeBasedOn(date != null && date.hasReleaseDate(),
                           ListGroup.BASIC, PrimaryView.TEXT_SHORT);

        addViewTypeBasedOn(rating != null,
                           ListGroup.BASIC, PrimaryView.RATING);

        addViewTypeBasedOn(overview.equals(""),
                           ListGroup.BASIC, PrimaryView.TEXT_LONG);

        addViewTypesToGroup(videos, ListGroup.VIDEO, PrimaryView.VIDEO);
        addViewTypesToGroup(reviews, ListGroup.REVIEW, PrimaryView.TEXT_SHORT);

        return viewTypes.size();

    }

    private void addViewMissing(ListGroup group) { addViewSpecific(group, PrimaryView.MISSING);}

    private void addViewSpecific(ListGroup group, PrimaryView view) {

        viewTypes.add(new ListViewType(group, view));

    }

    private void addViewTypeBasedOn(boolean viewNotMissing, ListGroup group, PrimaryView view) {

        if (viewNotMissing) addViewSpecific(group, view);

        addViewMissing(group);

    }

    private void addViewTypesToGroup(List list, ListGroup group, PrimaryView view) {

        if (list.size() == 0) {
            addViewMissing(group);
        }

        int i = 0;

        while (i < list.size()) {

            addViewSpecific(group, view);
            i++;

        }

    }


}
