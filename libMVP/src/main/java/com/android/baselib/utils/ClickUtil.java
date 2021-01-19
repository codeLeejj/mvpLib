package com.android.baselib.utils;

import androidx.collection.LruCache;

public class ClickUtil {
    private static final long delayTime = 500;
    private static LruCache<Object, Long> cache = new LruCache<>(10);

    /**
     * @param obj
     * @return true 可以执行,false 不可执行
     */
    public static boolean valid(Object obj) {
        Long current = System.currentTimeMillis();
        Object time = cache.get(obj);

        cache.put(obj, current);
        if (time == null) {
            return true;
        } else {
            Long last = (Long) time;
            boolean result = (current - last) > delayTime;
            return result;
        }
    }

    public static void valid(Object obj, Callback callback) {
        Long current = System.currentTimeMillis();
        Long last = cache.get(obj);
        cache.put(obj, current);
        if (last == null) {
            callback.callback(obj);
        } else {
            boolean result = (current - last) > delayTime;
            if (result) {
                callback.callback(obj);
                cache.put(obj, current);
            }
        }
    }

    public interface Callback {
        void callback(Object obj);
    }
}