package com.soundsofpolaris.timeline.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.soundsofpolaris.timeline.R;

public class BaseActivity extends ActionBarActivity {
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
