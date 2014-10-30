package com.soundsofpolaris.timeline.timeline;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.soundsofpolaris.timeline.R;
import com.soundsofpolaris.timeline.TimelineApplication;
import com.soundsofpolaris.timeline.base.BaseActivity;
import com.soundsofpolaris.timeline.dialogs.SelectImageSourceDialog;
import com.soundsofpolaris.timeline.models.Event;

public class TimelineListItemViewHolder extends RecyclerView.ViewHolder {
    public static final String TAG = TimelineListItemViewHolder.class.toString();

    public Event mEvent;

    public final RelativeLayout mLayout;
    public final ImageView mThumbnail;
    public final View mColor;
    public final TextView mTitle;
    public final TextView mDesc;

    public final LinearLayout mEditLayout;
    public final FrameLayout mEditAddImageButton;
    public final SeekBar mColorSlider;
    public final EditText mEditTitle;
    public final EditText mEditDesc;
    public final Button mNegativeButton;
    public final Button mPositiveButton;

    public TimelineListItemViewHolder(View v) {
        super(v);
        mLayout = (RelativeLayout) v.findViewById(R.id.card_layout);
        mThumbnail = (ImageView) v.findViewById(R.id.timeline_thumbnail);
        mColor = v.findViewById(R.id.timeline_color);
        mTitle = (TextView) v.findViewById(R.id.timeline_title);
        mDesc = (TextView) v.findViewById(R.id.timeline_desc);

        ImageButton listItemMenuButton = (ImageButton) v.findViewById(R.id.timeline_list_item_menu_button);
        listItemMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(v.getContext(), v);
                menu.inflate(R.menu.timeline_list_item_menu);
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.action_share:
                                return true;
                            case R.id.action_edit:
                                mLayout.setVisibility(View.GONE);
                                mEditLayout.setVisibility(View.VISIBLE);
                        }
                        return false;
                    }
                });
            }
        });

        mEditLayout = (LinearLayout) v.findViewById(R.id.card_edit_layout);
        mEditAddImageButton = (FrameLayout) v.findViewById(R.id.timeline_edit_new_image);
        mColorSlider = (SeekBar) v.findViewById(R.id.timeline_edit_color_slider);
        mEditTitle = (EditText) v.findViewById(R.id.timeline_edit_title);
        mEditDesc = (EditText) v.findViewById(R.id.timeline_edit_description);
        mNegativeButton = (Button) v.findViewById(R.id.timeline_edit_negative_button);
        mPositiveButton = (Button) v.findViewById(R.id.timeline_edit_positive_button);

        mEditAddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageSourceDialog selectImageSourceDialog = SelectImageSourceDialog.newInstance(mEditTitle.getText().toString());
                selectImageSourceDialog.show(((BaseActivity) v.getContext()).getSupportFragmentManager(), TAG);
            }
        });
    }
}
