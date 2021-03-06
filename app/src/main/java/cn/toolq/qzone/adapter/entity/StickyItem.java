/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package cn.toolq.qzone.adapter.entity;

/**
 * @author xuexiang
 * @since 2020/4/25 1:25 AM
 */
public class StickyItem {

    /**
     * 是否顶部粘连
     */
    private boolean mIsHeadSticky;
    /**
     * 顶部标题
     */
    private String mHeadTitle;

    /**
     * 新闻信息
     */
    private MsgInfo mMsgInfo;


    public StickyItem(String headTitle) {
        mHeadTitle = headTitle;
        mIsHeadSticky = true;
    }

    public StickyItem(MsgInfo msgInfo) {
        mMsgInfo = msgInfo;
        mIsHeadSticky = false;
    }

    public boolean isHeadSticky() {
        return mIsHeadSticky;
    }

    public String getHeadTitle() {
        return mHeadTitle;
    }

    public StickyItem setHeadTitle(String headTitle) {
        mHeadTitle = headTitle;
        return this;
    }

    public MsgInfo getMsgInfo() {
        return mMsgInfo;
    }

    public StickyItem setMsgInfo(MsgInfo msgInfo) {
        mMsgInfo = msgInfo;
        return this;
    }
}
