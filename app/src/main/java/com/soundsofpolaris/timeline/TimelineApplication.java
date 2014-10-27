package com.soundsofpolaris.timeline;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.ViewConfiguration;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.soundsofpolaris.timeline.debug.Logger;
import com.soundsofpolaris.timeline.tools.DatabaseHelper;
import com.soundsofpolaris.timeline.tools.FileHelper;
import com.soundsofpolaris.timeline.tools.LruBitmapCache;

import java.lang.reflect.Field;
import java.net.URL;

public class TimelineApplication extends Application {
    private static final String TAG = TimelineApplication.class.getSimpleName();

    private static TimelineApplication mInstance;

    private DatabaseHelper db;
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

        if (db == null) {
            db = new DatabaseHelper(getApplicationContext());
            db.updateDatabase(); //1.2.0 -- Used to add new column and "fix" date column
        }

//        final String imageUrl = "http://media-3.web.britannica.com/eb-media/73/71373-004-23C21E58.jpg";
//        final String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
//        addToRequestQueue(
//                new ImageRequest(
//                        imageUrl,
//                    new Response.Listener<Bitmap>() {
//                        @Override
//                        public void onResponse(Bitmap response) {
//                            FileHelper.saveImage(fileName, response);
//                        }
//                    }, 1000, 1000, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Logger.v(TAG, "Volley error with image: " + error.getMessage());
//                    }
//                })
//        );

        mInstance = this;
    }

    public DatabaseHelper getDatabase() {
        return db;
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
