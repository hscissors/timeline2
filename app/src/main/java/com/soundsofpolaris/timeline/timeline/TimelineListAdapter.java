package com.soundsofpolaris.timeline.timeline;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.models.Timeline;

import java.util.ArrayList;

public class TimelineListAdapter extends RecyclerView.Adapter<TimelineListAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mThumbnail;
        View mColor;
        TextView mTitle;
        TextView mDesc;

        public ViewHolder(CardView v){
            super(v);

            mColor = v.findViewById(R.id.timeline_color);
            mTitle = (TextView) v.findViewById(R.id.timeline_title);
            mDesc = (TextView) v.findViewById(R.id.timeline_desc);
        }
    }

    ArrayList<Timeline> mTimelines;

    public TimelineListAdapter(ArrayList<Timeline> timelines){
        mTimelines = timelines;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        CardView card = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.timeline_list_item, viewGroup, false);

        Button listItemMenuButton = (Button) card.findViewById(R.id.timeline_list_item_menu_button);

        listItemMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(v.getContext(), v);
                menu.inflate(R.menu.timeline_list_item_menu);
                menu.show();
            }
        });

        ViewHolder vh = new ViewHolder(card);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        viewHolder.mTitle.setText(mTimelines.get(pos).getName());
    }

    @Override
    public int getItemCount() {
        return mTimelines.size();
    }
}
