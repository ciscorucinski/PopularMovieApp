package com.udacity.rucinskic.spotify_streamer.ui.support;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.interfaces.Downloadable;

public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.Holder> {

    private final Downloadable<Movie> webSearch;
    private final Context context;

    public MovieDetailAdapter(final Context context, final Downloadable<Movie> webSearch) {

        this.context = context;
        this.webSearch = webSearch;

    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {

//        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        final View view = inflater.inflate(R.layout.movie_list_item, parent, false);
//
//        return new Holder(view);

    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

//        Movie movie = webSearch.getDownloadables().get(position);
//
//        holder.txtName.setText(movie.getName());
//        Picasso.with(context)
//                .load(movie.getImageUri())
//                .error(R.drawable.ic_not_available_white)
//                .into(holder.imgImage);

    }

    @Override
    public int getItemCount() { return webSearch.getDownloadables().size(); }


    static class Holder extends RecyclerView.ViewHolder {

        private final ImageView imgImage;
        private final TextView txtName;

        public Holder(final View itemView) {

            super(itemView);

            imgImage = (ImageView) itemView.findViewById(R.id.image);
            txtName = (TextView) itemView.findViewById(R.id.name);

        }

    }

}
