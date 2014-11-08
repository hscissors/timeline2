package com.soundsofpolaris.timeline.timeline;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.event.EventActivity;
import com.soundsofpolaris.timeline.tools.FileHelper;
import com.soundsofpolaris.timeline.tools.Utils;

import java.util.List;

public class TimelineListAdapter extends RecyclerView.Adapter<TimelineListItemViewHolder> {

    List<Timeline> mTimelines;

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
            viewHolder.mThumbnail.setImageBitmap(FileHelper.loadImage(timeline.getImageFileName()));
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

        viewHolder.mCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Intent i = new Intent(v.getContext(), EventActivity.class);
                i.putExtra(TimelineActivity.SELECTED_TIMELINE, mTimelines.get(pos));
                ((Activity) v.getContext()).startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTimelines.size();
    }
}
