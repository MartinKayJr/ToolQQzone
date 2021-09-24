package cn.toolq.qzone.common;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * QQ相关工具类
 */
public class QQUtils {

    /**
     * 通过qrsig计算出PtQrToken
     *
     * @param t qrsig
     * @return PtQrToken
     */
    public static int getPtQrToken(String t) {
        int e = 0, n = t.length();
        for (int i = 0; i < n; ++i) {
            e += (e << 5) + t.charAt(i);
        }
        return 2147483647 & e;
    }

    /**
     * 通过skey获取g_tk
     * @param sKey skey
     * @return g_tk
     */
    public static long getGTK(String sKey) {
        int hash = 5381;
        char[] sKeyArr = sKey.toCharArray();
        for (int i = 0, len = sKey.length(); i < len; ++i) {
            hash += (hash << 5) + (int) sKeyArr[i];
        }
        return (hash & 0x7fffffff);
    }


    /**
     * 通过skey获取bkn
     *
     * @param sKey skey
     * @return bkn
     */
    public static long getBkn(String sKey) {
        int hash = 5381;
        int len = sKey.length();
        char[] sKeyArr = sKey.toCharArray();
        for (int i = 0; i < len; i++) {
            hash += (hash << 5) + sKeyArr[i];
        }
        return hash & 2147483647;
    }

    /**
     * 解析PtUiCB
     *
     * @param ptUiCB 传入完整的ptUiCB字符串
     * @return 返回解析后的Map
     */
    public static Map<String, String> resolvePtUiCB(String ptUiCB) {
        Map<String, String> map = new HashMap<>();
        Matcher matcher = Pattern.compile("(\\'.*?\\')").matcher(ptUiCB);
        int matcher_start = 0;
        int temp = 0;
        while (matcher.find(matcher_start)) {
            map.put("param" + temp, matcher.group().replace("'", ""));
            temp++;
            matcher_start = matcher.end();
        }
        return map;
    }
}
