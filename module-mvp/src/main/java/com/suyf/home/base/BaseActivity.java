package com.suyf.home.base;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.suyf.tiny.page.PageManager;

public class BaseActivity extends FragmentActivity {

    @Override
    public void onBackPressed() {
        if (!PageManager.instance().handleBackEvent()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PageManager.instance().onActivityResult(requestCode, resultCode, data);
    }
}
