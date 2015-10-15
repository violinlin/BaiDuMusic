package com.whl.hp.baidumusic;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.whl.hp.baidumusic.tool.Config;
import com.whl.hp.baidumusic.tool.Debug;

import java.io.IOException;

/**
 * Created by hp-whl on 2015/9/20.
 */
public class MusicService extends Service {

    MediaPlayer mediaPlayer;
    int current = 0;

    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        registerReceiver(new PrgReceiver(), new IntentFilter(Config.PLAY_ACTION));
        Debug.startDebug("name", "服务已经创建");
    }


    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        String url = intent.getStringExtra("url");
        Debug.startDebug("name", url);
        return new MyBinder();
    }

    public void playMusic(String url) {
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    new ProgressThread().start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class ProgressThread extends Thread {
        @Override
        public void run() {
            while (mediaPlayer != null) {
                //当前的播放位置
                Intent intent = new Intent(Config.SERVICE_ACTION);
                intent.putExtra("total", mediaPlayer.getDuration());
                intent.putExtra("current", mediaPlayer.getCurrentPosition());
                //发送进度广播
                sendBroadcast(intent);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public class MyBinder extends Binder {
        public void init(String url) {
//            if (mediaPlayer.isPlaying()){
                mediaPlayer.reset();
//            }
            playMusic(url);
        }

        public void play() {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();

            }
        }


        public void stop() {

        }
    }

    class PrgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //接收服务中的进度广播
            current = intent.getIntExtra("current", 0);

            mediaPlayer.seekTo(current);

        }
    }

}
