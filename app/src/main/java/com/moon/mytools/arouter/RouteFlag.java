package com.moon.mytools.arouter;

/**
 * Date: 2022/12/21 13:50
 * Author: Moon
 * Desc:
 */
public interface RouteFlag {
    int FLAG_LOGIN = 0x01;
    int FLAG_AUTHENTICATION = FLAG_LOGIN << 1;
    int FLAG_VIP = FLAG_AUTHENTICATION << 1;
}
