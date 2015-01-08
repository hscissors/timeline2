package com.soundsofpolaris.timeline.tasks;

import android.os.AsyncTask;

import com.soundsofpolaris.timeline.TimelineApplication;
import com.soundsofpolaris.timeline.event.Event;
import com.soundsofpolaris.timeline.timeline.Timeline;

import java.util.List;

/**
 * Created by hscissors on 11/4/14.
 */
public class LoadEventsTask extends AsyncTask<Void, Void, List<Event>> {

    Listener mListener;
    long mTimelineId;

    public LoadEventsTask(long id) {
      mTimelineId = id;
    }


    public static interface Listener {
        public void onTaskComplete(List<Event> events);
    }


    @Override
    protected List<Event> doInBackground(Void... params) {
        return TimelineApplication.getInstance().getDatabaseHelper().getAllEventsByTimeline(mTimelineId);
    }

    @Override
    protected void onPostExecute(List<Event> events) {
        if (mListener != null) {
            mListener.onTaskComplete(events);

        }
    }

    public void setListener(Listener listener){
        mListener = listener;
    }
}
