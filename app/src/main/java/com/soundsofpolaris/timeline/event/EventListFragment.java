package com.soundsofpolaris.timeline.event;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.gui.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;

public class EventListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SELECTED_TIMELINE_ID = "selected_timeline";

    private int mTimelineID;

    public static EventListFragment newInstance(int timelineID) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putInt(SELECTED_TIMELINE_ID, timelineID);

        fragment.setArguments(args);
        return fragment;
    }

    public EventListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTimelineID = getArguments().getInt(SELECTED_TIMELINE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout rootView = (FrameLayout) inflater.inflate(R.layout.event_list_fragment, container, false);

        ArrayList<Event> events = new ArrayList();
        events.add(new Event(0, 1900, 1, 1000l, "Battle of Antietam", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1901, 1, 1000l, "Abraham Lincoln's Birthday", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1902, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1903, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1903, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1903, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1903, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1900, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1900, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1900, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1900, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1900, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1900, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1900, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1900, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1900, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1900, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1900, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1900, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1900, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1900, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1910, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1910, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1910, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1910, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1910, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1910, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1910, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));
        events.add(new Event(0, 1910, 1, 1000l, "Event", "Event Description", 0, 0, 0, 0xff27ae60, "test"));


        RecyclerView mEventList = (RecyclerView) rootView.findViewById(R.id.event_list);
//        mEventList.setHasFixedSize(true);
        EventListAdapter eventListAdapter = new EventListAdapter(events);
        mEventList.setAdapter(eventListAdapter);
        mEventList.addItemDecoration(new StickyRecyclerHeadersDecoration(eventListAdapter));
        mEventList.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }
}
