package com.suyf.another.page;

import android.view.View;

import com.suyf.another.R;
import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.TinyPage;

public class CouponListPage extends TinyPage {
    @Override
    public void onCreate(PageIntent pageIntent) {
        super.onCreate(pageIntent);
        setContentView(R.layout.page_coupon_list);
        findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPage(new PageIntent(CouponListPage.this, CouponDetailPage.class));
            }
        });
    }
}
