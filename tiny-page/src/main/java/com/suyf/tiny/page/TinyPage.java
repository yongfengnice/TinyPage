package com.suyf.tiny.page;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.suyf.tiny.context.IPageBroadcast;
import com.suyf.tiny.context.IPageEvent;
import com.suyf.tiny.context.IPageLifeCycle;
import com.suyf.tiny.utils.Check;

public class TinyPage implements IPageLifeCycle, IPageEvent, IPageBroadcast {
    private PageDelegate mPageDelegate;
    private View mContentView;
    private IPageContext mPageContext;
    private PageIntent mPageIntent;
    private PageReceiver mPageReceiver;

    public TinyPage() {
        mPageContext = PageManager.instance().getPageContext();
        mPageDelegate = new PageDelegate(mPageContext);
    }

    public Activity getActivity() {
        return mPageContext.getActivity();
    }

    protected void setContentView(int layoutResID) {
        mContentView = LayoutInflater.from(mPageContext.getActivity()).inflate(layoutResID, mPageContext.getContainer(), false);
        mPageDelegate.handleContentView(mContentView);
    }

    public View getContentView() {
        return mContentView;
    }

    public final <T extends View> T findViewById(int id) {
        if (id == View.NO_ID) {
            return null;
        }
        return mContentView.findViewById(id);
    }

    public void startPage(PageIntent pageIntent) {
        Check.notNull(pageIntent);
        mPageDelegate.startPage(this, pageIntent);
    }

    public void startPageForResult(PageIntent pageIntent) {
        Check.notNull(pageIntent);
        mPageDelegate.startPageForResult(this, pageIntent);
    }

    @Override
    public void onCreate(PageIntent pageIntent) {
        mPageDelegate.onCreate(this);
    }

    @Override
    public void onStart() {
        mPageDelegate.onStart(mContentView);
    }

    @Override
    public void onResume() {
        mPageDelegate.onResume(mContentView);
    }

    @Override
    public void onPause() {
        mPageDelegate.onPause(mContentView);
    }

    @Override
    public void onStop() {
        mPageDelegate.onStop(mContentView);
    }

    @Override
    public void onDestroy() {
        mPageDelegate.onDestroy(this);
        if (mPageReceiver != null) {
            mPageReceiver.removeAllAction();
            mPageReceiver = null;
        }
        mContentView = null;
        mPageIntent = null;
        mPageDelegate = null;
        mPageContext = null;
//        System.gc();  //alternate to add gc tip
    }

    @Override
    public void finishPage() {
        mPageDelegate.finishPage(this);
    }

    @Override
    public void onBackPressed() {
        mPageDelegate.onBackPressed(this);
    }

    @Override
    public void onNewPageIntent(PageIntent pageIntent) {

    }

    @Override
    public void onPageResult(PageResult pageResult) {

    }

    @Override
    public void setPageResult(PageResult pageResult) {
        if (mPageIntent != null && mPageIntent.isWaitForResult()) {
            mPageIntent.getSourcePage().onPageResult(pageResult);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public PageIntent getPageIntent() {
        return mPageIntent;
    }

    void setPageIntent(PageIntent pageIntent) {
        mPageIntent = pageIntent;
    }

    PageReceiver getPageReceiver() {
        return mPageReceiver;
    }

    @Override
    public void registerPageReceiver(PageReceiver pageReceiver, String... action) {
        if (mPageReceiver == null && pageReceiver != null) {
            mPageReceiver = pageReceiver;
            mPageReceiver.saveReceiverAction(action);
        } else if (mPageReceiver != null) {
            mPageReceiver.saveReceiverAction(action);
        }
    }

    @Override
    public void unregisterPageReceiver(String... action) {
        if (mPageReceiver != null) {
            mPageReceiver.removeReceiverAction(action);
            if (mPageReceiver.getReceiverActionSize() <= 0) {
                mPageReceiver = null;
            }
        }
    }

    @Override
    public void sendBroadcast(PageIntent pageIntent, String action) {
        mPageDelegate.sendBroadcast(pageIntent, action);
    }

}
