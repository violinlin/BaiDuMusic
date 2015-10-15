package com.whl.hp.baidumusic.tool;

/**
 * Created by hp-whl on 2015/9/20.
 */
public class Config {
    public static final String RADIO_URL="http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0" +
            "&method=baidu.ting.radio.getCategoryList&format=json";
    public static final String URL_NAME="url";

    public static final String MUSICLIST_URL="http://tingapi.ting.baidu.com/v1/restserver/ting?from=qianqian&version=2.1.0" +
            "&method=baidu.ting.radio.getChannelSong&format=json" +
            "&pn=0&rn=10&channelname=%s";
    public static final String MUSICID_URL="http://ting.baidu.com/data/music/links?songIds=%s";
    public static final String CHANEL_NAME="chanelName";
    public static final String CHANEL_ACTION="com.whl.chanel";
    public static final String MUSIC_ACTION="com.whl.music";
    public static final String MUSIC_ID="musicName";
    public static final String SERVICE_ACTION="com.whl.service";
    public static final String PLAY_ACTION="com.whl.play";

    public static final String HEAD_URL="http://ting.baidu.com%s";
}
