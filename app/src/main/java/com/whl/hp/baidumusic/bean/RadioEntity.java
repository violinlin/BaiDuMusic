package com.whl.hp.baidumusic.bean;

/**
 * Created by hp-whl on 2015/9/20.
 */
//电台实体咧
public class RadioEntity {
    private String title;
    private int cid;
    private Channel channellist;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public Channel getChannellist() {
        return channellist;
    }

    public void setChannellist(Channel channellist) {
        this.channellist = channellist;
    }
}
