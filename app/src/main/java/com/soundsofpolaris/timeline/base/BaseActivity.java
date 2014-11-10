package com.soundsofpolaris.timeline.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_about:
                return true;
            case R.id.action_donate:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
