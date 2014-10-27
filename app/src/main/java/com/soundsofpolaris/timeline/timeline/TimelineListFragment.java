package com.soundsofpolaris.timeline.timeline;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.models.Timeline;

import java.util.ArrayList;

public class TimelineListFragment extends Fragment {

    private RecyclerView mtimelineList;

    public static TimelineListFragment newInstance() {
        TimelineListFragment fragment = new TimelineListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ArrayList<Timeline> timelines = new ArrayList();
        timelines.add(new Timeline(0, "Horsehead Nebula", 0xffe74c3c));
        timelines.add(new Timeline(0, "Horsehead Nebula", 0xffe74c3c));
        timelines.add(new Timeline(0, "Horsehead Nebula", 0xffe74c3c));
        timelines.add(new Timeline(0, "Horsehead Nebula", 0xffe74c3c));
        timelines.add(new Timeline(0, "Horsehead Nebula", 0xffe74c3c));
        timelines.add(new Timeline(0, "Horsehead Nebula", 0xffe74c3c));

        FrameLayout rootView = (FrameLayout) inflater.inflate(R.layout.timeline_list_fragement, container, false);

        mtimelineList = (RecyclerView) rootView.findViewById(R.id.timeline_list);
//        mtimelineList.setHasFixedSize(true);
        mtimelineList.setAdapter(new TimelineListAdapter(timelines));
        mtimelineList.setLayoutManager(new LinearLayoutManager(getActivity()));

        mtimelineList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    ((ActionBarActivity )getActivity()).getSupportActionBar().show();
                } else {
                    ((ActionBarActivity )getActivity()).getSupportActionBar().hide();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
