package com.cj.couponlist;

import org.json.JSONArray;

import java.util.Collection;

@SuppressWarnings("unused")
public class CollectionUtils {
    public static boolean isEmpty(int[] arr) {
        return arr == null || arr.length == 0;
    }

    public static boolean isEmpty(float[] arr) {
        return arr == null || arr.length == 0;
    }

    public static boolean isEmpty(boolean[] arr) {
        return arr == null || arr.length == 0;
    }

    public static boolean isEmpty(long[] arr) {
        return arr == null || arr.length == 0;
    }

    public static boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    public static boolean isEmpty(JSONArray arr) {
        return arr == null || arr.length() == 0;
    }

    public static boolean isNotEmpty(JSONArray arr) {
        return !isEmpty(arr);
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean hasIndex(Object[] arr, int index) {
        return !isEmpty(arr) && (index >= 0 && index < arr.length);
    }

    public static boolean hasIndex(Collection<?> collection, int index) {
        return !isEmpty(collection) && (index >= 0 && index < collection.size());
    }

    public static boolean isAllNull(Object... objs) {
        boolean allNull = true;
        for (Object obj : objs) {
            if (obj != null) {
                allNull = false;
                break;
            }
        }
        return allNull;
    }

    public static boolean isAllNotNull(Object... objs) {
        return !isAllNull(objs);
    }

    public static <T> void safeAdd(Collection<T> collection, T item) {
        if (collection != null && item != null) {
            collection.add(item);
        }
    }

    public static <T> void safeAddAll(Collection<T> source, Collection<T> collection) {
        if (source != null && collection != null && !collection.isEmpty()) {
            source.addAll(collection);
        }
    }
}
