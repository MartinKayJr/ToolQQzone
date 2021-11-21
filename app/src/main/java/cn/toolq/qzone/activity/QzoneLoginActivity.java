package cn.toolq.qzone.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.common.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.toolq.qzone.base.BaseActivity;
import cn.toolq.qzone.common.ApiConst;
import cn.toolq.qzone.common.CookieKeyConst;
import cn.toolq.qzone.common.CookieUtils;
import cn.toolq.qzone.common.GlobalObject;
import cn.toolq.qzone.common.QQUtils;
import cn.toolq.qzone.databinding.ActivityQzoneLoginBinding;
import cn.toolq.qzone.model.UserModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * QQ空间登录
 */
public class QzoneLoginActivity extends BaseActivity {

    private ActivityQzoneLoginBinding binding;

    private ImageView imageQr;

    private TextView textStatus;

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .followRedirects(false)  //禁制OkHttp的重定向操作，我们自己处理重定向
            .cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                    GlobalObject.cookieStore.put(httpUrl.host(), list);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                    List<Cookie> cookies = GlobalObject.cookieStore.get(httpUrl.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            .build();

    private MyHandler handler = new MyHandler(this);
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQzoneLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化视图组件
        initViews();
        initQr();
        initEvent();
    }

    private void initViews() {
        imageQr = binding.imageQr;
        textStatus = binding.textStatus;
    }

    private void initEvent() {
        imageQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initQr();
            }
        });
    }

    /**
     * 初始化二维码
     */
    private void initQr() {
        //https://xui.ptlogin2.qq.com/cgi-bin/xlogin?pt_disable_pwd=1&appid=1006102&daid=1&style=23&hide_border=1&proxy_url=https://id.qq.com%2Flogin%2Fproxy.html&s_url=https://id.qq.com/index.html
        //https://xui.ptlogin2.qq.com/cgi-bin/xlogin?pt_disable_pwd=1&appid=715030901&daid=73&hide_close_icon=1&pt_no_auth=1&s_url=https%3A%2F%2Fqun.qq.com%2Fmember.html%23
        // QQ群的链接
        // https://xui.ptlogin2.qq.com/cgi-bin/xlogin?pt_disable_pwd=1&appid=715030901&daid=73&hide_close_icon=1&pt_no_auth=1&s_url=https%3A%2F%2Fqun.qq.com%2Fmember.html%23
        // QQ空间的链接
        //https://xui.ptlogin2.qq.com/cgi-bin/xlogin?proxy_url=https%3A//qzs.qq.com/qzone/v6/portal/proxy.html&daid=5&&hide_title_bar=1&low_login=0&qlogin_auto_login=1&no_verifyimg=1&link_target=blank&appid=549000912&style=22&target=self&s_url=https%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&pt_qr_app=%E6%89%8B%E6%9C%BAQQ%E7%A9%BA%E9%97%B4&pt_qr_link=https%3A//z.qzone.com/download.html&self_regurl=https%3A//qzs.qq.com/qzone/v6/reg/index.html&pt_qr_help_link=https%3A//z.qzone.com/download.html&pt_no_auth=0
        Request initRequest = new Request.Builder()
                .url(ApiConst.CGI_BIN_XLOGIN)
                .get()//默认就是GET请求，可以不写
                .build();
        Call initCall = okHttpClient.newCall(initRequest);
        initCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //https://ssl.ptlogin2.qq.com/ptqrshow?appid=1006102&e=2&l=M&s=3&d=72&v=4&t=0.5518777591223158&daid=1&pt_3rd_aid=0
                //https://ssl.ptlogin2.qq.com/ptqrshow?appid=715030901&e=2&l=M&s=3&d=72&v=4&t=0.41124352311620327&daid=73&pt_3rd_aid=0

                // QQ群的二维码链接
                // https://ssl.ptlogin2.qq.com/ptqrshow?appid=715030901&e=2&l=M&s=3&d=72&v=4&t=0.41124352311620327&daid=73&pt_3rd_aid=0
                // QQ空间的二维码链接
                // https://ssl.ptlogin2.qq.com/ptqrshow?appid=549000912&e=2&l=M&s=3&d=72&v=4&t=0.6652445662496691&daid=5&pt_3rd_aid=0
                final Request request = new Request.Builder()
                        .url(ApiConst.PT_QR_SHOW)
                        .get()//默认就是GET请求，可以不写
                        .build();
                Call ptQrCall = okHttpClient.newCall(request);
                ptQrCall.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        InputStream inputStream = response.body().byteStream();//得到图片的流
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                        if (thread == null) {
                            thread = new Thread() {
                                @Override
                                public void run() {
                                    super.run();
                                    while (true) {
                                        try {
                                            int i = loadQrMonitor();
                                            if (i == 1) {
                                                break;
                                            }
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    Log.e("TAG", "监测二维码状态线程结束");
                                }
                            };
                            thread.start();
                        }
                    }
                });
            }
        });
    }

    /**
     * 监测二维码状态
     *
     * @return 是否跳出监测
     */
    private int loadQrMonitor() {
        String qrsig = CookieUtils.getCookieValue(GlobalObject.cookieStore, "ssl.ptlogin2.qq.com", "qrsig");
        if (qrsig != null) {
            int ptQrToken = QQUtils.getPtQrToken(qrsig);
            //https://ssl.ptlogin2.qq.com/ptqrlogin?u1=https%3A%2F%2Fid.qq.com%2Findex.html&ptqrtoken=" + ptQrToken + "&ptredirect=1&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=0-0-1576722506346&js_ver=19112817&js_type=1&login_sig=&pt_uistyle=40&aid=1006102&daid=1&
            //https://ssl.ptlogin2.qq.com/ptqrlogin?u1=https%3A%2F%2Fqun.qq.com%2Fmember.html%23&ptqrtoken=259759073&ptredirect=1&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=2-0-1599983790537&js_ver=10000&js_type=1&login_sig=*-gF8QsOmPrh3FCzy7sA1cvxlPawFLjecfMP*51I*LARXWTFnupWKByid6AUHCRy&pt_uistyle=40&aid=715030901&daid=73&has_onekey=1&

            // QQ群的监听链接
            // https://ssl.ptlogin2.qq.com/ptqrlogin?u1=https%3A%2F%2Fqun.qq.com%2Fmember.html%23&ptqrtoken=" + ptQrToken + "&ptredirect=1&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=2-0-1599983790537&js_ver=10000&js_type=1&login_sig=&pt_uistyle=40&aid=715030901&daid=73&has_onekey=1&
            // QQ空间的监听链接
            // https://ssl.ptlogin2.qq.com/ptqrlogin?u1=https%3A%2F%2Fqzs.qzone.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&ptqrtoken=1348560235&ptredirect=0&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=0-2-1632146800213&js_ver=21082415&js_type=1&login_sig=bBnR-tw6NM0PggPOMEP5MtPJRUvNlKUbTtwGYBg6pM6LzGwAok5tUjWSroxpwcah&pt_uistyle=40&aid=549000912&daid=5&ptdrvs=*oGDW1EqUOVl*Ooq9u1aUBLiuOhjRDZ9Xft7rqOgcdpU4V-Cd*jm4jCVc97jas5m&sid=7415381709577295073&has_onekey=1&

            final Request request = new Request.Builder()
                    .url(ApiConst.ptQrLogin(ptQrToken))
                    .get()//默认就是GET请求，可以不写
                    .build();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //                0 = {Cookie@4582} "pt2gguin=; expires=Thu, 01 Jan 1970 00:00:00 GMT; domain=qq.com; path=/"
//                        1 = {Cookie@4583} "pt2gguin=o2250635418; expires=Tue, 19 Jan 2038 03:14:07 GMT; domain=ptlogin2.qq.com; path=/; secure"
//                        2 = {Cookie@4584} "ETK=; domain=ptlogin2.qq.com; path=/; secure"
//                        3 = {Cookie@4585} "uin=o2250635418; domain=qq.com; path=/"
//                        4 = {Cookie@4586} "skey=@3gvJKv5uM; domain=qq.com; path=/"
//                        5 = {Cookie@4587} "superuin=o2250635418; domain=ptlogin2.qq.com; path=/; secure"
//                        6 = {Cookie@4588} "supertoken=3915497265; domain=ptlogin2.qq.com; path=/; secure"
//                        7 = {Cookie@4589} "superkey=7S*fQQOwr6LZx340OB1p3CbrLjTE*NWopDrv86qb**Y_; domain=ptlogin2.qq.com; path=/; secure; httponly"
//                        8 = {Cookie@4590} "pt_recent_uins=633fcbe1abcc682cc2ba03469a194892295dd3becf7c67db93851cff71cddbbbbaf5a6f3308f6b4642f3ce0908d0f42c1c4dc1700c320882; expires=Sun, 24 Oct 2021 08:55:29 GMT; domain=ptlogin2.qq.com; path=/; secure; httponly"
//                        9 = {Cookie@4591} "RK=mVg1DRyflt; expires=Tue, 19 Jan 2038 03:14:07 GMT; domain=qq.com; path=/"
//                        10 = {Cookie@4592} "ptnick_2250635418=4d722e204b617920; domain=ptlogin2.qq.com; path=/; secure"
//                        11 = {Cookie@4593} "ptcz=4015dca4bbea478ff4bfc679e5d1f282795ecf023df51f0ac53c4377b229b35c; expires=Tue, 19 Jan 2038 03:14:07 GMT; domain=qq.com; path=/"
//                        12 = {Cookie@4594} "ptcz=; expires=Thu, 01 Jan 1970 00:00:00 GMT; domain=ptlogin2.qq.com; path=/; secure"
//                        13 = {Cookie@4595} "airkey=; expires=Thu, 01 Jan 1970 00:00:00 GMT; domain=qq.com; path=/"
//
                    String data = response.body().string();
                    String skey = CookieUtils.getCookieValue(GlobalObject.cookieStore, ApiConst.PT_LOGIN_DOMAIN, CookieKeyConst.S_KEY);
                    Log.e("TAG", "二维码状态：" + data + "cookie:" + skey);
                    Map<String, String> map = QQUtils.resolvePtUiCB(data);
                    String paramStatus = map.get("param0");
                    if (paramStatus != null) {
                        switch (Integer.parseInt(paramStatus)) {
                            case 0:
                                String loginUrl = map.get("param2");
                                Request loginRequest = new Request.Builder()
                                        .url(loginUrl)
                                        .get()//默认就是GET请求，可以不写
                                        .build();
                                Call loginCall = okHttpClient.newCall(loginRequest);
                                loginCall.enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        // 登录后的代码
                                        loginSuccess(map);
                                    }
                                });
                                break;
                            case 66:
                            case 67:
                            case 68:
                                break;
                        }
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = map.get("param4") + "(" + map.get("param5") + ")";
                        handler.sendMessage(msg);
                    }
                }
            });
        } else {
            return 1;
        }
        return 0;
    }

    private void loginSuccess(Map<String, String> map) {
        String sKey = CookieUtils.getCookieValue(GlobalObject.cookieStore, ApiConst.PT_LOGIN_DOMAIN, CookieKeyConst.S_KEY);
        String uinPrefixO = CookieUtils.getCookieValue(GlobalObject.cookieStore, ApiConst.PT_LOGIN_DOMAIN, CookieKeyConst.UIN);
        if (!StringUtils.isEmpty(uinPrefixO)) {
            UserModel.uin = Long.parseLong(uinPrefixO.replace("o", ""));
        }
        UserModel.userName = map.get("param5");
        UserModel.sKey = sKey;
        if (!StringUtils.isEmpty(sKey)) {
            UserModel.gTk = QQUtils.getGTK(sKey);
        }
        CookieUtils.saveCookie(GlobalObject.cookieStore);
        ActivityUtils.startActivity(MainActivity.class);
        finish();
    }

    private static class MyHandler extends Handler {
        private WeakReference<QzoneLoginActivity> wr;

        public MyHandler(QzoneLoginActivity mla) {
            wr = new WeakReference<>(mla);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            QzoneLoginActivity mla = wr.get();
            switch (msg.what) {
                case 0:
                    //初始化二维码
                    Bitmap bitmap = (Bitmap) msg.obj;
                    mla.imageQr.setImageBitmap(bitmap);//将图片的流转换成图片
                    break;
                case 1:
                    //二维码状态
                    String status = ((String) msg.obj).replaceAll("\\([^)]*\\)", "");
                    mla.textStatus.setText(status);
                    break;
                case 2:
                    mla.textStatus.setText((String) msg.obj);
                    break;
            }

        }
    }
}