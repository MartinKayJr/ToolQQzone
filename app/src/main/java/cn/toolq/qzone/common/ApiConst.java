package cn.toolq.qzone.common;

import static cn.toolq.qzone.model.UserModel.uin;

import cn.toolq.qzone.model.UserModel;

/**
 * API常量
 */
public class ApiConst {

    public static final int MSG_NUM = 10;

    /**
     * 平台登录域名
     */
    public static final String PT_LOGIN_DOMAIN = "ssl.ptlogin2.qq.com";

    /**
     * C/C++预登录接口
     */
    public static final String CGI_BIN_XLOGIN = "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?proxy_url=https%3A//qzs.qq.com/qzone/v6/portal/proxy.html&daid=5&&hide_title_bar=1&low_login=0&qlogin_auto_login=1&no_verifyimg=1&link_target=blank&appid=549000912&style=22&target=self&s_url=https%3A%2F%2Fqzs.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&pt_qr_app=%E6%89%8B%E6%9C%BAQQ%E7%A9%BA%E9%97%B4&pt_qr_link=https%3A//z.qzone.com/download.html&self_regurl=https%3A//qzs.qq.com/qzone/v6/reg/index.html&pt_qr_help_link=https%3A//z.qzone.com/download.html&pt_no_auth=0";

    /**
     * 二维码显示接口
     */
    public static final String PT_QR_SHOW = "https://ssl.ptlogin2.qq.com/ptqrshow?appid=549000912&e=2&l=M&s=3&d=72&v=4&t=0.6652445662496691&daid=5&pt_3rd_aid=0";

    /**
     * 二维码登录接口
     */
    public static final String PT_QR_LOGIN = "https://ssl.ptlogin2.qq.com/ptqrlogin?";

    /**
     * C/C++说说列表接口 V6版本
     */
    public static final String CGI_BIN_MSG_LIST_V6 = "https://h5.qzone.qq.com/proxy/domain/taotao.qq.com/cgi-bin/emotion_cgi_msglist_v6?";

    /**
     * 用户头像接口
     */
    public static final String USER_AVATAR = "https://qlogo4.store.qq.com/qzone/";

    /**
     * user.qzone.qq.com
     */
    public static final String USER_DOMAIN = "https://user.qzone.qq.com/";

    /**
     * 二维码登录接口
     *
     * @param ptQrToken ptQrToken
     * @return 接口链接
     */
    public static String ptQrLogin(int ptQrToken) {
        return PT_QR_LOGIN + "u1=https%3A%2F%2Fqzs.qzone.qq.com%2Fqzone%2Fv5%2Floginsucc.html%3Fpara%3Dizone&ptqrtoken=" + ptQrToken + "&ptredirect=0&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=0-2-1632146800213&js_ver=21082415&js_type=1&login_sig=bBnR-tw6NM0PggPOMEP5MtPJRUvNlKUbTtwGYBg6pM6LzGwAok5tUjWSroxpwcah&pt_uistyle=40&aid=549000912&daid=5&ptdrvs=*oGDW1EqUOVl*Ooq9u1aUBLiuOhjRDZ9Xft7rqOgcdpU4V-Cd*jm4jCVc97jas5m&sid=7415381709577295073&has_onekey=1&";
    }

    /**
     * C/C++说说列表接口 V6版本
     *
     * @param position 位置
     * @param number   说说数量 (注意：接口限制最多40条)
     * @return 接口链接
     */
    public static String cgiBinMsgListV6(int position, int number) {
        long g_tk = QQUtils.getGTK(UserModel.sKey);
        // "https://user.qzone.qq.com/proxy/domain/taotao.qq.com/cgi-bin/emotion_cgi_msglist_v6?uin=" + uin + "&inCharset=utf-8&outCharset=utf-8&hostUin=" + uin + "&notice=0&sort=0&pos=" + position + "&num=" + number + "&cgi_host=https%3A%2F%2Fuser.qzone.qq.com%2Fproxy%2Fdomain%2Ftaotao.qq.com%2Fcgi-bin%2Femotion_cgi_msglist_v6&code_version=1&format=jsonp&need_private_comment=1&g_tk=" + g_tk + "&g_tk=" + g_tk;
        return CGI_BIN_MSG_LIST_V6 +
                // QQ号
                "uin=" + uin + "&" +
                // 编码
                "inCharset=" + "utf-8" + "&" +
                // QQ号
                "hostUin=" + uin + "&" +
                // notice
                "notice=" + 0 + "&" +
                // sor
                "sort=" + 0 + "&" +
                // 说说起始位置
                "pos=" + position + "&" +
                // 从起始位置开始读取多少 最多40
                "num=" + number + "&" +
                // cgi_host
                "cgi_host=" + "https%3A%2F%2Fuser.qzone.qq.com%2Fproxy%2Fdomain%2Ftaotao.qq.com%2Fcgi-bin%2Femotion_cgi_msglist_v6" + "&" +
                // 代码版本
                "code_version=" + 1 + "&" +
                // 格式化
                "format=" + "jsonp" + "&" +
                // 需要私有评论
                "need_private_comment=" + 1 + "&" +
                // g_tk
                "g_tk=" + g_tk;
    }

    /**
     * 用户头像接口
     *
     * @return 链接地址
     */
    public static String userAvatar() {
        return USER_AVATAR + uin + "/" + uin + "/100";
    }

    /**
     * 说说访问地址
     * @param tid 说说ID
     * @return 说说地址
     */
    public static String moodLink(String tid) {
        return USER_DOMAIN + uin + "/mood/" + tid;
    }
}
