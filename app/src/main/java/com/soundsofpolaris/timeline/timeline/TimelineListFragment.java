package com.soundsofpolaris.timeline.timeline;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.soundsofpolaris.timeline.models.Timeline;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;
import com.soundsofpolaris.timeline.R;

public class TimelineListFragment extends Fragment {

    public static TimelineListFragment newInstance() {
        TimelineListFragment fragment = new TimelineListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ArrayList<Timeline> timelines = new ArrayList();
        timelines.add(new Timeline(0, "World War II", 0xffe74c3c));

        FrameLayout rootView = (FrameLayout) inflater.inflate(R.layout.timeline_list_fragement, container, false);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
