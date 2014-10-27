package com.soundsofpolaris.timeline.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.soundsofpolaris.timeline.TimelineApplication;
import com.soundsofpolaris.timeline.debug.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by hscissors on 10/26/14.
 */
public class FileHelper {
    private static final String TAG = FileHelper.class.getSimpleName();

    public static void saveImage(String filename, Bitmap image){
        try {
            File imageFile = new File(filename);
            if(imageFile.exists()) return;

            Context context = TimelineApplication.getInstance().getApplicationContext();
            FileOutputStream out = new FileOutputStream(new File(context.getFilesDir(), filename));
            image.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            Logger.e(TAG, "Image could not be saved! " + filename);
        }
    }
    public static Bitmap loadImage(String filename){
        try {
            Context context = TimelineApplication.getInstance().getApplicationContext();
            InputStream in = context.openFileInput(filename);
            Bitmap image = BitmapFactory.decodeStream(in);
            return image;
        } catch(Exception e){
            Logger.e(TAG, "Image could not be loaded! " + filename);
        }

        return null;
    }
}
