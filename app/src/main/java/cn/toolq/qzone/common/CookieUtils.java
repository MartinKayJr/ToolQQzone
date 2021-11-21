package cn.toolq.qzone.common;

import android.content.Context;

import com.tencent.mmkv.MMKV;
import com.xuexiang.xutil.net.JsonUtil;
import com.xuexiang.xutil.net.type.TypeBuilder;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import okhttp3.Cookie;

/**
 * Cookie相关的工具类
 */
public class CookieUtils {

    private static final String QZONE_COOKIE = "cn.toolq.qzone.common.KEY_TOKEN";

    private static final Type TYPE = TypeBuilder.newInstance(HashMap.class)
            .addTypeParam(String.class)
            // 开始 List<Cookie> 部分
            .beginSubType(List.class)
            // 设置List的泛型值
            .addTypeParam(Cookie.class)
            //结束 List<Cookie> 部分
            .endSubType()
            .build();

    public static void init(Context context) {
        MMKV.initialize(context);
        String cookiesMapJson = MMKV.defaultMMKV().decodeString(QZONE_COOKIE, "");
        GlobalObject.cookieStore = JsonUtil.fromJson(cookiesMapJson, TYPE);
    }

    /**
     * 保存Cookie到MMKV
     *
     * @param cookieMap Cookie的Map
     */
    public static void saveCookie(HashMap<String, List<Cookie>> cookieMap) {
        String cookiesMapJson = JsonUtil.toJson(cookieMap);
        MMKV.defaultMMKV().putString(QZONE_COOKIE, cookiesMapJson);
    }

    /**
     * 清除Cookie
     */
    public static void clearCookie() {
        GlobalObject.cookieStore = new HashMap<>();
        MMKV.defaultMMKV().remove(QZONE_COOKIE);
    }

    /**
     * 获取Cookie
     * @return CookieMap
     */
    public static HashMap<String, List<Cookie>> getCookie() {
        return GlobalObject.cookieStore;
    }

    /**
     * 是否有Cookie
     * @return 是/否
     */
    public static boolean hasCookie() {

        return MMKV.defaultMMKV().containsKey(QZONE_COOKIE);
    }

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
