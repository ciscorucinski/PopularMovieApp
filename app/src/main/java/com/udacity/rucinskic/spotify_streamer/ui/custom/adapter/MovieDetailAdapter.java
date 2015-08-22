package com.udacity.rucinskic.spotify_streamer.ui.custom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.rucinskic.spotify_streamer.App;
import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.enums.ListGroup;
import com.udacity.rucinskic.spotify_streamer.enums.PrimaryView;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder.BaseViewHolder;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.support.Indexer;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.support.IndexerBuilder;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Rating;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.ReleaseDate;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Review;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Video;

import java.util.List;

public class MovieDetailAdapter extends RecyclerView.Adapter<BaseViewHolder> {

	private final Movie movie;
	private final Context context;
	private Indexer indexer;

	public MovieDetailAdapter(final Context context, final Movie movie) {

		this.context = context;
		this.movie = movie;

		//        indexFor(movie);

	}

	public void index(Movie movie) {

		// I assume that Videos and Reviews can be of any size no less than 1 each. For basic information, such as date, rating and overview, if they are missing, those 3 views will still be there
		ReleaseDate date = movie.getReleaseDate();
		Rating rating = movie.getRating();
		CharSequence overview = movie.getOverview();
		List<Video> videos = movie.getVideos();
		List<Review> reviews = movie.getReviews();

		indexer = App.getMovieIndexer(); // If devices rotates, and this was performed already, then grab the saved copy

		if (indexer == null || indexer.getID() != movie.getID()) {

			indexer = new IndexerBuilder()
					.addView(PrimaryView.RELEASE, ListGroup.BASIC)
						.If(date != null && date.hasReleaseDate()).queue()

					.addView(PrimaryView.RATING, ListGroup.BASIC)
						.ifNot(rating == null).queue()

					.addView(PrimaryView.OVERVIEW, ListGroup.BASIC)
						.ifNot(overview.equals("")).queue()

					.addView(PrimaryView.VIDEO, ListGroup.VIDEO)
						.contitionalRepeat(videos.size()).noLessThan(1)
							.queue()

					.addView(PrimaryView.REVIEW, ListGroup.REVIEW)
						.contitionalRepeat(reviews.size()).noLessThan(1)
							.queue()

					.indexFor(movie.getID());

			App.setMovieIndexer(indexer);

		}
	}

	@Override
	public BaseViewHolder onCreateViewHolder(final ViewGroup parent, final int position) {

		final LayoutInflater inflater = LayoutInflater.from(context);
		final View view = inflater.inflate(R.layout.movie_detail_list_item, parent, false);

		return indexer.at(position).getViewHolder(view);

	}

	@Override
	public void onBindViewHolder(final BaseViewHolder holder, final int position) {

		holder.dynamicallyBindData(movie); // Don't need position variable. The holder knows the position already

	}

	@Override
	public int getItemViewType(int position) { return position; } // Pass through. I have other ways to determine viewType based on position. not int-based

	@Override
	public int getItemCount() {

		if (indexer == null) { return 0; }
		return indexer.getIndexSize();

	}

}
