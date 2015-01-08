package com.soundsofpolaris.timeline.timeline;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.base.BaseActivity;
import com.soundsofpolaris.timeline.event.EventActivity;
import com.soundsofpolaris.timeline.tasks.LoadImageTask;
import com.soundsofpolaris.timeline.tools.FileHelper;
import com.soundsofpolaris.timeline.tools.Utils;

import java.util.List;

public class TimelineListAdapter extends RecyclerView.Adapter<TimelineListItemViewHolder> {

    private List<Timeline> mTimelines;
    private OnItemClickListener mOnItemClickListener;

    public static interface OnItemClickListener{
        public void onItemClick(int pos);
        public boolean onItemMenuClick(MenuItem item, int pos);
    }

    public TimelineListAdapter(List<Timeline> timelines) {
        mTimelines = timelines;
    }

    @Override
    public TimelineListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.timeline_list_item, viewGroup, false);
        final TimelineListItemViewHolder vh = new TimelineListItemViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final TimelineListItemViewHolder viewHolder, final int pos) {
        Timeline timeline = mTimelines.get(pos);

        if(!Utils.isEmpty(timeline.getImageFileName())) {
            LoadImageTask loadImageTask = new LoadImageTask(timeline.getImageFileName());
            loadImageTask.setListener(new LoadImageTask.Listener() {
                @Override
                public void onTaskComplete(Bitmap image) {
                    viewHolder.mThumbnail.setImageBitmap(image);
                }
            });
            loadImageTask.execute();
        } else {
            viewHolder.mThumbnail.setVisibility(View.GONE);
        }

        viewHolder.mColor.setBackgroundColor(timeline.getColor());

        viewHolder.mTitle.setText(timeline.getTitle());

        if(!Utils.isEmpty(timeline.getDescription())){
            viewHolder.mDesc.setText(timeline.getDescription());
        } else {
            viewHolder.mDesc.setVisibility(View.GONE);
        }

        viewHolder.mTotalEvents.setText(String.valueOf(timeline.getTotalEvents()));

        viewHolder.mCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mOnItemClickListener.onItemClick(pos);
            }
        });

        viewHolder.mItemMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu menu = new PopupMenu(v.getContext(), v);
                menu.inflate(R.menu.timeline_list_item_menu);
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return mOnItemClickListener.onItemMenuClick(item, pos);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTimelines.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
}
