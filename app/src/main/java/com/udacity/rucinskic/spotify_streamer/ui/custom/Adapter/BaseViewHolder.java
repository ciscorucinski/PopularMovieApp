package com.udacity.rucinskic.spotify_streamer.ui.custom.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.udacity.rucinskic.spotify_streamer.R;
import com.udacity.rucinskic.spotify_streamer.ui.custom.InfoListItemView;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    public final InfoListItemView root;
    public final boolean isHeader;

    //    public final ImageView imgMainIcon;
    //    public final ImageView imgActionIcon;
    //    public final TextView txtMainInformation;
    //    public final TextView txtExtraInformation;
    //    public final TextView txtDivider;
    //    public final ViewGroup root;
    public int position;

    public BaseViewHolder(final View itemView, boolean isHeader) {

        super(itemView);

        position = 0;

        root = (InfoListItemView) itemView.findViewById(R.id.movie_detail_base_list_item);
        this.isHeader = isHeader;

        //        imgMainIcon = (ImageView) itemView.findViewById(R.id.movie_detail_main_icon);
        //        imgActionIcon = (ImageView) itemView.findViewById(R.id.movie_detail_action_icon);
        //        txtMainInformation = (TextView) itemView.findViewById(R.id.movie_detail_secondary_text);
        //        txtDivider = (TextView) itemView.findViewById(R.id.movie_detail_secondary_divider);
        //        txtExtraInformation = (TextView) itemView.findViewById(R.id.movie_detail_secondary_extra);
        //        root = (ViewGroup) itemView.findViewById(R.id.movie_detail_list_item);

    }

}