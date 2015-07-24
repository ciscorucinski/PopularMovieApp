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

public class InfoListItemView extends FrameLayout {

    private static final int SECONDARY_LAYOUT_STRING_DEFAULT = 0;
    private static final int SECONDARY_LAYOUT_STRING_WITH_EXTRA = 1;

    public enum PrimaryViewType {

        LABEL(1), IMAGE(2), VIDEO(3), RATING(4), PROGRESS(5), SPACE(6), STUB(7);

        private int ID;

        PrimaryViewType(int ID) { this.ID = ID;}

        public int getValue() { return ID; }

    }

    private static final int INVALID_RESOURCE = 0;

    private boolean hasStringExtra;

    private boolean hasMainIcon;
    private boolean hasSecondaryIcon;

    private int primaryViewType;

    private InfoListItemView root;

    private ViewGroup viewListItem;
    private ImageView imgMainIcon;
    private ImageView imgSecondaryIcon;
    private ViewStub viewPrimaryStub;
    private View viewPrimary;
    private TextView txtSecondary;
    private TextView txtSecondaryDivider;
    private TextView txtSecondaryExtra;

    public InfoListItemView(Context context) { this(context, null); }

    public InfoListItemView(Context context, AttributeSet attrs) {

        super(context, attrs);

        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.InfoListItemView, 0, 0);

        int mainIcon = a.getResourceId(R.styleable.InfoListItemView_mainIcon, INVALID_RESOURCE);
        int actionIcon = a.getResourceId(R.styleable.InfoListItemView_actionIcon, INVALID_RESOURCE);
        int primaryLayout =
                a.getResourceId(R.styleable.InfoListItemView_primaryLayout, R.layout.space);
        String secondaryText = a.getString(R.styleable.InfoListItemView_secondaryText);
        String extraText = a.getString(R.styleable.InfoListItemView_secondaryExtraText);

        primaryViewType = a.getInt(R.styleable.InfoListItemView_primaryViewType,
                                   PrimaryViewType.SPACE.ID);
        hasStringExtra = a.getBoolean(R.styleable.InfoListItemView_hasStringExtra, false);

        a.recycle();

        root = (InfoListItemView) LayoutInflater.from(context)
                .inflate(R.layout.movie_detail_list_item_base, this, true);

        viewListItem = (ViewGroup) root.findViewById(R.id.movie_detail_list_item);

        imgMainIcon = (ImageView) root.findViewById(R.id.movie_detail_main_icon);
        imgSecondaryIcon = (ImageView) root.findViewById(R.id.movie_detail_action_icon);
        viewPrimaryStub = (ViewStub) root.findViewById(R.id.movie_detail_primary_view_stub);
        txtSecondary = (TextView) root.findViewById(R.id.movie_detail);
        txtSecondaryDivider = (TextView) root.findViewById(R.id.movie_detail_secondary_divider);
        txtSecondaryExtra = (TextView) root.findViewById(R.id.movie_detail_secondary_extra);

        hasMainIcon = (mainIcon != INVALID_RESOURCE);
        if (hasMainIcon) imgMainIcon.setImageResource(mainIcon);

        hasSecondaryIcon = (actionIcon != INVALID_RESOURCE);
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


        // default view is just an empty space, so no need for a guard clause
        if (primaryViewType != PrimaryViewType.STUB.ID) { // TODO this needs to be more detailed

            viewPrimaryStub.setLayoutResource(primaryLayout);
            viewPrimary =
                    viewPrimaryStub.inflate(); // No longer have access to the Stub after inflate

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
    } // TODO hide and show needed views

    public void setPrimaryView(@LayoutRes int layout) { // TODO check this

        viewPrimaryStub.setLayoutResource(layout);
        viewPrimary = viewPrimaryStub.inflate(); // No longer have access to the Stub after inflate

    }

    public void setMainActionOnClickListener(OnClickListener listener) {

        this.viewListItem.setOnClickListener(listener);

    }

    public void setSecondaryActionOnClickListener(OnClickListener listener) {

        this.imgSecondaryIcon.setOnClickListener(listener);

    }

    public void setMainActionOnLongClickListener(OnLongClickListener listener) {

        this.viewListItem.setOnLongClickListener(listener);

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

}
