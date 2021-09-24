package cn.toolq.qzone.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.Cookie;

/**
 * Cookie相关的工具类
 */
public class CookieUtils {
    public static String getCookieStr(HashMap<String, List<Cookie>> cookieStore, String domain) {
        StringBuilder sb = new StringBuilder();
        Iterator<Cookie> iterator = cookieStore.get(domain).iterator();
        while (iterator.hasNext()) {
            Cookie next = iterator.next();
            sb.append(next.name() + "=" + next.value() + ";");
        }
        return sb.toString();
    }

    public static String getCookieValue(HashMap<String, List<Cookie>> cookieStore, String domain, String key) {
        Iterator<Cookie> iterator = cookieStore.get(domain).iterator();
        while (iterator.hasNext()) {
            Cookie next = iterator.next();
            if (key.equals(next.name())) {
                return next.value();
            }
        }
        return null;
    }
}
