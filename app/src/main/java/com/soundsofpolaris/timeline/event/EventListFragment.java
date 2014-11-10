package com.soundsofpolaris.timeline.event;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.shamanland.fab.FloatingActionButton;
import com.shamanland.fab.ShowHideOnScroll;
import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.TimelineApplication;
import com.soundsofpolaris.timeline.base.BaseActivity;
import com.soundsofpolaris.timeline.gui.StickyRecyclerHeadersDecoration;
import com.soundsofpolaris.timeline.timeline.Timeline;
import com.soundsofpolaris.timeline.timeline.TimelineEditFragment;
import com.soundsofpolaris.timeline.tools.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class EventListFragment extends Fragment {
    private static final String SELECTED_TIMELINE = "selected_timeline";

    private Timeline mSelectedTimeline;

    public static EventListFragment newInstance(Timeline selectedTimeline) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_TIMELINE, selectedTimeline);

        fragment.setArguments(args);
        return fragment;
    }

    public EventListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelectedTimeline = (Timeline) getArguments().getParcelable(SELECTED_TIMELINE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.event_list_fragment, container, false);

        //TODO task?
        List<Event> events = TimelineApplication.getInstance().getDatabaseHelper().getAllEventsByTimeline(mSelectedTimeline.getId());

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) v.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, EventEditFragment.newInstance(mSelectedTimeline), EventEditFragment.class.toString())
                        .addToBackStack(EventEditFragment.class.toString())
                        .commit();
            }
        });

        RecyclerView mEventList = (RecyclerView) rootView.findViewById(R.id.event_list);
//        mEventList.setHasFixedSize(true);
        EventListAdapter eventListAdapter = new EventListAdapter(events);
        mEventList.setAdapter(eventListAdapter);
        mEventList.setOnTouchListener(new ShowHideOnScroll(fab));
        mEventList.addItemDecoration(new StickyRecyclerHeadersDecoration(eventListAdapter));
        mEventList.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.event_list_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}
