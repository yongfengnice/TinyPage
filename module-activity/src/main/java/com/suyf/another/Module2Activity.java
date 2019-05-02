package com.suyf.another;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;

import com.suyf.another.page.CouponPage;
import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.PageManager;

public class Module2Activity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_module2, null);
        setContentView(view);
        PageManager.instance().init(this, (ViewGroup) view);
        PageManager.instance().startPage(new PageIntent(CouponPage.class));
    }

    @Override
    public void onBackPressed() {
        if (!PageManager.instance().handleBackEvent()) {
            super.onBackPressed();
        }
    }
}
