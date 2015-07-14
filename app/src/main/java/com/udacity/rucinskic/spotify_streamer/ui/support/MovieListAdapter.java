package com.udacity.rucinskic.spotify_streamer.ui.support;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.interfaces.Downloadable;
import com.udacity.rucinskic.spotify_streamer.interfaces.OnItemClickListener;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.Holder> {

    private final Downloadable<Movie> webSearch;
    private final Context context;
    private final OnItemClickListener listener;

    public MovieListAdapter(final Context context, final Downloadable<Movie> webSearch, final OnItemClickListener listener) {

        this.context = context;
        this.webSearch = webSearch;
        this.listener = listener;

    }

    @Override
    public Holder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.movie_list_item, parent, false);

        return new Holder(view, listener);

    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        Movie movie = webSearch.getDownloadables().get(position);

        holder.txtName.setText(movie.getName());
        Picasso.with(context)
                .load(movie.getImageUri())
                .error(R.drawable.ic_not_available_white)
                .into(holder.imgImage);

    }

    @Override
    public int getItemCount() { return webSearch.getDownloadables().size(); }

    public void clear() { this.webSearch.clearDownloadables(); }

    static class Holder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        private final ImageView imgImage;
        private final TextView txtName;
        private final OnItemClickListener listener;

        public Holder(final View itemView, final OnItemClickListener listener) {

            super(itemView);

            this.listener = listener;

            imgImage = (ImageView) itemView.findViewById(R.id.image);
            txtName = (TextView) itemView.findViewById(R.id.name);

            imgImage.setOnClickListener(this);
            txtName.setOnClickListener(this);

        }

        @Override
        public void onClick(final View v) {

            listener.onClick(v, getAdapterPosition());

        }

    }

}
