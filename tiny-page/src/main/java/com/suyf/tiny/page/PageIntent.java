package com.suyf.tiny.page;

import com.suyf.tiny.utils.LogUtils;

public class PageIntent extends PageResult {
    public static final int MODE_NORMAL = 0;
    public static final int MODE_SINGLE_TOP = 1;
    public static final int MODE_SINGLE_TASK = 2;
    private TinyPage mSourcePage;
    private Class<? extends TinyPage> mTargetClass;
    private int mLaunchMode = MODE_NORMAL;
    private boolean mWaitForResult = false;

    public PageIntent() {
    }

    public PageIntent(Class<? extends TinyPage> targetClass) {
        mTargetClass = targetClass;
    }

    public PageIntent(TinyPage sourcePage, Class<? extends TinyPage> targetClass) {
        this(targetClass);
        mSourcePage = sourcePage;
    }

    public PageIntent(String classKey) {
        try {
            Object name = PageManager.instance().getClassNameByKey(classKey);
            if (name instanceof Class<?>) {
                mTargetClass = (Class<? extends TinyPage>) name;
            } else {
                mTargetClass = (Class<? extends TinyPage>) Class.forName(name.toString());
                PageManager.instance().registerClass(classKey, mTargetClass);
            }
        } catch (Exception e) {
            LogUtils.d("pageIntent target class not found:" + e.getMessage());
        }
    }

    public PageIntent(TinyPage sourcePage, String classKey) {
        this(classKey);
        mSourcePage = sourcePage;
    }

    public void setLaunchMode(int launchMode) {
        if (launchMode < 0 || launchMode > 2) {
            launchMode = MODE_NORMAL;
        }
        mLaunchMode = launchMode;
    }

    int getLaunchMode() {
        return mLaunchMode;
    }

    boolean isWaitForResult() {
        return mWaitForResult;
    }

    void setWaitForResult(boolean waitForResult) {
        mWaitForResult = waitForResult;
    }

    TinyPage getSourcePage() {
        return mSourcePage;
    }

    void setSourcePage(TinyPage sourcePage) {
        mSourcePage = sourcePage;
    }

    Class<? extends TinyPage> getTargetClass() {
        return mTargetClass;
    }

    void setTargetClass(Class<? extends TinyPage> targetClass) {
        mTargetClass = targetClass;
    }
}
