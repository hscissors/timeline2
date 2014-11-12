package com.soundsofpolaris.timeline.timeline;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.base.BaseActivity;

public class TimelineListItemViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = TimelineListItemViewHolder.class.toString();

    public Timeline mTimeline;

    public final CardView mCardLayout;
    public final RelativeLayout mItemLayout;
    public final ImageView mThumbnail;
    public final FrameLayout mColor;
    public final TextView mTitle;
    public final TextView mDesc;
    public final ImageButton mItemMenuButton;

    public TimelineListItemViewHolder(View v) {
        super(v);
        mCardLayout = (CardView) v.findViewById(R.id.card_layout);
        mItemLayout = (RelativeLayout) v.findViewById(R.id.item_layout);
        mThumbnail = (ImageView) v.findViewById(R.id.timeline_thumbnail);
        mColor = (FrameLayout) v.findViewById(R.id.timeline_color);
        mTitle = (TextView) v.findViewById(R.id.timeline_title);
        mDesc = (TextView) v.findViewById(R.id.timeline_desc);
        mItemMenuButton = (ImageButton) v.findViewById(R.id.timeline_list_item_menu_button);
    }
}
