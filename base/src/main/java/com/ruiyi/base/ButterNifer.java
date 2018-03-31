package com.ruiyi.base;

import java.lang.reflect.Constructor;

/**
 * Created by liupei on 2018/3/27.
 */

public class ButterNifer {
    public static final String page_name = "com.ruiyi.bundle.";

    public static void bund(Object aClass) {
        String bundClassName = aClass.getClass().getSimpleName() + "_BundleView";
        try {
            Constructor<?> constructor = Class.forName(page_name + bundClassName).getConstructor(aClass.getClass());
            constructor.newInstance(aClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
