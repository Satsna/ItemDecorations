package com.sheldonchen.itemdecorations;

/**
 * Created by cxd on 2018/4/17
 */

public class CheckUtil {
    private CheckUtil() {/* no instance.*/}

    public static int ensureNatural(int value) {
        return value < 0 ? 0 : value;
    }
}
