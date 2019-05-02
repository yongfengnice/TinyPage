package com.suyf.tiny.utils;

public class Check {
    public static void notNull(Object object) {
        if (object == null) {
            throw new NullPointerException("exception from check: object==null");
        }
    }
}
