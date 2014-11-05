package com.soundsofpolaris.timeline.timeline;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.base.BaseActivity;
import com.soundsofpolaris.timeline.dialogs.MessageDialog;
import com.soundsofpolaris.timeline.dialogs.SelectImageSourceDialog;
import com.soundsofpolaris.timeline.tasks.SaveTimelineTask;
import com.soundsofpolaris.timeline.tools.FileHelper;
import com.soundsofpolaris.timeline.tools.Utils;

import java.util.ArrayList;

/**
 * Created by hscissors on 10/31/14.
 */
public class TimelineEditView extends FrameLayout {
    private static String TAG = TimelineEditView.class.toString();

    private FrameLayout mImageContainer;
    private ImageView mAddImageIcon;
    private ImageView mImageSingle;
    private ViewPager mImagePager;
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

    private void init(final Context context) {
        inflate(context, R.layout.timeline_edit_view, this);

        mImageContainer = (FrameLayout) findViewById(R.id.timeline_edit_new_image_container);
        mAddImageIcon = (ImageView) findViewById(R.id.timeline_edit_new_image_icon);
        mImageSingle = (ImageView) findViewById(R.id.timeline_edit_new_image_single);
        mImagePager = (ViewPager) findViewById(R.id.timeline_edit_image_list);
        mEditColor = (FrameLayout) findViewById(R.id.timeline_edit_color);
        mColorSlider = (SeekBar) findViewById(R.id.timeline_edit_color_slider);
        mEditTitle = (EditText) findViewById(R.id.timeline_edit_title);
        mEditDesc = (EditText) findViewById(R.id.timeline_edit_description);
        mNegativeButton = (Button) findViewById(R.id.timeline_edit_negative_button);
        mPositiveButton = (Button) findViewById(R.id.timeline_edit_positive_button);

        mNegativeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Delete", Toast.LENGTH_SHORT).show();
            }
        });

        mPositiveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = null;
                if (mImageSingle.getVisibility() == View.VISIBLE &&
                        mAddImageIcon.getVisibility() == View.GONE) {
                    bitmap = ((BitmapDrawable) mImageSingle.getDrawable()).getBitmap();
                }

                if (mImagePager.getVisibility() == View.VISIBLE &&
                        mAddImageIcon.getVisibility() == View.GONE) {

                }

                String title = mEditTitle.getText().toString();
                if (Utils.isEmpty(title)) {
                    Toast.makeText(getContext(), "Please add title", Toast.LENGTH_SHORT).show();
                    return;
                }

                String imageFileName = new String();
                if (bitmap != null) {
                    imageFileName = String.valueOf(System.currentTimeMillis() / 1000L);
                    FileHelper.saveImage(imageFileName, bitmap);
                }

                ColorDrawable colorDrawable = (ColorDrawable) mEditColor.getBackground();
                int color = colorDrawable.getColor();
                final Timeline newTimeline = new Timeline(-1, title, color, imageFileName);

                SaveTimelineTask task = new SaveTimelineTask(new SaveTimelineTask.Listener() {
                    @Override
                    public void onTaskComplete(int gid) {
                        newTimeline.setId(gid);
                    }
                });

                task.execute(newTimeline);
            }
        });

        mImageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                SelectImageSourceDialog selectImageSourceDialog = SelectImageSourceDialog.newInstance(mEditTitle.getText().toString());
                selectImageSourceDialog.setListener(new SelectImageSourceDialog.Listener() {
                    @Override
                    public void onSelectedFromPhone(Bitmap image) {
                        mAddImageIcon.setVisibility(View.GONE);
                        mImagePager.setVisibility(View.GONE);
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
                        ((BaseActivity) getContext()).hideLoader();
                        if (imageUrls == null) {
                            MessageDialog messageDialog = MessageDialog.newInstance("Select", getResources().getString(R.string.error_select_title), true);
                            messageDialog.show(((BaseActivity) context).getSupportFragmentManager(), TAG);
                            return;
                        }
                        mAddImageIcon.setVisibility(View.GONE);
                        mImagePager.setVisibility(View.VISIBLE);
                        mImageSingle.setVisibility(View.GONE);

                        final TimelineEditViewImagePageAdapter mImagePagerAdapter = new TimelineEditViewImagePageAdapter(imageUrls, mEditColor);
                        mImagePager.setAdapter(mImagePagerAdapter);
                        mImagePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                mImagePagerAdapter.setCurrentPagePosition(position);
                                View currentView = mImagePager.findViewWithTag(position);
                                if (currentView != null) {
                                    Palette currentPalette = (Palette) currentView.findViewById(R.id.image_list_item_image_view).getTag();
                                    if (currentPalette != null) {
                                        mEditColor.setBackgroundColor(currentPalette.getDarkVibrantColor(R.color.alternate));
                                    } else {
                                        mEditColor.setBackgroundColor(getResources().getColor(R.color.alternate));
                                    }
                                }
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
                    }
                });

                selectImageSourceDialog.show(((BaseActivity) v.getContext()).getSupportFragmentManager(), TAG);
            }
        });
    }
}
