package com.whl.hp.baidumusic.bean;

/**
 * Created by hp-whl on 2015/9/20.
 */
public class Channel {
    private String name;
    private String channelid;
    private String thumb;
    private String ch_name;
    private int value;
    private String cate_name;
    private String cate_sname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getCh_name() {
        return ch_name;
    }

    public void setCh_name(String ch_name) {
        this.ch_name = ch_name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getCate_sname() {
        return cate_sname;
    }

    public void setCate_sname(String cate_sname) {
        this.cate_sname = cate_sname;
    }
}
