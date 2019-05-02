package com.suyf.tiny.page;

import android.app.Activity;
import android.view.ViewGroup;

class PageContext extends IPageContext {
    private Activity mActivity;
    private ViewGroup mContainer;
    private PageStack mPageStack;

    public PageContext() {
        mPageStack = new PageStack();
    }

    @Override
    void init(Activity activity, ViewGroup container) {
        mActivity = activity;
        mContainer = container;
    }

    @Override
    Activity getActivity() {
        return mActivity;
    }

    @Override
    ViewGroup getContainer() {
        return mContainer;
    }

    @Override
    PageStack getPageStack() {
        return mPageStack;
    }

}