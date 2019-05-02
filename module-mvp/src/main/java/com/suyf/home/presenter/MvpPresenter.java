package com.suyf.home.presenter;

import com.suyf.home.base.BasePresenter;
import com.suyf.home.page.MvpPage;
import com.suyf.tiny.page.PageIntent;

public class MvpPresenter extends BasePresenter<MvpPage> {

    @Override
    protected void init(PageIntent pageIntent) {
        getPage().showIntentInfo(pageIntent);
    }
}
