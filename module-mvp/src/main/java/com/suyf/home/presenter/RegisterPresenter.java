package com.suyf.home.presenter;

import com.suyf.home.base.BasePresenter;
import com.suyf.home.page.RegisterPage;
import com.suyf.tiny.page.PageIntent;

public class RegisterPresenter extends BasePresenter<RegisterPage> {

    @Override
    protected void init(PageIntent pageIntent) {
        getPage().showIntentText(pageIntent);
    }
}
