package com.suyf.tiny.page;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.suyf.tiny.R;
import com.suyf.tiny.listener.AnimListener;
import com.suyf.tiny.utils.LogUtils;

import java.util.LinkedList;

class PageDelegate {
    private IPageContext mPageContext;

    PageDelegate(IPageContext pageContext) {
        mPageContext = pageContext;
    }

    public void handleContentView(View contentView) {
        mPageContext.getContainer().addView(contentView);
        contentView.setVisibility(View.INVISIBLE);
        if (contentView.getBackground() == null) {
            contentView.setBackgroundColor(0xffffffff);
        }
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.d("contentView onClick: ");
            }
        });
    }

    public void startPage(TinyPage currentPage, PageIntent pageIntent) {
        pageIntent.setWaitForResult(false);
        startPageInternal(currentPage, pageIntent);
    }

    public void startPageForResult(TinyPage currentPage, PageIntent pageIntent) {
        pageIntent.setWaitForResult(true);
        startPageInternal(currentPage, pageIntent);
    }

    private void startPageInternal(TinyPage currentPage, PageIntent pageIntent) {
        switch (pageIntent.getLaunchMode()) {
            case PageIntent.MODE_SINGLE_TOP:
                startPageSingleTop(currentPage, pageIntent);
                break;
            case PageIntent.MODE_SINGLE_TASK:
                startPageSingleTask(currentPage, pageIntent);
                break;
            case PageIntent.MODE_NORMAL:
                startPageNormal(currentPage, pageIntent);
            default:
                break;
        }
    }

    private void startPageSingleTask(TinyPage currentPage, PageIntent pageIntent) {
        PageStack pageStack = mPageContext.getPageStack();
        if (pageStack.isPageExist(pageIntent.getTargetClass())) {
            while (pageStack.size() > 0) {
                TinyPage page = pageStack.removeTopPage();
                if (page.getClass() == pageIntent.getTargetClass()) {
                    //find target page
                    pageStack.addPageToTop(page);
                    page.setPageIntent(pageIntent);
                    page.onNewPageIntent(pageIntent);
                    page.onStart();
                    page.onResume();
                    return;
                } else {
                    page.onStop();
                    page.onDestroy();
                }
            }
        } else {
            startPageNormal(currentPage, pageIntent);
        }
    }

    private void startPageSingleTop(TinyPage currentPage, PageIntent pageIntent) {
        TinyPage topPage = mPageContext.getPageStack().getTopPage();
        if (topPage != null && topPage.getClass() == pageIntent.getTargetClass()) {
            topPage.setPageIntent(pageIntent);
            topPage.onNewPageIntent(pageIntent);
        } else {
            startPageNormal(currentPage, pageIntent);
        }
    }

    private void startPageNormal(TinyPage currentPage, PageIntent pageIntent) {
        try {//create,add to stack
            TinyPage nextPage = pageIntent.getTargetClass().newInstance();
            nextPage.setPageIntent(pageIntent);
            nextPage.onCreate(pageIntent);
            enterPageWithAnim();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enterPageWithAnim() {
//        mPageContext.getPageStack().printStackInfo();
        TinyPage topPage = mPageContext.getPageStack().getTopPage();
        topPage.onStart();//visible
        Animation animation = AnimationUtils.loadAnimation(mPageContext.getActivity(), R.anim.page_enter);
        topPage.getContentView().setAnimation(animation);
        animation.setAnimationListener(new AnimListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                mPageContext.getPageStack().getTopPage().onResume();
                TinyPage secondPage = mPageContext.getPageStack().getSecondPage();
                if (secondPage != null) {
                    secondPage.onPause();
                    secondPage.onStop();//gone
                }
            }
        });
    }

    public void onCreate(TinyPage page) {
        mPageContext.getPageStack().addPageToTop(page);
    }

    public void onStart(View contentView) {
        if (contentView != null) {
            contentView.setVisibility(View.VISIBLE);
        }
    }

    public void onResume(View contentView) {
        contentView.setFocusable(true);
        contentView.setFocusableInTouchMode(true);
    }

    public void onPause(View contentView) {
        contentView.setFocusable(false);
        contentView.setFocusableInTouchMode(false);
    }

    public void onStop(View contentView) {
        if (contentView != null) {
            contentView.setVisibility(View.INVISIBLE);
        }
    }

    public void onDestroy(TinyPage page) {
        LogUtils.d(page.getClass() + " ========= onDestroy");
        PageIntent pageIntent = page.getPageIntent();
        if (pageIntent != null) {
            pageIntent.setWaitForResult(false);
            pageIntent.setSourcePage(null);
            pageIntent.setTargetClass(null);
            page.setPageIntent(null);
        }
        mPageContext.getContainer().removeView(page.getContentView());
        mPageContext.getPageStack().removeFromStack(page);
        if (mPageContext.getPageStack().isEmpty()) {
            //empty,exit activity,exit app
            mPageContext.getActivity().finish();
            PageManager.instance().destroy();
            mPageContext = null;
        }
    }

    //remove top page
    public void onBackPressed(TinyPage page) {
        mPageContext.getPageStack().printStackInfo();
        if (mPageContext.getPageStack().size() > 1) {
            exitPageWithAnim(page);
        } else {
            page.onPause();
            page.onStop();
            page.onDestroy();
        }
    }

    public void finishPage(TinyPage page) {
        if (mPageContext.getPageStack().getTopPage() == page) {
            //top page
            onBackPressed(page);
        } else {
            // other page
            page.onPause();
            page.onStop();
            page.onDestroy();
        }
    }

    private void exitPageWithAnim(TinyPage page) {
        page.onPause();//not focusable
        TinyPage prePage = mPageContext.getPageStack().getSecondPage();
        prePage.onStart();
        prePage.onResume();

        if (page.getContentView().getContext() != prePage.getContentView().getContext()) {
            //activity has changed,finish current page activity,restore pre page activity
            Activity activity = page.getActivity();
            page.onStop();
            page.onDestroy();
            activity.finish();
            //restore prePage activity
            View prePageView = prePage.getContentView();
            PageManager.instance().init((Activity) prePageView.getContext(), (ViewGroup) prePageView.getParent());
            System.gc();
            return;
        }

        page.getContentView().clearAnimation();
        Animation animation = AnimationUtils.loadAnimation(mPageContext.getActivity(), R.anim.page_exit);
        page.getContentView().setAnimation(animation);
        animation.setAnimationListener(new AnimListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                super.onAnimationEnd(animation);
                TinyPage topPage = mPageContext.getPageStack().getTopPage();
                topPage.onStop();//invisible
                topPage.onDestroy();
            }
        });
    }

    public void sendBroadcast(PageIntent pageIntent, String action) {
        LinkedList<TinyPage> pageList = mPageContext.getPageStack().getPageList();
        PageReceiver receiver;
        for (TinyPage tinyPage : pageList) {
            receiver = tinyPage.getPageReceiver();
            if (receiver != null && receiver.containAction(action)) {
                receiver.onBroadcastReceive(pageIntent, action);
            }
        }
    }
}
