package com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.holder;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.udacity.rucinskic.spotify_streamer.ui.custom.InfoListItemView;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.support.Indexer;
import com.udacity.rucinskic.spotify_streamer.ui.support.model.Movie;

public class OverviewViewHolder extends BaseViewHolder {


	public OverviewViewHolder(View itemView, Indexer.Indexed index) {

		super(itemView, index);
	}

	@Override
	public void onLoadedDataBind(final InfoListItemView parent, View customView, final Movie data) {

		final TextView textView = (TextView) customView;
		//        DocumentView documentView = (DocumentView) customView;
		//
		//        documentView.setText(data.getOverview());


		textView.setText(data.getOverview());

		final CharSequence text = textView.getText();

		parent.setActionIcon(getIndex().getActionIconResource());
		parent.setMainActionOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				new AlertDialog.Builder(getContext()).setTitle("Overview").setMessage(text)
						.setNeutralButton("OK", null)
						.create().show();

			}
		});

		//		}
		parent.setHasStringExtra(false);

	}

	@Override
	public View createNewInitializedView(Context context, FrameLayout layout) {

		TextView textView = new TextView(context, null, android.R.attr.textAppearanceSmall);
		textView.setTextColor(Color.BLACK);
		textView.setMaxLines(5);
		textView.setEllipsize(TextUtils.TruncateAt.END);
		layout.addView(textView);

		return textView;

		//        DocumentView documentView = new DocumentView(context, DocumentView.PLAIN_TEXT);
		//        layout.addView(documentView);
		//
		//        IDocumentLayout.LayoutParams params = documentView.getDocumentLayoutParams();
		//        params.setTextTypeface(Typeface.DEFAULT);
		//        params.setAntialias(true);
		//        params.setTextSubPixel(true);
		//        params.setTextAlignment(TextAlignment.JUSTIFIED); // Using this library for justification
		//        params.setMaxLines(2);
		//
		//        documentView.setCacheConfig(DocumentView.CacheConfig.GRAYSCALE); // This reduces memory consumption a lot. Default caching behavior is expansive in this library
		//
		//        return documentView;

	}

}