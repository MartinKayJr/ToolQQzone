package cn.toolq.qzone.activity;

import android.view.KeyEvent;

import com.xuexiang.xui.utils.KeyboardUtils;
import com.xuexiang.xui.widget.activity.BaseSplashActivity;
import com.xuexiang.xutil.app.ActivityUtils;

import cn.toolq.qzone.R;
import cn.toolq.qzone.xui.utils.SettingSPUtils;
import cn.toolq.qzone.xui.utils.TokenUtils;

public class SplashActivity extends BaseSplashActivity {

    public final static String KEY_IS_DISPLAY = "key_is_display";
    public final static String KEY_ENABLE_ALPHA_ANIM = "key_enable_alpha_anim";

    private boolean isDisplay = false;

    @Override
    protected long getSplashDurationMillis() {
        return 500;
    }

    @Override
    public void onCreateActivity() {
        isDisplay = getIntent().getBooleanExtra(KEY_IS_DISPLAY, isDisplay);
        boolean enableAlphaAnim = getIntent().getBooleanExtra(KEY_ENABLE_ALPHA_ANIM, false);
        SettingSPUtils spUtil = SettingSPUtils.getInstance();
        if (spUtil.isFirstOpen()) {
            spUtil.setIsFirstOpen(false);
            ActivityUtils.startActivity(UserGuideActivity.class);
            finish();

        } else {
            if (enableAlphaAnim) {
                initSplashView(R.drawable.qzone_logo);
            } else {
                initSplashView(R.drawable.toolq_config_bg_splash);
            }
            startSplash(enableAlphaAnim);
        }
    }

    @Override
    public void onSplashFinished() {
        if (!isDisplay) {
            if (TokenUtils.hasToken()) {
                ActivityUtils.startActivity(MainActivity.class);
            } else {
                ActivityUtils.startActivity(QzoneLoginActivity.class);
            }
        }
        finish();
    }

    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return KeyboardUtils.onDisableBackKeyDown(keyCode) && super.onKeyDown(keyCode, event);
    }

}