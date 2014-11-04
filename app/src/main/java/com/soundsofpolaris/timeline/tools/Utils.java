package com.soundsofpolaris.timeline.tools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.view.View;

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

    public static Bitmap loadBitmapFromView(View v) {
        if (v.getLayoutParams().width <= 0) {
            v.measure(v.getLayoutParams().WRAP_CONTENT, v.getLayoutParams().WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            return b;
        }
        Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        return b;
    }
}
