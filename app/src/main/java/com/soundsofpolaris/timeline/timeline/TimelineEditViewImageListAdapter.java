package com.soundsofpolaris.timeline.timeline;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.NetworkImageView;
import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.TimelineApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hscissors on 11/1/14.
 */
public class TimelineEditViewImageListAdapter extends PagerAdapter {

    private ArrayList<String> mImageUrls;

    public TimelineEditViewImageListAdapter(ArrayList<String> imageUrls){
        mImageUrls = imageUrls;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.timeline_edit_view_image_list_item, container, false);

        NetworkImageView image = (NetworkImageView) view.findViewById(R.id.image_list_item_image_view);
        image.setImageUrl(mImageUrls.get(position), TimelineApplication.getInstance().getImageLoader());
        image.setErrorImageResId(R.drawable.ic_action_picture_large);
        image.setTag(position);

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
