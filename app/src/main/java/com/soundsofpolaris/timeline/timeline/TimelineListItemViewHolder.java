package com.soundsofpolaris.timeline.timeline;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.models.Timeline;

public class TimelineListItemViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = TimelineListItemViewHolder.class.toString();

    public Timeline mTimeline;

    public final CardView mCardLayout;
    public final RelativeLayout mItemLayout;
    public final ImageView mThumbnail;
    public final View mColor;
    public final TextView mTitle;
    public final TextView mDesc;

    public TimelineListItemViewHolder(View v) {
        super(v);
        mCardLayout = (CardView) v.findViewById(R.id.card_layout);
        mItemLayout = (RelativeLayout) v.findViewById(R.id.item_layout);
        mThumbnail = (ImageView) v.findViewById(R.id.timeline_thumbnail);
        mColor = v.findViewById(R.id.timeline_color);
        mTitle = (TextView) v.findViewById(R.id.timeline_title);
        mDesc = (TextView) v.findViewById(R.id.timeline_desc);

        ImageButton listItemMenuButton = (ImageButton) v.findViewById(R.id.timeline_list_item_menu_button);
        listItemMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu menu = new PopupMenu(v.getContext(), v);
                menu.inflate(R.menu.timeline_list_item_menu);
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.action_share:
                                return true;
                            case R.id.action_edit:
                                mItemLayout.setVisibility(View.GONE);
                                mCardLayout.addView(new TimelineEditView(v.getContext()));
                        }
                        return false;
                    }
                });
            }
        });


    }
}
