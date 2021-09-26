package cn.toolq.qzone.fragment.other;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xuexiang.xaop.annotation.SingleClick;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xui.utils.ViewUtils;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xutil.net.JSONUtils;
import com.xuexiang.xutil.net.JsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.toolq.qzone.R;
import cn.toolq.qzone.activity.MainActivity;
import cn.toolq.qzone.adapter.entity.MsgInfo;
import cn.toolq.qzone.api.entity.ApiMsgInfo;
import cn.toolq.qzone.api.entity.ApiMsgPage;
import cn.toolq.qzone.base.BaseFragment;
import cn.toolq.qzone.common.ApiConst;
import cn.toolq.qzone.common.GlobalObject;
import cn.toolq.qzone.fragment.components.MsgListOptionAdapter;
import cn.toolq.qzone.xui.utils.Utils;
import cn.toolq.qzone.xui.utils.XToastUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Page(name = "我的说说")
public class MyMsgFragment extends BaseFragment {

    private MyMsgHandler myMsgHandler = new MyMsgHandler(this);

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MsgListOptionAdapter mAdapter;

    @BindView(R.id.fl_edit)
    FrameLayout flEdit;
    @BindView(R.id.scb_select_all)
    SmoothCheckBox scbSelectAll;

    private TextView mTvSwitch;

    private int currentPosition = 0;
    private int num = ApiConst.MSG_NUM;

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_msg;
    }

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
        mTvSwitch = (TextView) titleBar.addAction(new TitleBar.TextAction(getString(R.string.title_enter_manage_mode)) {
            @SingleClick
            @Override
            public void performAction(View view) {
                if (mAdapter == null) {
                    return;
                }
                mAdapter.switchManageMode();
                refreshManageMode();
            }
        });
        return titleBar;
    }

    private void refreshManageMode() {
        if (mTvSwitch != null) {
            mTvSwitch.setText(mAdapter.isManageMode() ? R.string.title_exit_manage_mode : R.string.title_enter_manage_mode);
        }
        ViewUtils.setVisibility(flEdit, mAdapter.isManageMode());
    }

    @Override
    protected void initArgs() {
        super.initArgs();

    }

    @Override
    protected void initViews() {
        WidgetUtils.initRecyclerView(recyclerView, 0);
        recyclerView.setAdapter(mAdapter = new MsgListOptionAdapter(isSelectAll -> {
            if (scbSelectAll != null) {
                scbSelectAll.setCheckedSilent(isSelectAll);
            }
        }));
        scbSelectAll.setOnCheckedChangeListener((checkBox, isChecked) -> mAdapter.setSelectAll(isChecked));
    }

    @Override
    protected void initListeners() {
        //下拉刷新
        refreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            currentPosition = 0;
            getMoodPage(currentPosition, num);
        }, 1000));
        //上拉加载
        refreshLayout.setOnLoadMoreListener(refreshLayout -> refreshLayout.getLayout().postDelayed(() -> {
            currentPosition += num;
            getMoodPage(currentPosition, num);
        }, 1000));
        refreshLayout.autoRefresh();//第一次进入触发自动刷新，演示效果

        mAdapter.setOnItemClickListener((itemView, item, position) -> {
            if (mAdapter.isManageMode()) {
                mAdapter.updateSelectStatus(position);
            } else {
                Utils.goWeb(getContext(), item.getDetailUrl());
            }
        });
        mAdapter.setOnItemLongClickListener((itemView, item, position) -> {
            if (!mAdapter.isManageMode()) {
                mAdapter.enterManageMode(position);
                refreshManageMode();
            }
        });
    }

    public ApiMsgPage getMoodPage(int position, int num) {
        Request getMoodPageRequest = new Request.Builder()
                .url(ApiConst.cgiBinMsgListV6(position, num))
                .get()//默认就是GET请求，可以不写
                .build();
        Call getMoodPageCall = okHttpClient.newCall(getMoodPageRequest);
        getMoodPageCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                data = data.replace("_Callback(", "").replace(");", "");
                ApiMsgPage apiMsgPage = JSONObject.parseObject(data, ApiMsgPage.class);
                if (apiMsgPage != null) {
                    List<ApiMsgInfo> msgList = apiMsgPage.getMsglist();
                    if (msgList != null && msgList.size() > 0) {
                        ArrayList<MsgInfo> msgInfoList = new ArrayList<>();
                        for (ApiMsgInfo apiMsgInfo : msgList) {
                            MsgInfo msgInfo = new MsgInfo();
                            msgInfo.setTid(apiMsgInfo.getTid());
                            msgInfo.setCreateTime(apiMsgInfo.getCreateTime());
                            msgInfo.setTag(apiMsgInfo.getSource_name());
                            msgInfo.setSummary(apiMsgInfo.getContent());
                            msgInfo.setImageUrl(apiMsgInfo.getContent());
                            msgInfo.setPraise(0);
                            msgInfo.setComment(apiMsgInfo.getCmtnum());
                            msgInfo.setRead(0);
                            msgInfo.setDetailUrl(ApiConst.moodLink(apiMsgInfo.getTid()));
                            msgInfoList.add(msgInfo);
                        }
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = msgInfoList;
                        myMsgHandler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = 1;
                        myMsgHandler.sendMessage(msg);
                    }
                }
            }
        });
        return null;
    }

    @SingleClick
    @OnClick(R.id.btn_submit)
    public void onViewClicked(View view) {
        XToastUtils.toast("选中了" + mAdapter.getSelectedIndexList().size() + "个选项！");

    }

    public MainActivity getContainer() {
        return (MainActivity) getActivity();
    }


    private static class MyMsgHandler extends Handler {

        private WeakReference<MyMsgFragment> wr;

        public MyMsgHandler(MyMsgFragment mmf) {
            wr = new WeakReference<>(mmf);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            MyMsgFragment mmf = wr.get();
            switch (msg.what) {
                case 0:
                    List<MsgInfo> msgInfoList = (ArrayList<MsgInfo>) msg.obj;
                    // 如果当前读取起始位置大于1，则说明已经至少读取过1次，采用增加数据的发送
                    if (mmf.currentPosition > 1) {
                        mmf.mAdapter.loadMore(msgInfoList);
                        mmf.refreshLayout.finishLoadMore();
                    } else {
                        mmf.mAdapter.refresh(msgInfoList);
                        mmf.refreshLayout.finishRefresh();
                    }
                    break;
                case 1:
                    XToastUtils.toast("说说全部加载完毕");
                    mmf.refreshLayout.finishLoadMoreWithNoMoreData();
                    break;
            }
        }
    }

}
