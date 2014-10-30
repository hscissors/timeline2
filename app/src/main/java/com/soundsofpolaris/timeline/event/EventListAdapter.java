package com.soundsofpolaris.timeline.event;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.gui.StickyRecyclerHeadersAdapter;
import com.soundsofpolaris.timeline.models.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

public class EventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyRecyclerHeadersAdapter {

    private ArrayList<Event> mEvents;
    public EventListAdapter(ArrayList<Event> events) {
        mEvents = events;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        public boolean isSelected;

        public RelativeLayout mEventContainer;
        public TextView mMonth;
        public TextView mDay;
        public RelativeLayout mDateContainer;
        public TextView mTitle;
        public TextView mDesc;

        public EventViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.event_title);
            mDesc = (TextView) view.findViewById(R.id.event_description);

            mDateContainer = (RelativeLayout) view.findViewById(R.id.event_date_container);
            mMonth = (TextView) view.findViewById(R.id.event_month);
            mDay = (TextView) view.findViewById(R.id.event_day);

            mEventContainer = (RelativeLayout) view.findViewById(R.id.event_container);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int typeView) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_list_item, viewGroup, false);;
        ImageButton listItemMenuButton = (ImageButton) view.findViewById(R.id.event_list_item_menu_button);

        final RecyclerView.ViewHolder vh = new EventViewHolder(view);

        listItemMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(v.getContext(), v);
                menu.inflate(R.menu.event_list_menu);
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.action_share:
                                return true;
                            case R.id.action_edit:
//                                vh.mLayout.setVisibility(View.GONE);
//                                vh.mEditLayout.setVisibility(View.VISIBLE);
                                return true;
                        }
                        return false;
                    }
                });
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int pos) {
        Event event = mEvents.get(pos);
        ((EventViewHolder) viewHolder).mDateContainer.setBackgroundColor(event.getGroupColor());

        ((EventViewHolder) viewHolder).mDay.setText(event.getDay());
        ((EventViewHolder) viewHolder).mMonth.setText(event.getMonth());
        ((EventViewHolder) viewHolder).mTitle.setText(event.getTitle());
        ((EventViewHolder) viewHolder).mDesc.setText(event.getDescription());


        ((EventViewHolder) viewHolder).mEventContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout container = (RelativeLayout) v;
                if(((EventViewHolder) viewHolder).isSelected){
                    ((EventViewHolder) viewHolder).mDesc.setVisibility(View.GONE);
                } else {
                    ((EventViewHolder) viewHolder).mDesc.setVisibility(View.VISIBLE);
                }

                ((EventViewHolder) viewHolder).isSelected = !((EventViewHolder) viewHolder).isSelected;
            }
        });
    }

    @Override
    public long getHeaderId(int position) {
        return mEvents.get(position).getYear();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_section, parent, false);
        return new RecyclerView.ViewHolder(view) {};
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        TextView header = (TextView) viewHolder.itemView;
        header.setText(String.valueOf(mEvents.get(position).getYear()));
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }
}
