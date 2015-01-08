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
import android.widget.TextView;

import com.shamanland.fab.FloatingActionButton;
import com.shamanland.fab.ShowHideOnScroll;
import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.base.BaseActivity;
import com.soundsofpolaris.timeline.event.EventActivity;
import com.soundsofpolaris.timeline.tasks.LoadTimelinesTask;

import java.util.List;

public class TimelineListFragment extends Fragment implements LoadTimelinesTask.Listener, View.OnClickListener, TimelineListAdapter.OnItemClickListener{

    private static final int EDIT_TIMELINE_REQUEST = 1;
    private static final int ADD_TIMELINE_REQUEST = 2;

    private RelativeLayout mEmptyMessageContainer;

    private RecyclerView mTimelineList;
    private TimelineListAdapter mTimelineListAdapter;
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

        mEmptyMessageContainer = (RelativeLayout) rootView.findViewById(R.id.empty_message_container);
        TextView emptyMessage = (TextView) rootView.findViewById(R.id.empty_message);
        emptyMessage.setText(getString(R.string.empty_timelines));

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

        mTimelineListAdapter = new TimelineListAdapter(mTimelines);
        mTimelineListAdapter.setOnItemClickListener(this);
        mTimelineListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkAdapterIsEmpty();
            }
        });

        mTimelineList.setAdapter(mTimelineListAdapter);

        checkAdapterIsEmpty();

        ((BaseActivity) getActivity()).hideLoader();
    }

    private void checkAdapterIsEmpty(){
        if(mTimelineListAdapter != null && mTimelineListAdapter.getItemCount() > 0){
            mEmptyMessageContainer.setVisibility(View.GONE);
        }  else {
            mEmptyMessageContainer.setVisibility(View.VISIBLE);
        }
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
        i.putExtra(EventActivity.SELECTED_TIMELINE, mTimelines.get(pos));
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

        ((BaseActivity) getActivity()).hideLoader();

        Timeline timeline = (Timeline) data.getParcelableExtra(TimelineEditFragment.EDIT_TIMELINE);
        int atPosition = data.getIntExtra(TimelineEditFragment.EDIT_TIMELINE_POSITION, 0);
        switch(requestCode){
            case ADD_TIMELINE_REQUEST:
                mTimelines.add(0, timeline);
                mTimelineList.getAdapter().notifyDataSetChanged();
                break;
            case EDIT_TIMELINE_REQUEST:
                mTimelines.add(atPosition, timeline);
                mTimelineList.getAdapter().notifyItemChanged(atPosition);
                break;
        }
    }



}
