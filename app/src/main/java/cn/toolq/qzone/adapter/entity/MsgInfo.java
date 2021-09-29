package cn.toolq.qzone.adapter.entity;

import androidx.annotation.NonNull;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 说说信息
 */
public class MsgInfo implements Cloneable {

    private static AtomicLong sAtomicLong = new AtomicLong();


    private long ID;

    private String tid;
    /**
     * 用户名
     */
    private String CreateTime;
    /**
     * 标签
     */
    private String Tag;
    /**
     * 标题
     */
    private String Title;
    /**
     * 摘要
     */
    private String Summary;

    /**
     * 图片
     */
    private String ImageUrl;
    /**
     * 点赞数
     */
    private int Praise;
    /**
     * 评论数
     */
    private int Comment;
    /**
     * 阅读量
     */
    private int Read;
    /**
     * 新闻的详情地址
     */
    private String DetailUrl;


    public MsgInfo() {

    }



    public MsgInfo(String createTime, String tag, String title, String summary, String imageUrl, int praise, int comment, int read, String detailUrl) {
        CreateTime = createTime;
        Tag = tag;
        Title = title;
        Summary = summary;
        ImageUrl = imageUrl;
        Praise = praise;
        Comment = comment;
        Read = read;
        DetailUrl = detailUrl;
    }


    public MsgInfo(String tag, String title, String summary, String imageUrl, String detailUrl) {
        Tag = tag;
        Title = title;
        Summary = summary;
        ImageUrl = imageUrl;
        DetailUrl = detailUrl;
    }


    public MsgInfo(String tag, String title) {
        ID = sAtomicLong.incrementAndGet();
        Tag = tag;
        Title = title;

        Praise = (int) (Math.random() * 100 + 5);
        Comment = (int) (Math.random() * 50 + 5);
        Read = (int) (Math.random() * 500 + 50);
    }

    public MsgInfo resetContent() {
        Praise = (int) (Math.random() * 100 + 5);
        Comment = (int) (Math.random() * 50 + 5);
        Read = (int) (Math.random() * 500 + 50);
        return this;
    }

    public MsgInfo setID(long ID) {
        this.ID = ID;
        return this;
    }

    public long getID() {
        return ID;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public MsgInfo setCreateTime(String createTime) {
        CreateTime = createTime;
        return this;
    }

    public String getTag() {
        return Tag;
    }

    public MsgInfo setTag(String tag) {
        Tag = tag;
        return this;
    }

    public String getTitle() {
        return Title;
    }

    public MsgInfo setTitle(String title) {
        Title = title;
        return this;
    }

    public String getSummary() {
        return Summary;
    }

    public MsgInfo setSummary(String summary) {
        Summary = summary;
        return this;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public MsgInfo setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
        return this;
    }

    public int getPraise() {
        return Praise;
    }

    public MsgInfo setPraise(int praise) {
        Praise = praise;
        return this;
    }

    public int getComment() {
        return Comment;
    }

    public MsgInfo setComment(int comment) {
        Comment = comment;
        return this;
    }

    public int getRead() {
        return Read;
    }

    public MsgInfo setRead(int read) {
        Read = read;
        return this;
    }

    public String getDetailUrl() {
        return DetailUrl;
    }

    public MsgInfo setDetailUrl(String detailUrl) {
        DetailUrl = detailUrl;
        return this;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @NonNull
    @Override
    public String toString() {
        return "MsgInfo{" +
                "UserName='" + CreateTime + '\'' +
                ", Tag='" + Tag + '\'' +
                ", Title='" + Title + '\'' +
                ", Summary='" + Summary + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                ", Praise=" + Praise +
                ", Comment=" + Comment +
                ", Read=" + Read +
                ", DetailUrl='" + DetailUrl + '\'' +
                '}';
    }

    @NonNull
    @Override
    public MsgInfo clone() {
        try {
            return (MsgInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new MsgInfo();
    }
}
