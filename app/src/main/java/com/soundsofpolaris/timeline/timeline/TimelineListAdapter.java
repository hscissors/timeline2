package com.soundsofpolaris.timeline.timeline;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.models.Timeline;
import com.soundsofpolaris.timeline.tools.FileHelper;

import java.util.ArrayList;

public class TimelineListAdapter extends RecyclerView.Adapter<TimelineListAdapter.ViewHolder>{

    //private final Bitmap mImage;
    private int mColor;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mThumbnail;
        View mColor;
        TextView mTitle;
        TextView mDesc;

        public ViewHolder(CardView v){
            super(v);

            mThumbnail = (ImageView) v.findViewById(R.id.timeline_thumbnail);
            mColor = v.findViewById(R.id.timeline_color);
            mTitle = (TextView) v.findViewById(R.id.timeline_title);
            mDesc = (TextView) v.findViewById(R.id.timeline_desc);
        }
    }

    ArrayList<Timeline> mTimelines;

    public TimelineListAdapter(ArrayList<Timeline> timelines){
        mTimelines = timelines;
        //mImage = FileHelper.loadImage("71373-004-23C21E58.jpg");
       // mColor = Palette.generate(mImage).getDarkMutedSwatch().getRgb();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        CardView card = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.timeline_list_item, viewGroup, false);

        ImageButton listItemMenuButton = (ImageButton) card.findViewById(R.id.timeline_list_item_menu_button);

        listItemMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(v.getContext(), v);
                menu.inflate(R.menu.timeline_list_item_menu);
                menu.show();
            }
        });

        ViewHolder vh = new ViewHolder(card);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {

        //viewHolder.mThumbnail.setImageBitmap(mImage);
        viewHolder.mColor.setBackgroundColor(mColor);
        viewHolder.mTitle.setText(mTimelines.get(pos).getName());
        viewHolder.mDesc.setText("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
    }

    @Override
    public int getItemCount() {
        return mTimelines.size();
    }
}
