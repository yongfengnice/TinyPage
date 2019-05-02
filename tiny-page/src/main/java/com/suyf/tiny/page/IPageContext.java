package com.suyf.tiny.page;

import android.app.Activity;
import android.view.ViewGroup;

public abstract class IPageContext {

    abstract void init(Activity activity, ViewGroup container);

    abstract Activity getActivity();

    abstract ViewGroup getContainer();

    abstract PageStack getPageStack();
}
