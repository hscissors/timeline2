package com.soundsofpolaris.timeline.timeline;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.TimelineApplication;
import com.soundsofpolaris.timeline.gui.CustomNetworkImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hscissors on 11/1/14.
 */
public class TimelineEditViewImagePageAdapter extends PagerAdapter {

    private ArrayList<String> mImageUrls;
    private View mColor;
    private int mCurrentPagePosition = -1;

    public TimelineEditViewImagePageAdapter(ArrayList<String> imageUrls, View color){
        mImageUrls = imageUrls;
        mColor = color;
    }

    public void setCurrentPagePosition(int currentPagePosition){
        mCurrentPagePosition = currentPagePosition;
    }

    @Override
    public View instantiateItem(final ViewGroup container, final int position) {
        final View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.timeline_edit_view_image_list_item, container, false);
        view.setTag(position);

        final CustomNetworkImageView image = (CustomNetworkImageView) view.findViewById(R.id.image_list_item_image_view);
        image.setImageUrl(mImageUrls.get(position), TimelineApplication.getInstance().getImageLoader());
        image.setErrorImageResId(R.drawable.ic_action_picture_large);
        image.setListener(new CustomNetworkImageView.Listener() {
            @Override
            public void onError() {

            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onPaletteGenerated(Palette palette) {
                if(position == mCurrentPagePosition){
                    mColor.setBackgroundColor(palette.getDarkVibrantColor(R.color.alternate));
                }
                image.setTag(palette);
            }
        });
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
