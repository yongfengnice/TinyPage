package com.suyf.tiny;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import com.suyf.tiny.page.PageManager;

/**
 * 仅作模块参考，因为你的项目可能不是继承Activity
 * 更详细的请参考module-mvp
 */
public class DemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PageManager.instance().init(this, (ViewGroup) findViewById(android.R.id.content));
//        PageManager.instance().startPage(new PageIntent(FirstPage.class));
    }

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
