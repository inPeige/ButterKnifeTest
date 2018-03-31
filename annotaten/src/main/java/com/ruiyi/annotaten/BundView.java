package com.ruiyi.annotaten;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liupei on 2018/3/27.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface BundView {
    int value();
}
