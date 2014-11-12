package com.soundsofpolaris.timeline.timeline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.RelativeLayout;

import com.shamanland.fab.FloatingActionButton;
import com.shamanland.fab.ShowHideOnScroll;
import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.TimelineApplication;
import com.soundsofpolaris.timeline.base.BaseActivity;
import com.soundsofpolaris.timeline.event.EventActivity;
import com.soundsofpolaris.timeline.event.EventListFragment;
import com.soundsofpolaris.timeline.tasks.LoadTimelinesTask;

import java.util.List;

public class TimelineListFragment extends Fragment implements LoadTimelinesTask.Listener, View.OnClickListener, TimelineListAdapter.OnItemClickListener{

    private static final int EDIT_TIMELINE_REQUEST = 1;
    private static final int ADD_TIMELINE_REQUEST = 2;

    private RecyclerView mTimelineList;
    private List<Timeline> mTimelines;

    public static TimelineListFragment newInstance() {
        TimelineListFragment fragment = new TimelineListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        final RelativeLayout rootView = (RelativeLayout) inflater.inflate(R.layout.timeline_list_fragment, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(this);

        mTimelineList = (RecyclerView) rootView.findViewById(R.id.timeline_list);
        mTimelineList.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTimelineList.setOnTouchListener(new ShowHideOnScroll(fab));

        LoadTimelinesTask task = new LoadTimelinesTask(this);

        task.execute();

        ((BaseActivity) getActivity()).showLoader();

        return rootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.timeline_list_menu, menu);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_reorder:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //After tasks are loaded
    @Override
    public void onTaskComplete(List<Timeline> timelines) {
        mTimelines = timelines;
        TimelineListAdapter timelineListAdapter = new TimelineListAdapter(mTimelines);
        timelineListAdapter.setOnItemClickListener(this);
        mTimelineList.setAdapter(timelineListAdapter);
        ((BaseActivity) getActivity()).hideLoader();
    }

    //On fab selected
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.fab:
                TimelineEditFragment editFragment = TimelineEditFragment.newInstance(null, -1);
                editFragment.setTargetFragment(this, ADD_TIMELINE_REQUEST);
                ((BaseActivity) getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, editFragment, TimelineEditFragment.class.toString())
                        .addToBackStack(TimelineEditFragment.class.toString())
                        .commit();
                break;
        }

    }

    //On selected timeline card
    @Override
    public void onItemClick(int pos) {
        Intent i = new Intent(getActivity(), EventActivity.class);
        i.putExtra(TimelineActivity.SELECTED_TIMELINE, mTimelines.get(pos));
        getActivity().startActivity(i);
    }

    //On selected timeline menu
    @Override
    public boolean onItemMenuClick(MenuItem item, int pos) {
        switch (item.getItemId()){
            case R.id.action_edit:
                Timeline timeline = mTimelines.remove(pos);
                TimelineEditFragment editFragment = TimelineEditFragment.newInstance(timeline, pos);
                editFragment.setTargetFragment(this, EDIT_TIMELINE_REQUEST);
                ((BaseActivity) getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, editFragment, TimelineEditFragment.class.toString())
                        .addToBackStack(TimelineEditFragment.class.toString())
                        .commit();
                return true;
            case R.id.action_share:
                return true;
        }

        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timeline timeline = (Timeline) data.getParcelableExtra(TimelineEditFragment.SELECTED_TIMELINE);
        int atPosition = data.getIntExtra(TimelineEditFragment.SELECTED_TIMELINE_POSITION, 0);
        switch(requestCode){
            case ADD_TIMELINE_REQUEST:
                mTimelines.add(0, timeline);
                mTimelineList.getAdapter().notifyItemInserted(0);
                break;
            case EDIT_TIMELINE_REQUEST:
                mTimelines.add(atPosition, timeline);
                mTimelineList.getAdapter().notifyItemChanged(atPosition);
                break;
        }
    }



}
