package cn.toolq.qzone;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.mikepenz.iconics.Iconics;
import com.xuexiang.xormlite.annotation.DataBase;
import com.xuexiang.xormlite.enums.DataBaseType;
import com.xuexiang.xui.XUI;

import cn.toolq.qzone.widget.iconfont.XUIIconFont;
import cn.toolq.qzone.xui.utils.SettingSPUtils;
import cn.toolq.qzone.xui.utils.sdkinit.TbsInit;
import cn.toolq.qzone.xui.utils.sdkinit.XBasicLibInit;
import cn.toolq.qzone.xui.utils.sdkinit.XUpdateInit;


/**
 * 应用初始化
 */
@DataBase(name = "TQzone", type = DataBaseType.INTERNAL)
public class MyApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决4.x运行崩溃的问题
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化基础库
        XBasicLibInit.init(this);
        initUI();
        //三方SDK初始化
        XUpdateInit.init(this);
        TbsInit.init(this);
    }

    /**
     * 初始化XUI 框架
     */
    private void initUI() {
        XUI.debug(MyApp.isDebug());
        if (SettingSPUtils.getInstance().isUseCustomFont()) {
            //设置默认字体为华文行楷
            XUI.initFontStyle("fonts/hwxk.ttf");
        }

        //字体图标库
        Iconics.init(this);
        //这是自己定义的图标库
        Iconics.registerFont(new XUIIconFont());

    }


    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }


}
