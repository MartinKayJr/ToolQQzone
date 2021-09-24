package cn.toolq.qzone.activity;

import android.app.Activity;

import com.xuexiang.xui.widget.activity.BaseGuideActivity;

import java.util.List;

import cn.toolq.qzone.common.DataProvider;

/**
 * 启动引导页
 */
public class UserGuideActivity extends BaseGuideActivity {
    @Override
    protected List<Object> getGuideResourceList() {
        return DataProvider.getUserGuides();
    }

    @Override
    protected Class<? extends Activity> getSkipClass() {
        return QzoneLoginActivity.class;
    }

}
