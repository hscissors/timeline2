package com.soundsofpolaris.timeline.event;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;


import com.soundsofpolaris.timeline.timeline.Timeline;

import java.util.ArrayList;

/**
 * Created by harold on 11/3/14.
 */
public class LinkedTimelineListAdapter extends BaseAdapter implements ListAdapter{

    private ArrayList<Timeline> mTimelines;

    public LinkedTimelineListAdapter(int selectedTimeline){
        //mTimelines = timelines;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mTimelines.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return mTimelines.isEmpty();
    }
}
