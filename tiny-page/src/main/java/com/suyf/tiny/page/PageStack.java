package com.suyf.tiny.page;

import com.suyf.tiny.utils.LogUtils;

import java.util.LinkedList;

class PageStack {
    private LinkedList<TinyPage> mPageList;

    PageStack() {
        mPageList = new LinkedList<>();
    }


    public TinyPage removeTopPage() {
        return mPageList.pop();
    }

    public void addPageToTop(TinyPage page) {
        mPageList.push(page);
    }

    public boolean isEmpty() {
        return mPageList.isEmpty();
    }

    public TinyPage getTopPage() {
        return mPageList.peek();
    }

    public TinyPage getSecondPage() {
        TinyPage top = mPageList.poll();
        TinyPage second = mPageList.peek();
        mPageList.push(top);
        return second;
    }


    public int size() {
        return mPageList.size();
    }

    public void removeFromStack(TinyPage page) {
        mPageList.remove(page);
    }

    public boolean isPageExist(Class<? extends TinyPage> pageClass) {
        boolean targetPageExist = false;
        for (int i = 0; i < size(); i++) {
            if (mPageList.get(i).getClass() == pageClass) {
                targetPageExist = true;
                break;
            }
        }
        return targetPageExist;
    }

    public void printStackInfo() {
        for (int i = 0; i < size(); i++) {
            LogUtils.d("printStackInfo: " + mPageList.get(i));
        }
    }

    public LinkedList<TinyPage> getPageList() {
        return mPageList;
    }
}
