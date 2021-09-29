package cn.toolq.qzone.api.entity;

/**
 * 说说图片信息
 */
public class ApiMsgPicInfo {
    /**
     * 图片位置（方格位置）
     */
    private int absolute_position;
    /**
     * 图片地址
     */
    private String smallurl;
    /**
     * 图片高度
     */
    private int height;
    /**
     * 图片宽度
     */
    private int width;

    public int getAbsolute_position() {
        return absolute_position;
    }

    public void setAbsolute_position(int absolute_position) {
        this.absolute_position = absolute_position;
    }

    public String getSmallurl() {
        return smallurl;
    }

    public void setSmallurl(String smallurl) {
        this.smallurl = smallurl;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
