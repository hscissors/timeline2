package com.soundsofpolaris.timeline.timeline;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.event.EventActivity;
import com.soundsofpolaris.timeline.event.EventListFragment;
import com.soundsofpolaris.timeline.models.Timeline;
import com.soundsofpolaris.timeline.tools.FileHelper;

import java.util.ArrayList;

public class TimelineListAdapter extends RecyclerView.Adapter<TimelineListAdapter.ViewHolder> {

    private final Bitmap mImage;
    private int mColor;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout mEditLayout;
        private final RelativeLayout mLayout;
        private final ImageView mThumbnail;
        private final View mColor;
        private final TextView mTitle;
        private final TextView mDesc;

        public ViewHolder(CardView v) {
            super(v);
            mEditLayout = (LinearLayout) v.findViewById(R.id.card_edit_layout);
            mLayout = (RelativeLayout) v.findViewById(R.id.card_layout);
            mThumbnail = (ImageView) v.findViewById(R.id.timeline_thumbnail);
            mColor = v.findViewById(R.id.timeline_color);
            mTitle = (TextView) v.findViewById(R.id.timeline_title);
            mDesc = (TextView) v.findViewById(R.id.timeline_desc);
        }
    }

    ArrayList<Timeline> mTimelines;

    public TimelineListAdapter(ArrayList<Timeline> timelines) {
        mTimelines = timelines;
        mImage = FileHelper.loadImage("horsehead_caelum.jpg");
        if (mImage != null) {
            mColor = Palette.generate(mImage).getMutedSwatch().getRgb();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        CardView card = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.timeline_list_item, viewGroup, false);
        final ViewHolder vh = new ViewHolder(card);

        ImageButton listItemMenuButton = (ImageButton) card.findViewById(R.id.timeline_list_item_menu_button);

        listItemMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                vh.mLayout.setVisibility(View.GONE);
                                vh.mEditLayout.setVisibility(View.VISIBLE);
                        }
                        return false;
                    }
                });
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int pos) {

        if (mImage != null) {
            viewHolder.mThumbnail.setImageBitmap(mImage);
            viewHolder.mColor.setBackgroundColor(mColor);
        }
        viewHolder.mTitle.setText(mTimelines.get(pos).getName());
        viewHolder.mDesc.setText("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");

        viewHolder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(v.getContext(), EventActivity.class);
                ((Activity) v.getContext()).startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTimelines.size();
    }
}
