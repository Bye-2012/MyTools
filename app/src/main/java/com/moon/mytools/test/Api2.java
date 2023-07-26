package com.moon.mytools.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date: 2023/7/9 22:34
 * Author: Moon
 * Desc:
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Api2 {
    String path();
}
