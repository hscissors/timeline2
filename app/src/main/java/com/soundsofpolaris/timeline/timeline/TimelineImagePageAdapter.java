package com.soundsofpolaris.timeline.timeline;

import android.support.v4.view.PagerAdapter;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.TimelineApplication;
import com.soundsofpolaris.timeline.gui.PaletteNetworkImageView;

import java.util.ArrayList;

/**
 * Created by hscissors on 11/1/14.
 */
public class TimelineImagePageAdapter extends PagerAdapter {

    private ArrayList<String> mImageUrls;
    private View mColorView;
    private View mCurrentPage;
    private int mCurrentPagePosition;

    public TimelineImagePageAdapter(ArrayList<String> imageUrls, View colorView){
        mImageUrls = imageUrls;
        mColorView = colorView;
    }

    public View getCurrentPage(){
        return mCurrentPage;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentPage = (View) object;
        mCurrentPagePosition = position;
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public View instantiateItem(final ViewGroup container, final int position) {
        final View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.timeline_edit_fragment_image_pager_item, container, false);

        final PaletteNetworkImageView image = (PaletteNetworkImageView) view.findViewById(R.id.image_list_item_image_view);
        image.setImageUrl(mImageUrls.get(position), TimelineApplication.getInstance().getImageLoader());
        image.setErrorImageResId(R.drawable.ic_action_picture_large);
        image.setListener(new PaletteNetworkImageView.Listener() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onPaletteGenerated(Palette palette) {
                if(position == mCurrentPagePosition){
                    mColorView.setBackgroundColor(palette.getDarkVibrantColor(R.color.alternate));
                }
                image.setTag(palette);
            }
        });

        view.setTag(image);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
         container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
