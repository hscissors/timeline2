package com.soundsofpolaris.timeline.timeline;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
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
        timelines.add(new Timeline(0, "World War II", 0xffe74c3c));
        timelines.add(new Timeline(0, "World War II", 0xffe74c3c));
        timelines.add(new Timeline(0, "World War II", 0xffe74c3c));
        timelines.add(new Timeline(0, "World War II", 0xffe74c3c));
        timelines.add(new Timeline(0, "World War II", 0xffe74c3c));
        timelines.add(new Timeline(0, "World War II", 0xffe74c3c));

        FrameLayout rootView = (FrameLayout) inflater.inflate(R.layout.timeline_list_fragement, container, false);

        RecyclerView timelineList = (RecyclerView) rootView.findViewById(R.id.timeline_list);
        timelineList.setHasFixedSize(true);
        timelineList.setAdapter(new TimelineListAdapter(timelines));
        timelineList.setLayoutManager(new LinearLayoutManager(getActivity()));

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
