package com.soundsofpolaris.timeline.event;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.soundsofpolaris.timeline.base.BaseActivity;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.timeline.Timeline;
import com.soundsofpolaris.timeline.timeline.TimelineActivity;


public class EventActivity extends BaseActivity implements DrawerLayout.DrawerListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.in_right_to_left, R.anim.out_left_to_right);
        setContentView(R.layout.event_activity);

        Timeline selectedTimeline = (Timeline) getIntent().getExtras().getParcelable(TimelineActivity.SELECTED_TIMELINE);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, EventListFragment.newInstance(selectedTimeline))
                    .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerListener(this);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //TODO load timelines, excluding selected
        //ListView linkedTimelineList = (ListView) findViewById(R.id.linked_timeline_list);
        //linkedTimelineList.setAdapter(new LinkedTimelineListAdapter(0));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.in_left_to_right, R.anim.out_right_to_left);
    }

    @Override
    public void onDrawerSlide(View view, float v) {

    }

    @Override
    public void onDrawerOpened(View view) {

    }

    @Override
    public void onDrawerClosed(View view) {

    }

    @Override
    public void onDrawerStateChanged(int i) {

    }
}
