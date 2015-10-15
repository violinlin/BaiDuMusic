package com.whl.hp.baidumusic.tool;

import android.util.Log;

/**
 * Created by hp-whl on 2015/9/20.
 */
public class Debug {

    public static boolean isDebug=false;
    public static void startDebug(String tag,String content){
        if (isDebug){
            Log.d(tag,content);
        }
    }
}
