package com.soundsofpolaris.timeline.timeline;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.shamanland.fab.FloatingActionButton;
import com.shamanland.fab.ShowHideOnScroll;
import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.base.BaseActivity;
import com.soundsofpolaris.timeline.tasks.LoadTimelinesTask;

import java.util.List;

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

        final RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.timeline_list_fragment, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) v.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, TimelineEditFragment.newInstance(), TimelineEditFragment.class.toString())
                        .addToBackStack(TimelineEditFragment.class.toString())
                        .commit();
            }
        });

        mtimelineList = (RecyclerView) rootView.findViewById(R.id.timeline_list);
        mtimelineList.setLayoutManager(new LinearLayoutManager(getActivity()));

        mtimelineList.setOnTouchListener(new ShowHideOnScroll(fab));

        LoadTimelinesTask task = new LoadTimelinesTask(new LoadTimelinesTask.Listener() {
            @Override
            public void onTaskComplete(List<Timeline> timelines) {
                mtimelineList.setAdapter(new TimelineListAdapter(timelines));
                ((BaseActivity) getActivity()).hideLoader();
            }
        });

        task.execute();

        ((BaseActivity) getActivity()).showLoader();

        return rootView;
    }

}
