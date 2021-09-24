package cn.toolq.qzone.common;

import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;

/**
 * 全局对象
 */
public class GlobalObject {
    /**
     * QQ空间登录Cookie信息
     */
    public static HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
}
