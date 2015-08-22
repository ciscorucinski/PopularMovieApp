package com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.interfaces.DataBinder;
import com.udacity.rucinskic.spotify_streamer.interfaces.StubReplacer;
import com.udacity.rucinskic.spotify_streamer.ui.custom.InfoListItemView;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.support.Indexer;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder
		implements DataBinder<InfoListItemView, Movie>, StubReplacer<FrameLayout> {

	private final Context context;
	private final InfoListItemView root;
	private final Indexer.Indexed index;
	private FrameLayout primaryViewLayout;

	BaseViewHolder(final View itemView, Indexer.Indexed indexer) {

		super(itemView);

		this.root = (InfoListItemView) itemView.findViewById(R.id.movie_detail_base_list_item);
		this.context = itemView.getContext();
		this.index = indexer;

	}

	Indexer.Indexed getIndex() { return this.index; }

	Context getContext() { return this.context; }

	public void dynamicallyBindData(Movie movie) {

		sharedDataBinding(root);

		if (index.getIsViewAvailable()) {

			FrameLayout layout = convertStubIntoViewGroup();
			View customView = createNewInitializedView(context, layout);
			onLoadedDataBind(root, customView, movie);

		} else {

			onMissingDataBind(root);

		}

	}

	@Override
	public FrameLayout convertStubIntoViewGroup() {

		ViewStub stub = root.viewPrimaryStub;

		if (primaryViewLayout == null) {

			primaryViewLayout = (FrameLayout) stub.inflate();

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);

			primaryViewLayout.setLayoutParams(params);

		}

		return primaryViewLayout;

	}

	@Override
	public void onMissingDataBind(InfoListItemView parent) {

		parent.setSecondaryExtraText("Not Available");

	}

	@Override
	public void sharedDataBinding(InfoListItemView parent) {


		if (index.isHeaderView())
			root.setMainIcon(index.getGroupIconResource());

		parent.setSecondaryText(index.getDescription());
		parent.setHasStringExtra(
				true); // Right now, most list items have "extras". Extended classes must "opt-out" of showing extras

	}

}