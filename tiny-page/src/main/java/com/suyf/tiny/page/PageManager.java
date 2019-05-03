package com.suyf.tiny.page;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.util.SparseArray;
import android.view.ViewGroup;

public class PageManager {
    private static PageManager sPageManager;
    private IPageContext mPageContext;
    private SparseArray<Object> mClassMap;

    private PageManager() {
    }

    public static PageManager instance() {
        if (sPageManager == null) {
            synchronized (PageManager.class) {
                if (sPageManager == null) {
                    sPageManager = new PageManager();
                }
            }
        }
        return sPageManager;
    }

    //可以多次调用init方法，切换Activity和Container
    public void init(Activity activity, ViewGroup container) {
        if (mPageContext == null) {
            mPageContext = new PageContext();
        }
        mPageContext.init(activity, container);
    }

    public IPageContext getPageContext() {
        return mPageContext;
    }

    void destroy() {
        unRegisterAllClass();
        mPageContext = null;
    }

    public void startPageForResult(PageIntent pageIntent) {
        TinyPage topPage = mPageContext.getPageStack().getTopPage();
        if (topPage != null) {
            pageIntent.setSourcePage(topPage);
            topPage.startPageForResult(pageIntent);
        }
    }

    public void startPage(PageIntent pageIntent) {
        TinyPage topPage = mPageContext.getPageStack().getTopPage();
        if (topPage != null) {
            pageIntent.setSourcePage(topPage);
            topPage.startPage(pageIntent);
        } else {//first page
            try {
                TinyPage page = pageIntent.getTargetClass().newInstance();
                page.onCreate(pageIntent);
                page.onStart();
                page.onResume();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startActivity(Activity sourceActivity, String classKey) {
        Object name = getClassNameByKey(classKey);
        if (name != null) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(sourceActivity, name.toString()));
            sourceActivity.startActivity(intent);
        }
    }

    //exit app
    public void exitApp() {
        PageStack pageStack = mPageContext.getPageStack();
        while (!pageStack.isEmpty()) {
            pageStack.removeTopPage().finishPage();
        }
    }

    public boolean handleBackEvent() {
        if (!mPageContext.getPageStack().isEmpty()) {
            mPageContext.getPageStack().getTopPage().onBackPressed();
            return true;
        }
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        TinyPage topPage = mPageContext.getPageStack().getTopPage();
        if (topPage != null) {
            topPage.onActivityResult(requestCode, resultCode, data);
        }
    }

    //=============================register class

    public void registerClass(String classKey, Object className) {
        if (mClassMap == null) {
            mClassMap = new SparseArray<>();
        }
        mClassMap.put(classKey.hashCode(), className);
    }

    public void unRegisterClass(String classKey) {
        if (mClassMap != null) {
            mClassMap.delete(classKey.hashCode());
        }
    }

    public void unRegisterAllClass() {
        if (mClassMap != null) {
            mClassMap.clear();
            mClassMap = null;
        }
    }

    public Object getClassNameByKey(String classKey) {
        if (mClassMap != null) {
            return mClassMap.get(classKey.hashCode());
        }
        return null;
    }
}
