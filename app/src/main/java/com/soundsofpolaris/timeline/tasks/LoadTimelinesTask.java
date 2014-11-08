package com.soundsofpolaris.timeline.tasks;

import android.os.AsyncTask;

import com.soundsofpolaris.timeline.TimelineApplication;
import com.soundsofpolaris.timeline.timeline.Timeline;

import java.util.List;

/**
 * Created by hscissors on 11/4/14.
 */
public class LoadTimelinesTask extends AsyncTask<Void, Void, List<Timeline>> {

    Listener mListener;

    public LoadTimelinesTask(Listener listener) {
        mListener = listener;
    }


    public static interface Listener {
        public void onTaskComplete(List<Timeline> timelines);
    }

    @Override
    protected List<Timeline> doInBackground(Void... params) {
        return TimelineApplication.getInstance().getDatabaseHelper().getAllTimelines();
    }

    @Override
    protected void onPostExecute(List<Timeline> timelines) {
        if (mListener != null) {
            mListener.onTaskComplete(timelines);

        }
    }
}
