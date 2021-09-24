package cn.toolq.qzone.fragment.other;

import android.view.View;

import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.widget.actionbar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import cn.toolq.qzone.R;
import cn.toolq.qzone.activity.MainActivity;
import cn.toolq.qzone.base.BaseFragment;
import cn.toolq.qzone.common.GlobalObject;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

@Page(name = "我的说说")
public class MyMsgFragment extends BaseFragment {

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

    @Override
    protected TitleBar initTitle() {
        TitleBar titleBar = super.initTitle();
        titleBar.setLeftImageResource(R.drawable.ic_action_menu);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            @SingleClick
            public void onClick(View v) {
                getContainer().openMenu();
            }
        });
        return titleBar;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_msg;
    }

    @Override
    protected void initArgs() {
        super.initArgs();

    }

    @Override
    protected void initViews() {

    }

    public MainActivity getContainer() {
        return (MainActivity) getActivity();
    }


}
