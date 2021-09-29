package cn.toolq.qzone.api.entity;

import java.util.List;

/**
 * 说说分页
 */
public class ApiMsgPage {
    /**
     * 说说列表
     */
    private List<ApiMsgInfo> msglist;
    /**
     * 读取数量
     */
    private int num;
    /**
     * 总数量
     */
    private int total;

    public List<ApiMsgInfo> getMsglist() {
        return msglist;
    }

    public void setMsglist(List<ApiMsgInfo> msglist) {
        this.msglist = msglist;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
