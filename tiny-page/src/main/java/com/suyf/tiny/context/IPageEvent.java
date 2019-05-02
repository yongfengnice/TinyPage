package com.suyf.tiny.context;

import android.content.Intent;

import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.PageResult;

public interface IPageEvent {

    void finishPage();

    void onBackPressed();

    void onNewPageIntent(PageIntent pageIntent);

    void onPageResult(PageResult pageResult);

    void setPageResult(PageResult pageResult);

    void onActivityResult(int requestCode, int resultCode, Intent data);

}
