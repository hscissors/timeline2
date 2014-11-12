package com.soundsofpolaris.timeline.timeline;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
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
import com.soundsofpolaris.timeline.tasks.AddTimelineTask;
import com.soundsofpolaris.timeline.tasks.EditTimelineTask;
import com.soundsofpolaris.timeline.tools.Utils;

import java.util.ArrayList;

public class TimelineEditFragment extends Fragment {
    private static String TAG = TimelineEditFragment.class.toString();

    public static final String SELECTED_TIMELINE = "selected_timeline";
    public static final String SELECTED_TIMELINE_POSITION = "selected_timeline_position";

    private Timeline mSelectedTimeline;
    private int mSelectedTimelinePosition;

    private FrameLayout mImageContainer;
    private ImageView mAddImageIcon;
    private ImageView mImageSingle;
    private ViewPager mImagePager;
    private TimelineImagePageAdapter mImagePagerAdapter;
    private FrameLayout mEditColor;
    private SeekBar mColorSlider;
    private EditText mEditTitle;
    private EditText mEditDesc;
    private Button mNegativeButton;
    private Button mPositiveButton;

    public static TimelineEditFragment newInstance(Timeline selectedTimeline, int atPosition) {
        TimelineEditFragment fragment = new TimelineEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_TIMELINE, selectedTimeline);
        args.putInt(SELECTED_TIMELINE_POSITION, atPosition);
        fragment.setArguments(args);
        return fragment;
    }

    public TimelineEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelectedTimeline = (Timeline) getArguments().getParcelable(SELECTED_TIMELINE);
            mSelectedTimelinePosition = getArguments().getInt(SELECTED_TIMELINE_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().supportInvalidateOptionsMenu();

        FrameLayout rootView = (FrameLayout) inflater.inflate(R.layout.timeline_edit_fragment, container, false);

        mImageContainer = (FrameLayout) rootView.findViewById(R.id.timeline_edit_new_image_container);
        mAddImageIcon = (ImageView) rootView.findViewById(R.id.timeline_edit_new_image_icon);
        mImageSingle = (ImageView) rootView.findViewById(R.id.timeline_edit_new_image_single);
        mImagePager = (ViewPager) rootView.findViewById(R.id.timeline_edit_image_list);
        mEditColor = (FrameLayout) rootView.findViewById(R.id.timeline_edit_color);
        mColorSlider = (SeekBar) rootView.findViewById(R.id.timeline_edit_color_slider);
        mEditTitle = (EditText) rootView.findViewById(R.id.timeline_edit_title);
        mEditDesc = (EditText) rootView.findViewById(R.id.timeline_edit_description);
        mNegativeButton = (Button) rootView.findViewById(R.id.timeline_edit_negative_button);
        mPositiveButton = (Button) rootView.findViewById(R.id.timeline_edit_positive_button);

        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = null;
                if (mImageSingle.getVisibility() == View.VISIBLE &&
                        mAddImageIcon.getVisibility() == View.GONE) {
                    bitmap = ((BitmapDrawable) mImageSingle.getDrawable()).getBitmap();
                }

                if (mImagePager.getVisibility() == View.VISIBLE &&
                        mAddImageIcon.getVisibility() == View.GONE) {
                    bitmap = ((BitmapDrawable)((ImageView) mImagePagerAdapter.getCurrentPage().getTag()).getDrawable()).getBitmap();
                }

                String imageFileName = new String();
                if (bitmap != null) {
                    imageFileName = String.valueOf(System.currentTimeMillis() / 1000L);
                }

                String title = mEditTitle.getText().toString();
                if (Utils.isEmpty(title)) {
                    Toast.makeText(getActivity(), "Please add title", Toast.LENGTH_SHORT).show();
                    return;
                }

                String desc = mEditDesc.getText().toString();


                ColorDrawable colorDrawable = (ColorDrawable) mEditColor.getBackground();
                int color = colorDrawable.getColor();

                if(mSelectedTimeline == null) {
                    AddTimelineTask task = new AddTimelineTask(title, desc, color, imageFileName, bitmap);
                    task.setListener(new AddTimelineTask.Listener() {

                        @Override
                        public void onTaskComplete(Timeline timeline) {
                            notifyParentFragment(timeline);
                        }
                    });

                    task.execute();
                } else {
                    EditTimelineTask task = new EditTimelineTask(mSelectedTimeline.getId(), title, desc, color, imageFileName, bitmap);
                    task.setListener(new EditTimelineTask.Listener() {

                        @Override
                        public void onTaskComplete(Timeline timeline) {
                            notifyParentFragment(timeline);
                        }
                    });

                    task.execute();
                }
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
                        ((BaseActivity) getActivity()).hideLoader();
                        if (imageUrls == null) {
                            MessageDialog messageDialog = MessageDialog.newInstance("Select", getResources().getString(R.string.error_select_title), true);
                            messageDialog.show(((BaseActivity) getActivity()).getSupportFragmentManager(), TAG);
                            return;
                        }
                        mAddImageIcon.setVisibility(View.GONE);
                        mImagePager.setVisibility(View.VISIBLE);
                        mImageSingle.setVisibility(View.GONE);

                        mImagePagerAdapter = new TimelineImagePageAdapter(imageUrls, mEditColor);
                        mImagePager.setAdapter(mImagePagerAdapter);
                        mImagePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                ImageView currentImage = (ImageView) mImagePagerAdapter.getCurrentPage().getTag();
                                if (currentImage != null) {
                                    Palette currentPalette = (Palette) currentImage.getTag();
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

        return rootView;
    }

    private void notifyParentFragment(Timeline timeline){
        Intent i = new Intent();
        i.putExtra(SELECTED_TIMELINE, timeline);
        i.putExtra(SELECTED_TIMELINE_POSITION, mSelectedTimelinePosition);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
        getActivity().onBackPressed();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }
}

