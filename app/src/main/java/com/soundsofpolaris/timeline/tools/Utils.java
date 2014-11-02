package com.soundsofpolaris.timeline.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.soundsofpolaris.timeline.TimelineApplication;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by hscissors on 10/29/14.
 */
public class Utils {
    public static boolean isEmpty(String string){
        return (string == null || string.isEmpty() || string.equals(""));
    }
}
