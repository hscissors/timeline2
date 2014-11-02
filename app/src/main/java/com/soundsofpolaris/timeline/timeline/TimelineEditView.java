package com.soundsofpolaris.timeline.timeline;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.base.BaseActivity;
import com.soundsofpolaris.timeline.dialogs.MessageDialog;
import com.soundsofpolaris.timeline.dialogs.SelectImageSourceDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hscissors on 10/31/14.
 */
public class TimelineEditView extends FrameLayout {
    private static String TAG = TimelineEditView.class.toString();

    private FrameLayout mImageContainer;
    private ImageView mAddImageIcon;
    private ImageView mImageSingle;
    private ViewPager mImageList;
    private FrameLayout mEditColor;
    private SeekBar mColorSlider;
    private EditText mEditTitle;
    private EditText mEditDesc;
    private Button mNegativeButton;
    private Button mPositiveButton;

    public TimelineEditView(final Context context) {
        super(context);
        init(context);
    }

    private void init(final Context context){
        inflate(context, R.layout.timeline_edit_view, this);

        mImageContainer = (FrameLayout) findViewById(R.id.timeline_edit_new_image_container);
        mAddImageIcon = (ImageView) findViewById(R.id.timeline_edit_new_image_icon);
        mImageSingle = (ImageView) findViewById(R.id.timeline_edit_new_image_single);
        mImageList = (ViewPager) findViewById(R.id.timeline_edit_image_list);
        mEditColor = (FrameLayout) findViewById(R.id.timeline_edit_color);
        mColorSlider = (SeekBar) findViewById(R.id.timeline_edit_color_slider);
        mEditTitle = (EditText) findViewById(R.id.timeline_edit_title);
        mEditDesc = (EditText) findViewById(R.id.timeline_edit_description);
        mNegativeButton = (Button) findViewById(R.id.timeline_edit_negative_button);
        mPositiveButton = (Button) findViewById(R.id.timeline_edit_positive_button);

        mImageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                SelectImageSourceDialog selectImageSourceDialog = SelectImageSourceDialog.newInstance(mEditTitle.getText().toString());
                selectImageSourceDialog.setListener(new SelectImageSourceDialog.Listener() {
                    @Override
                    public void onSelectedFromPhone(Bitmap image) {
                        mAddImageIcon.setVisibility(View.GONE);
                        mImageList.setVisibility(View.GONE);
                        mImageSingle.setVisibility(View.VISIBLE);
                        mImageSingle.setImageBitmap(image);

                        Palette.generateAsync(image, new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                mEditColor.setBackgroundColor(palette.getMutedSwatch().getRgb());
                            }
                        });

                    }

                    @Override
                    public void onSelectedFromWeb(final ArrayList<String> imageUrls) {
                        if (imageUrls == null) {
                            MessageDialog messageDialog = MessageDialog.newInstance("Select", getResources().getString(R.string.error_select_title), true);
                            messageDialog.show(((BaseActivity) context).getSupportFragmentManager(), TAG);
                            return;
                        }
                        mAddImageIcon.setVisibility(View.GONE);
                        mImageList.setVisibility(View.VISIBLE);
                        mImageSingle.setVisibility(View.GONE);

                        mImageList.setAdapter(new TimelineEditViewImageListAdapter(imageUrls));
                    }
                });

                selectImageSourceDialog.show(((BaseActivity) v.getContext()).getSupportFragmentManager(), TAG);
            }
        });
    }
}