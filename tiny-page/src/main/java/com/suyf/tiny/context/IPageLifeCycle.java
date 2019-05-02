package com.suyf.tiny.context;

import com.suyf.tiny.page.PageIntent;

public interface IPageLifeCycle {
    void onCreate(PageIntent pageIntent);
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
}
