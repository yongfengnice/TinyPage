package com.suyf.tiny.page;

import java.util.HashMap;
import java.util.Map;

public class PageResult {
    private Map<String, Object> mObjectMap = new HashMap<>();

    public void putExtra(String key, Object value) {
        mObjectMap.put(key, value);
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defVal) {
        Object o = mObjectMap.get(key);
        if (o instanceof CharSequence) {
            return o.toString();
        } else {
            return defVal;
        }
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defVal) {
        Object o = mObjectMap.get(key);
        if (o instanceof Integer) {
            return Integer.parseInt(o.toString());
        } else {
            return defVal;
        }
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defVal) {
        Object o = mObjectMap.get(key);
        if (o instanceof Boolean) {
            return Boolean.parseBoolean(o.toString());
        } else {
            return defVal;
        }
    }

    public Object getObject(String key) {
        return getObject(key, null);
    }

    public Object getObject(String key, Object defVal) {
        Object o = mObjectMap.get(key);
        if (o != null) {
            return o;
        } else {
            return defVal;
        }
    }
}
