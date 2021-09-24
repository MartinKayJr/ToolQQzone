package cn.toolq.qzone.common;

import com.xuexiang.xaop.annotation.MemoryCache;

import java.util.ArrayList;
import java.util.List;

import cn.toolq.qzone.R;
import cn.toolq.qzone.adapter.entity.NewInfo;

/**
 * 数据提供者
 */
public class DataProvider {

    public static String[] urls = new String[]{//640*360 360/640=0.5625
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160323071011277.jpg",//伪装者:胡歌演绎"痞子特工"
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144158380433341332.jpg",//无心法师:生死离别!月牙遭虐杀
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144160286644953923.jpg",//花千骨:尊上沦为花千骨
            "http://photocdn.sohu.com/tvmobilemvms/20150902/144115156939164801.jpg",//综艺饭:胖轩偷看夏天洗澡掀波澜
            "http://photocdn.sohu.com/tvmobilemvms/20150907/144159406950245847.jpg",//碟中谍4:阿汤哥高塔命悬一线,超越不可能
    };

    public static List<Object> getUserGuides() {
        List<Object> list = new ArrayList<>();
        list.add(R.drawable.guide_img_1);
        list.add(R.drawable.guide_img_2);
        list.add(R.drawable.guide_img_3);
        list.add(R.drawable.guide_img_4);
        return list;
    }

    /**
     * 用于占位的空信息
     *
     * @return
     */
    @MemoryCache
    public static List<NewInfo> getEmptyNewInfo() {
        List<NewInfo> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new NewInfo());
        }
        return list;
    }
}
