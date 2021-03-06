package com.soundsofpolaris.timeline;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.ViewConfiguration;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.soundsofpolaris.timeline.base.BaseActivity;
import com.soundsofpolaris.timeline.debug.Debug;
import com.soundsofpolaris.timeline.debug.Logger;
import com.soundsofpolaris.timeline.dialogs.MessageDialog;
import com.soundsofpolaris.timeline.tools.DatabaseHelper;
import com.soundsofpolaris.timeline.tools.FileHelper;
import com.soundsofpolaris.timeline.tools.LruBitmapCache;

import java.lang.reflect.Field;
import java.net.URL;

public class TimelineApplication extends Application {
    private static final String TAG = TimelineApplication.class.getSimpleName();

    private static TimelineApplication mInstance;

    private DatabaseHelper mDatabaseHelper;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;

    public static synchronized TimelineApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        try {
//            ViewConfiguration config = ViewConfiguration.get(this);
//            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
//            if (menuKeyField != null) {
//                menuKeyField.setAccessible(true);
//                menuKeyField.setBoolean(config, false);
//            }
//        } catch (Exception ex) {
//            // Ignore
//        }

        if (mDatabaseHelper == null) {
            mDatabaseHelper = new DatabaseHelper(getApplicationContext());
            mDatabaseHelper.updateDatabase(); //1.2.0 -- Used to add new column and "fix" date column
        }

        mInstance = this;
    }

    public static void handleException(Exception e){
//        FragmentManager fm = ((BaseActivity) getInstance().getBaseContext()).getSupportFragmentManager();
//        if(Debug.ENABLED){
//            MessageDialog messageDialog = MessageDialog.newInstance("Error", e.getMessage(), false);
//            messageDialog.show(fm, TAG);
//        } else {
//            MessageDialog messageDialog = MessageDialog.newInstance("Error", getInstance().getResources().getString(R.string.default_error_message), false);
//            messageDialog.show(fm, TAG);
//        }
    }

    public DatabaseHelper getDatabaseHelper() {
        return mDatabaseHelper;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
