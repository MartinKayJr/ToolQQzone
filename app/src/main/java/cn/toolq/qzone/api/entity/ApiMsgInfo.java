package cn.toolq.qzone.api.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class ApiMsgInfo {
    /**
     * 说说id
     */
    private String tid;
    /**
     * 有时候qq签名会同步为说说，设置为仅自己可见时该字段为1
     */
    private int secret;
    /**
     * 创建时间
     */
    @JSONField(name = "createTime")
    private String createTime;
    /**
     * 发布平台
     */
    private String source_name;
    /**
     * 说说内容
     */
    private String content;
    /**
     * 图片总数
     */
    private int pictotal;
    /**
     * 评论数量
     */
    private int cmtnum;
    /**
     * 转发数量
     */
    private int fwdnum;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public int getSecret() {
        return secret;
    }

    public void setSecret(int secret) {
        this.secret = secret;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPictotal() {
        return pictotal;
    }

    public void setPictotal(int pictotal) {
        this.pictotal = pictotal;
    }

    public int getCmtnum() {
        return cmtnum;
    }

    public void setCmtnum(int cmtnum) {
        this.cmtnum = cmtnum;
    }

    public int getFwdnum() {
        return fwdnum;
    }

    public void setFwdnum(int fwdnum) {
        this.fwdnum = fwdnum;
    }
}
