package cn.toolq.qzone.model;

/**
 * 说说信息
 */
public class MoodInfoModel {
    /**
     * 说说ID
     */
    private String moodId;
    /**
     * 发布者
     */
    private long uin;
    /**
     * 发布者昵称
     */
    private String nickname;
    /**
     * 有时候qq签名会同步为说说，设置为仅自己可见时该字段为1
     */
    private int secret;
    /**
     * 发布时间
     */
    private String publishTime;
    /**
     * 发布平台
     */
    private String sourceName;
    /**
     * 说说内容
     */
    private String content;
    /**
     * 图片总数
     */
    private int picTotal;
    /**
     * 评论总数
     */
    private int cmtNum;
    /**
     * 转发总数
     */
    private int fwdNum;
    /**
     * 地理位置
     */
    private String locate;
    /**
     * 地理位置
     */
    private String position;
    /**
     * 经纬度
     */
    private String posX;
    /**
     * 经纬度
     */
    private String posY;
}
