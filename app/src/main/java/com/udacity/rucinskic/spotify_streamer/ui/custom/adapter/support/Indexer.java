package com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.support;

import android.support.annotation.DrawableRes;
import android.view.View;

import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder.BaseViewHolder;

// With this class, there are always two ways of getting something. Calling a method with an indexFor parameter in Indexer, or by providing Indexed with an indexFor and just calling the needed parameterless method
public class Indexer {

	private static IndexerBuilder.Flattener indexer;
	private final int id;

	Indexer(IndexerBuilder.Flattener indexer, int id) {

		//		InfoListItemView.setIndexer(this);
		Indexer.indexer = indexer;
		this.id = id;

	}

	public Indexed at(int position) { return new Indexed(position); }

	public int getIndexSize() { return indexer.indexSize; }

	public void clear() {indexer = null; }

	public int getID() { return this.id; }

	public class Indexed {

		private final static int OR_INVALID = -1;
		private final int index;

		public Indexed(int index) { this.index = index; }

		public BaseViewHolder getViewHolder(View view) {

			return indexer.listViewType.get(index).getViewHolder(view, this);

		}

		public
		@DrawableRes
		int getGroupIconResource() { return indexer.listGroup.get(index).getIconResource(); }

		public
		@DrawableRes
		int getActionIconResource() { return indexer.listViewType.get(index).getIconResource(); }

		public boolean hasActionIcon() { return indexer.listViewType.get(index).hasIcon(); }

		public int getVideoIndex() { return indexer.listVideoIndex.get(index, OR_INVALID); }

		public int getReviewIndex() { return indexer.listReviewIndex.get(index, OR_INVALID); }

		public boolean isHeaderView() { return indexer.listIsHeader.get(index); }

		public String getDescription() { return indexer.listViewType.get(index).getDescription(); }

		public boolean getIsViewAvailable() { return indexer.listIsAvailable.get(index); }

	}


}