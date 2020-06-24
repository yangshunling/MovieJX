package com.yangsl.moviejx.utils;

import com.tencent.mmkv.MMKV;

import static com.yangsl.moviejx.Constance.MOVIEURL;

/**
 * @Description: MMKV高性能存储
 * @Author: Anonymous
 * @Time: 2019/11/16 15:32
 */
public class SpUtil {

    private static MMKV kv = MMKV.defaultMMKV();

    public static void put(String key, Object o) {
        if (o instanceof String)
            kv.encode(key, (String) o);
        if (o instanceof Boolean)
            kv.encode(key, (Boolean) o);
        if (o instanceof Integer)
            kv.encode(key, (Integer) o);
    }

    public static String getString(String key) {
        return kv.decodeString(key, MOVIEURL);
    }

    public static boolean getBoolean(String key) {
        return kv.decodeBool(key, false);
    }

    public static int getInteger(String key) {
        return kv.decodeInt(key, 0);
    }

    public static void remove(String key) {
        kv.remove(key);
    }

    public static void removeValueForKey(String key) {
        kv.removeValueForKey(key);
    }

    public static void cleanAll() {
        kv.clearAll();
    }
}
