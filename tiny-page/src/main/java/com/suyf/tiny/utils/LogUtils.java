package com.suyf.tiny.utils;

import android.util.Log;

public class LogUtils {
    private static final String TAG = "LogUtils";

    public static void d(Object message) {
        Log.d(TAG, "LogUtils: " + message);
    }

}
