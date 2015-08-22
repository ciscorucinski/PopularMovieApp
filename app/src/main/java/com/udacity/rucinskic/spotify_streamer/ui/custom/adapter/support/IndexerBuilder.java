package com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.support;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import com.udacity.rucinskic.spotify_streamer.enums.ListGroup;
import com.udacity.rucinskic.spotify_streamer.enums.PrimaryView;

public class IndexerBuilder {

	private ViewBuilder viewBuilder;
	private Indexer indexer;

	public ViewBuilder addView(PrimaryView view, ListGroup group) {

		if (viewBuilder == null) { viewBuilder = new ViewBuilder(view, group); }
		return viewBuilder;

	}

	public Indexer indexFor(int id) {

		if (indexer == null) { indexer = new Flattener(id).index(); }
		return indexer;

	}

	public class ViewBuilder {

		final PrimaryView view;
		final ListGroup group;
		boolean isNotMissing;
		int occurrences;

		public ViewBuilder(PrimaryView view, ListGroup group) {

			this.view = view;
			this.group = group;
			this.occurrences = 1;
			this.isNotMissing = true; // Assume it should get added unless stated otherwise

		}

		public RepeatBuilder contitionalRepeat(int occurrences) {

			return new RepeatBuilder(this, occurrences);

		}

		public ViewBuilder repeat(int occurrences) {

			this.occurrences = occurrences;
			return this;
		}

		public ViewBuilder If(boolean shouldAddView) {

			this.isNotMissing = this.isNotMissing && shouldAddView;
			return this;

		}

		public ViewBuilder ifNot(boolean shouldNotAddView) {

			this.isNotMissing = this.isNotMissing && !shouldNotAddView;
			return this;

		}

		public IndexerBuilder queue() {

			for (int i = 0; i < occurrences; i++) {

				ListGroup.addViewToGroup(group, view, isNotMissing);

			}

			return new IndexerBuilder();

		}

		public class RepeatBuilder {

			private final ViewBuilder viewBuilder;
			private int occurrences;

			RepeatBuilder(ViewBuilder viewBuilder, int occurrences) {

				this.occurrences = occurrences;
				this.viewBuilder = viewBuilder;
			}

			public ViewBuilder noLessThan(int minOccurrences) {

				if (this.occurrences <= 0) this.viewBuilder.isNotMissing = false;

				this.occurrences = Math.max(this.occurrences, minOccurrences);
				this.viewBuilder.occurrences = this.occurrences;
				return this.viewBuilder;

			}

			public ViewBuilder noMoreThan(int maxOccurrences) {

				this.occurrences = Math.min(this.occurrences, maxOccurrences);
				this.viewBuilder.occurrences = this.occurrences;
				return this.viewBuilder;

			}

			public ViewBuilder displayAll() { return this.viewBuilder; }

		}
	}

	public class Flattener {

		final SparseArray<ListGroup> listGroup = new SparseArray<>();
		final SparseArray<PrimaryView> listViewType = new SparseArray<>();
		final SparseBooleanArray listIsHeader = new SparseBooleanArray();
		final SparseBooleanArray listIsAvailable = new SparseBooleanArray();
		final SparseIntArray listVideoIndex = new SparseIntArray();
		final SparseIntArray listReviewIndex = new SparseIntArray();
		final int id;
		private final SparseIntArray listGroupIndex = new SparseIntArray();
		int indexSize;

		Flattener(int id) { this.id = id; }

		Indexer index() {

			// Increments, non-resetting
			int flatIndex = 0;

			// Increments, resets at 0
			int groupedIndexNumber;

			for (ListGroup group : ListGroup.values()) {

				groupedIndexNumber = 0;

				for (int index = 0; index < group.getSize(); index++) {

					listIsHeader.append(flatIndex, group.getHeaderList().get(index));
					listGroupIndex.append(flatIndex, groupedIndexNumber);

					listGroup.append(flatIndex, group);
					listViewType.append(flatIndex, group.getViewTypeList().get(index));

					listIsAvailable.append(flatIndex, group.getIsAvailableList().get(index));

					// Populate these lists for convenience within this class
					if (group == ListGroup.REVIEW) listReviewIndex.append(flatIndex, index);
					if (group == ListGroup.VIDEO) listVideoIndex.append(flatIndex, index);

					groupedIndexNumber++;
					flatIndex++;

				}

				group.clearData();

			}

			this.indexSize = flatIndex;

			return new Indexer(this, id);

		}
	}
}

