package com.suyf.another.page;

import android.view.View;

import com.suyf.another.R;
import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.TinyPage;

public class CouponPage extends TinyPage {
    @Override
    public void onCreate(PageIntent pageIntent) {
        super.onCreate(pageIntent);
        setContentView(R.layout.page_coupon);
        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPage(new PageIntent(CouponPage.this, CouponListPage.class));
            }
        });
    }
}
