package com.soundsofpolaris.timeline.debug;

import android.util.Log;

/**
 * Created by hscissors on 10/21/14.
 */
public class Logger {
    public static void i(String tag, String msg){
        if(Debug.ENABLED && Debug.LOGGER) Log.i(tag, msg);
    }

    public static void v(String tag, String msg){
        if(Debug.ENABLED && Debug.LOGGER) Log.v(tag, msg);
    }

    public static void d(String tag, String msg){
        if(Debug.ENABLED && Debug.LOGGER) Log.d(tag, msg);
    }

    public static void e(String tag, String msg){
        if(Debug.ENABLED && Debug.LOGGER) Log.e(tag, msg);
    }
}
