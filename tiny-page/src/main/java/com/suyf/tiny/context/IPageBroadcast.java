package com.suyf.tiny.context;

import com.suyf.tiny.page.PageIntent;
import com.suyf.tiny.page.PageReceiver;

public interface IPageBroadcast {

    void registerPageReceiver(PageReceiver pageReceiver, String... action);

    void unregisterPageReceiver(String... action);

    void sendBroadcast(PageIntent pageIntent, String action);

}
