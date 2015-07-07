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
import com.udacity.rucinskic.spotify_streamer.interfaces.NetworkCacheable;
import com.udacity.rucinskic.spotify_streamer.interfaces.OnItemClickListener;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.Holder> {

    NetworkCacheable<Movie> webSearch;
    Context context;
    OnItemClickListener listener;

    public MovieListAdapter(Context context, NetworkCacheable webSearch, OnItemClickListener listener) {

        this.context = context;
        this.webSearch = webSearch;
        this.listener = listener;

    }

    public void clearAdapter() {

        this.webSearch = null;
        this.context = null;
        this.listener = null;

    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);

        return new Holder(view, listener);

    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Movie movie = webSearch.getCache().get(position);
//                movies.get(position);

        holder.txtName.setText(movie.getName());
        Picasso.with(context)
                .load(movie.getImageUri())
                .error(R.drawable.ic_not_available_white)
                .into(holder.imgImage);

    }

    @Override
    public int getItemCount() { return webSearch.getCache().size(); }


    class Holder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        private ImageView imgImage;
        private TextView txtName;
        private OnItemClickListener listener;

        public Holder(View itemView, OnItemClickListener listener) {

            super(itemView);

            this.listener = listener;

            imgImage = (ImageView) itemView.findViewById(R.id.image);
            txtName = (TextView) itemView.findViewById(R.id.name);

            imgImage.setOnClickListener(this);
            txtName.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            listener.onClick(v, getAdapterPosition());

        }

    }

}
