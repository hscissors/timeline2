package com.soundsofpolaris.timeline.base;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.soundsofpolaris.timeline.R;

public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showLoader(){
        RelativeLayout loader = (RelativeLayout) this.findViewById(R.id.loading_screen);
        if(loader != null){
            loader.setVisibility(View.VISIBLE);
        }
    }
    public void hideLoader(){
        RelativeLayout loader = (RelativeLayout) this.findViewById(R.id.loading_screen);
        if(loader != null){
            loader.setVisibility(View.GONE);
        }
    }
}
