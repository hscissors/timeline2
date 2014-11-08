package com.soundsofpolaris.timeline.tasks;

import android.os.AsyncTask;

import com.soundsofpolaris.timeline.TimelineApplication;
import com.soundsofpolaris.timeline.event.Event;
import com.soundsofpolaris.timeline.timeline.Timeline;

public class SaveEventTask extends AsyncTask<Void, Void, Event> {

    private Listener mListener;

    private final int mYear;
    private final int mMonth;
    private final long mDate;
    private final String mTitle;
    private final String mDesc;
    private final boolean mIsAllYear;
    private final boolean mIsAllMonth;
    private final Timeline mParentTimeline;

    public static interface Listener {
        public void onTaskComplete(Event event);
    }

    public SaveEventTask(int year, int month, long date, String title, String desc, boolean isAllYear, boolean isAllMonth, Timeline parentTimeline) {
        mYear = year;
        mMonth = month;
        mDate = date;
        mTitle = title;
        mDesc = desc;
        mIsAllYear = isAllYear;
        mIsAllMonth = isAllMonth;
        mParentTimeline = parentTimeline;

    }

    @Override
    protected Event doInBackground(Void... params) {
        return TimelineApplication.getInstance().getDatabaseHelper().addEvent(mYear, mMonth, mDate, mTitle, mDesc, mIsAllYear, mIsAllMonth, mParentTimeline);
    }

    @Override
    protected void onPostExecute(Event event) {
        if (mListener != null) {
            mListener.onTaskComplete(event);
        }
    }

    public void setListener(Listener listener){
        mListener = listener;
    }
}
