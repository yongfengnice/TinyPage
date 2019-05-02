package com.suyf.home.base;

import com.suyf.tiny.page.PageIntent;

public abstract class BasePresenter<T extends BasePage> {
    private T mPage;

    void attachView(T t) {
        mPage = t;
    }

    void detachView() {
        mPage = null;
    }

    public T getPage() {
        return mPage;
    }

    protected abstract void init(PageIntent pageIntent);
}
