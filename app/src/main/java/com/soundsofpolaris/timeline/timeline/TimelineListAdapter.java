package com.soundsofpolaris.timeline.timeline;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.event.EventActivity;
import com.soundsofpolaris.timeline.tools.FileHelper;

import java.util.ArrayList;

public class TimelineListAdapter extends RecyclerView.Adapter<TimelineListItemViewHolder> {

    private final Bitmap mImage;
    private int mColor;

    ArrayList<Timeline> mTimelines;

    public TimelineListAdapter(ArrayList<Timeline> timelines) {
        mTimelines = timelines;
        mImage = FileHelper.loadImage("horsehead_caelum.jpg");
        if (mImage != null) {
            mColor = Palette.generate(mImage).getMutedSwatch().getRgb();
        }
    }

    @Override
    public TimelineListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.timeline_list_item, viewGroup, false);
        final TimelineListItemViewHolder vh = new TimelineListItemViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final TimelineListItemViewHolder viewHolder, int pos) {

        if (mImage != null) {
            viewHolder.mThumbnail.setImageBitmap(mImage);
            viewHolder.mColor.setBackgroundColor(mColor);
        }
        viewHolder.mTitle.setText(mTimelines.get(pos).getName());
        viewHolder.mDesc.setText("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");

        viewHolder.mCardLayout.setOnClickListener(new View.OnClickListener() {
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
