package com.soundsofpolaris.timeline.tasks;

import android.os.AsyncTask;

import com.soundsofpolaris.timeline.TimelineApplication;
import com.soundsofpolaris.timeline.timeline.Timeline;

import java.util.List;

/**
 * Created by hscissors on 11/4/14.
 */
public class SaveTimelineTask extends AsyncTask<Timeline, Void, Integer> {

    Listener mListener;

    public SaveTimelineTask(Listener listener) {
        mListener = listener;
    }


    public static interface Listener {
        public void onTaskComplete(int gid);
    }

    @Override
    protected Integer doInBackground(Timeline... params) {
        if(params[0] == null){
            return -1;
        }

        return TimelineApplication.getInstance().getDatabaseHelper().addGroup(
                params[0].getName(),
                params[0].getColor(),
                params[0].getImageFile());
    }

    @Override
    protected void onPostExecute(Integer gid) {
        if (mListener != null) {
            mListener.onTaskComplete(gid);

        }
    }
}
