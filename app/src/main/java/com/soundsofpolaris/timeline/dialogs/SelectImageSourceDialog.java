package com.soundsofpolaris.timeline.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.base.BaseActivity;
import com.soundsofpolaris.timeline.tasks.LoadImageURLsTask;
import com.soundsofpolaris.timeline.tools.FileHelper;
import com.soundsofpolaris.timeline.tools.Utils;

import java.util.ArrayList;

public class SelectImageSourceDialog extends DialogFragment {

    private static final String TAG = SelectImageSourceDialog.class.toString();
    private static final int SELECT_PICTURE = 1;
    private static final String TIMELINE_TITLE = "timeline_title";

    private String mTimelineTitle;

    private Button mSelectFromPhone;
    private Button mSelectFromWeb;

    private Listener mListener;

    public static SelectImageSourceDialog newInstance(String timelineTitle) {
        SelectImageSourceDialog fragment = new SelectImageSourceDialog();

        Bundle args = new Bundle();
        args.putString(TIMELINE_TITLE, timelineTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments() != null){
            mTimelineTitle = getArguments().getString(TIMELINE_TITLE);
        }

        View v = inflater.inflate(R.layout.dialog_select_image_source, container, false);

        mSelectFromPhone = (Button) v.findViewById(R.id.dialog_select_image_source_phone_button);
        mSelectFromWeb = (Button) v.findViewById(R.id.dialog_select_image_source_web_button);

        mSelectFromPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFromPhone();
            }
        });

        mSelectFromWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFromWeb();
            }
        });
        return v;
    }

    public void setListener(Listener listener){
        mListener = listener;
    }

    private void selectFromPhone(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent imageReturned) {
        super.onActivityResult(requestCode, resultCode, imageReturned);

        switch(requestCode) {
            case SELECT_PICTURE:
                if(resultCode == Activity.RESULT_OK){
                    try {
                        Uri uri = imageReturned.getData();
                        Bitmap image = FileHelper.resampleBitmap(uri, getActivity());
                        if(mListener != null){
                            mListener.onSelectedFromPhone(image);
                        }
                        dismiss();

                    } catch (Exception e) {
                        MessageDialog messageDialog = MessageDialog.newInstance("Error", getResources().getString(R.string.error_select_new_image), true);
                        messageDialog.show(getActivity().getSupportFragmentManager(), TAG);
                    }
                }
        }
    }

    private void selectFromWeb(){
        if(Utils.isEmpty(mTimelineTitle)){
            mListener.onSelectedFromWeb(null);
            dismiss();
            return;
        } else {
            ((BaseActivity) getActivity()).showLoader();
            ArrayList<String> imageUrls = new ArrayList();
            LoadImageURLsTask task = new LoadImageURLsTask(new LoadImageURLsTask.Listener() {
                @Override
                public void onTaskComplete(ArrayList<String> imageUrls) {
                    if(mListener != null){
                        mListener.onSelectedFromWeb(imageUrls);
                    }
                }
            });

            task.execute(mTimelineTitle);
            dismiss();
        }
    }

    public static interface Listener{
        public void onSelectedFromPhone(Bitmap image);
        public void onSelectedFromWeb(ArrayList<String> imageUrls);
    }
}

