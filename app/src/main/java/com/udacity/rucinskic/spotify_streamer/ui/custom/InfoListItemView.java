package com.udacity.rucinskic.spotify_streamer.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.ui.custom.adapter.support.Indexer;

@SuppressWarnings("UnusedDeclaration")
public class InfoListItemView extends FrameLayout {

	private static final int SECONDARY_LAYOUT_STRING_DEFAULT = 0;
	private static final int SECONDARY_LAYOUT_STRING_WITH_EXTRA = 1;

	private static final int INVALID_RESOURCE = -1;
	private static Indexer indexer;
	public final ViewGroup root;
	public final ImageView imgMainIcon;
	public final ImageView imgSecondaryIcon;
	public final ViewStub viewPrimaryStub;
	public final TextView txtSecondary;
	public final TextView txtSecondaryDivider;
	public final TextView txtSecondaryExtra;
	public View viewPrimary;
	private boolean hasStringExtra;

	public InfoListItemView(Context context) { this(context, null); }

	public InfoListItemView(Context context, AttributeSet attrs) {

		super(context, attrs);

		TypedArray a = context.getTheme()
				.obtainStyledAttributes(attrs, R.styleable.InfoListItemView, 0, 0);

		int mainIcon = a.getResourceId(R.styleable.InfoListItemView_mainIcon, INVALID_RESOURCE);
		int actionIcon = a.getResourceId(R.styleable.InfoListItemView_actionIcon, INVALID_RESOURCE);

		String secondaryText = a.getString(R.styleable.InfoListItemView_secondaryText);
		String extraText = a.getString(R.styleable.InfoListItemView_secondaryExtraText);

		hasStringExtra = a.getBoolean(R.styleable.InfoListItemView_hasStringExtra, false);

		a.recycle();

		root = (ViewGroup) LayoutInflater.from(context)
				.inflate(R.layout.movie_detail_list_item_stub, this, true);

		imgMainIcon = (ImageView) root.findViewById(R.id.movie_detail_main_icon);
		imgSecondaryIcon = (ImageView) root.findViewById(R.id.movie_detail_action_icon);
		viewPrimaryStub = (ViewStub) root.findViewById(R.id.movie_detail_primary_view_stub);
		txtSecondary = (TextView) root.findViewById(R.id.movie_detail_secondary_text);
		txtSecondaryDivider = (TextView) root.findViewById(R.id.movie_detail_secondary_divider);
		txtSecondaryExtra = (TextView) root.findViewById(R.id.movie_detail_secondary_extra);

		boolean hasMainIcon = (mainIcon != INVALID_RESOURCE);
		if (hasMainIcon) imgMainIcon.setImageResource(mainIcon);

		boolean hasSecondaryIcon = (actionIcon != INVALID_RESOURCE);
		if (hasSecondaryIcon) imgSecondaryIcon.setImageResource(actionIcon);

		if (secondaryText != null) txtSecondary.setText(secondaryText);

		if (hasStringExtra) {

			txtSecondaryDivider.setVisibility(VISIBLE);
			txtSecondaryExtra.setVisibility(VISIBLE);
			txtSecondaryExtra.setText(extraText);

		} else {

			txtSecondaryDivider.setVisibility(GONE);
			txtSecondaryExtra.setVisibility(GONE);
			txtSecondaryExtra.setText("");

		}

	}

	public void setMainIcon(@DrawableRes int icon) { this.imgMainIcon.setImageResource(icon); }

	public void setActionIcon(@DrawableRes int icon) {
		this.imgSecondaryIcon.setImageResource(icon);
	}

	public void setSecondaryText(String text) { this.txtSecondary.setText(text); }

	public void setSecondaryText(@StringRes int text) { this.txtSecondary.setText(text); }

	public void setSecondaryExtraText(String text) { this.txtSecondaryExtra.setText(text); }

	public void setSecondaryExtraText(@StringRes int text) { this.txtSecondaryExtra.setText(text); }

	public void setHasStringExtra(boolean hasStringExtra) {

		this.hasStringExtra = hasStringExtra;

		if (hasStringExtra) {

			txtSecondaryDivider.setVisibility(VISIBLE);
			txtSecondaryExtra.setVisibility(VISIBLE);

		} else {

			txtSecondaryDivider.setVisibility(GONE);
			txtSecondaryExtra.setVisibility(GONE);
			txtSecondaryExtra.setText("");

		}

	}

	public void setMainActionOnClickListener(OnClickListener listener) {

		this.root.setOnClickListener(listener);

	}

	public void setSecondaryActionOnClickListener(OnClickListener listener) {

		this.imgSecondaryIcon.setOnClickListener(listener);

	}

	public void setMainActionOnLongClickListener(OnLongClickListener listener) {

		this.root.setOnLongClickListener(listener);

	}

	public void setSecondaryActionOnLongClickListener(OnLongClickListener listener) {

		this.imgSecondaryIcon.setOnLongClickListener(listener);

	}

	public void setPrimaryViewOnClickListener(OnClickListener listener) {

		this.viewPrimary.setOnClickListener(listener);

	}

	public void setPrimaryViewOnLongClickListener(OnLongClickListener listener) {

		this.viewPrimary.setOnLongClickListener(listener);

	}

	public ImageView getMainIconView() { return this.imgMainIcon; }

	public ImageView getActionIconView() { return this.imgSecondaryIcon; }

	public TextView getSecondaryTextView() { return this.txtSecondary; }

	public TextView getExtraTextView() { return this.txtSecondaryExtra; }

	public View getPrimaryView() { return this.viewPrimary; }

	public void setPrimaryView(@LayoutRes int layout) {

		viewPrimaryStub.setLayoutResource(layout);
		viewPrimary = viewPrimaryStub.inflate(); // No longer have access to the Stub after inflate

	}

	//	public Indexer getIndexer() { return indexer; }
	//	public static void setIndexer(Indexer indexer) { InfoListItemView.indexer = indexer; }

}
