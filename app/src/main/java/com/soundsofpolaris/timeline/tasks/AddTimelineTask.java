package com.soundsofpolaris.timeline.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.soundsofpolaris.timeline.TimelineApplication;
import com.soundsofpolaris.timeline.timeline.Timeline;
import com.soundsofpolaris.timeline.tools.FileHelper;
import com.soundsofpolaris.timeline.tools.Utils;

import java.util.List;

/**
 * Created by hscissors on 11/4/14.
 */
public class AddTimelineTask extends AsyncTask<Void, Void, Timeline> {

    private Listener mListener;

    private String mTitle;
    private String mDesc;
    private int mColor;
    private String mImageFileName;
    private Bitmap mImage;

    public AddTimelineTask(String title, String desc, int color, String imageFileName, Bitmap image) {
        mTitle = title;
        mDesc = desc;
        mColor = color;
        mImageFileName = imageFileName;
        mImage = image;
    }


    public static interface Listener {
        public void onTaskComplete(Timeline timeline);
    }

    @Override
    protected Timeline doInBackground(Void... params) {
        if(!Utils.isEmpty(mImageFileName) && mImage != null){
            FileHelper.saveImage(mImageFileName, mImage);
        }

        return TimelineApplication.getInstance().getDatabaseHelper().addTimeline(mTitle, mDesc, mColor, mImageFileName);
    }

    @Override
    protected void onPostExecute(Timeline timeline) {
        mImage = null;
        if (mListener != null) {
            mListener.onTaskComplete(timeline);
        }
    }

    public void setListener(Listener listener){
        mListener = listener;
    }
}
