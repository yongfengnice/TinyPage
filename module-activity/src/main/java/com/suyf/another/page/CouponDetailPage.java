package com.suyf.another.page;

import com.suyf.another.R;
import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.TinyPage;

public class CouponDetailPage extends TinyPage {
    @Override
    public void onCreate(PageIntent pageIntent) {
        super.onCreate(pageIntent);
        setContentView(R.layout.page_coupon_detail);
    }
}
