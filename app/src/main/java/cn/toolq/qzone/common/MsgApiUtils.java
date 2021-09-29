package cn.toolq.qzone.common;

import java.util.ArrayList;
import java.util.List;

import cn.toolq.qzone.model.UserModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 说说接口工具类
 */
public class MsgApiUtils {

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                    GlobalObject.cookieStore.put(httpUrl.host(), list);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                    List<Cookie> cookies = GlobalObject.cookieStore.get("ptlogin2.qzone.qq.com");
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            .build();

    /**
     * 获取说说列表
     *
     * @param position 读取起始位置
     * @param number   读取数量
     * @param callback 回调
     */
    public static void cgiBinMsgListV6(int position, int number, Callback callback) {
        Request getMoodPageRequest = new Request.Builder()
                .url(ApiConst.getCgiBinMsgListV6Api(position, number))
                .get()//默认就是GET请求，可以不写
                .build();
        Call getMoodPageCall = okHttpClient.newCall(getMoodPageRequest);
        getMoodPageCall.enqueue(callback);
    }

    /**
     * 删除说说
     *
     * @param tid      说说ID
     * @param callback 回调
     */
    public static void cgiBinMsgDeleteV6(String tid, Callback callback) {
        FormBody formBody = new FormBody.Builder()
                .add("hostuin", UserModel.uin + "")
                .add("tid", tid)
                .add("t1_source", "1")
                .add("code_version", "1")
                .add("format", "fs")
                .add("qzreferrer", ApiConst.getQzReferrer())
                .build();
        Request deleteMoodRequest = new Request.Builder()
                .url(ApiConst.getCgiBinMsgDeleteV6Api())
                .post(formBody)//默认就是GET请求，可以不写
                .build();
        Call deleteMoodCall = okHttpClient.newCall(deleteMoodRequest);
        deleteMoodCall.enqueue(callback);
    }
}
