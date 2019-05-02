package com.suyf.home.base;

import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.TinyPage;

public abstract class BasePage<T extends BasePresenter> extends TinyPage {

    protected T mPresenter;

    @Override
    public void onCreate(PageIntent pageIntent) {
        super.onCreate(pageIntent);
        mPresenter = createPresenter();
        if (mPresenter == null) {
            throw new IllegalArgumentException("please call createPresenter() to provider a presenter!!!");
        }
        mPresenter.attachView(this);

        int layout = setLayout();
        if (layout > 0) {
            setContentView(layout);
        } else {
            throw new IllegalArgumentException("please call setLayout() to provider a xml layout!!!");
        }
        initView();
        initEvent();
        mPresenter.init(pageIntent);
    }

    protected abstract int setLayout();

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract T createPresenter();

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
