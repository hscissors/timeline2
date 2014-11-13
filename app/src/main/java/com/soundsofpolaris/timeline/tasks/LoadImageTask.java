package com.soundsofpolaris.timeline.tasks;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.soundsofpolaris.timeline.TimelineApplication;
import com.soundsofpolaris.timeline.timeline.Timeline;
import com.soundsofpolaris.timeline.tools.FileHelper;

import java.util.List;

/**
 * Created by hscissors on 11/4/14.
 */
public class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {

    private Listener mListener;

    private final String mImageFileName;

    public LoadImageTask(String imageFileName) {
        mImageFileName = imageFileName;
    }


    public static interface Listener {
        public void onTaskComplete(Bitmap image);
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        return FileHelper.loadImage(mImageFileName);
    }

    @Override
    protected void onPostExecute(Bitmap image) {
        if (mListener != null) {
            mListener.onTaskComplete(image);

        }
    }

    public void setListener(Listener listener){
        mListener = listener;
    }}
