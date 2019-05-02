package com.suyf.tiny.page;

import java.util.HashSet;
import java.util.Set;

public abstract class PageReceiver {
    private Set<String> mActionSet = new HashSet<>();

    public abstract void onBroadcastReceive(PageIntent pageIntent, String action);

    public void saveReceiverAction(String... action) {
        if (action != null && action.length > 0) {
            for (int i = 0; i < action.length; i++) {
                mActionSet.add(action[i]);
            }
        }
    }

    public void removeReceiverAction(String... action) {
        if (action != null && action.length > 0) {
            for (int i = 0; i < action.length; i++) {
                mActionSet.remove(action[i]);
            }
        }
    }

    public int getReceiverActionSize() {
        return mActionSet.size();
    }

    public boolean containAction(String action) {
        return mActionSet.contains(action);
    }

    public void removeAllAction() {
        mActionSet.clear();
    }
}
