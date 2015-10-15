package com.whl.hp.baidumusic;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whl.hp.baidumusic.bean.MusicInfo;
import com.whl.hp.baidumusic.bean.SoungList;
import com.whl.hp.baidumusic.tool.Config;
import com.whl.hp.baidumusic.tool.Debug;
import com.whl.hp.baidumusic.tool.NUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp-whl on 2015/9/20.
 */
public class PlayFragment extends Fragment {
    TextView textView, time, songWord;
    SeekBar seekBar;
    List<MusicInfo> list;
    String lys;
    int i = 0;
    ImageView imageView;
    public ArrayList<String> soungLists;
    String musicurl;
    String[] wordList;
    int seceltPosition = 0;
    Bitmap songImage;
    ScrollView scrollView;
    boolean isFirst = false;
    boolean isClick = false;
    ImageButton pre, play, next;
    private MusicService.MyBinder myBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MusicService.MyBinder) service;
            myBinder.init(musicurl);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.MUSIC_ACTION);
        filter.addAction(Config.SERVICE_ACTION);
        getActivity().registerReceiver(new MuseicReceiver(), filter);


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.player_layout, container, false);
        textView = (TextView) view.findViewById(R.id.playName);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        time = (TextView) view.findViewById(R.id.time);
        songWord = (TextView) view.findViewById(R.id.songWord);
        imageView = (ImageView) view.findViewById(R.id.songImage);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent intent = new Intent(Config.PLAY_ACTION);
                int now = seekBar.getProgress();
                intent.putExtra("current", now);
                getActivity().sendBroadcast(intent);

            }
        });
        play = (ImageButton) view.findViewById(R.id.paly);
        play.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (soungLists!=null) {
                    myBinder.play();
                }


            }
        });
        next = (ImageButton) view.findViewById(R.id.next);
        pre = (ImageButton) view.findViewById(R.id.pre);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soungLists!=null) {
                    if (seceltPosition - 1 >= 0) {
                        seceltPosition -= 1;
                        loadMusicInfo(soungLists.get(seceltPosition));
                    }
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soungLists!=null) {

                    if (seceltPosition + 1 < soungLists.size()) {
                        seceltPosition += 1;
                        loadMusicInfo(soungLists.get(seceltPosition));
                    }

                }
            }
        });
        return view;

    }

    public void loadImageView(String url) {
        NUtils.get(NUtils.TYPE_IMG, url, new NUtils.AbsCallback() {
            @Override
            public void response(String url, byte[] bytes) {
                super.response(url, bytes);
                Debug.startDebug("debug", list.get(0).getSongPicBig() + "图片");
                songImage = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);


            }
        });
    }

    //加载歌词
    public void loadSongWord(String url) {
        NUtils.get(NUtils.TYPE_TXT, String.format(Config.HEAD_URL, url), new NUtils.AbsCallback() {
            public void response(String url, byte[] bytes) {
                super.response(url, bytes);

                try {
                    String s = new String(bytes, "utf-8");
                    lys = s;
                    wordList = s.split("\n");

//                    songWord.setText(s);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadMusicInfo(String id) {
        String url = String.format(Config.MUSICID_URL, id);
        NUtils.get(NUtils.TYPE_TXT, url, new NUtils.AbsCallback() {
            @Override
            public void response(String url, byte[] bytes) {
                super.response(url, bytes);
                try {
                    String json = new String(bytes, "utf-8");
                    JSONObject obj1 = new JSONObject(json);
                    JSONObject obj2 = obj1.getJSONObject("data");
                    JSONArray array = obj2.getJSONArray("songList");
                    TypeToken<List<MusicInfo>> typeToken = new TypeToken<List<MusicInfo>>() {
                    };
                    Gson geon = new Gson();
                    list = geon.fromJson(array.toString(), typeToken.getType());
                    textView.setText(list.get(0).getArtistName() + "     " + list.get(0).getSongName());
                    musicurl = list.get(0).getSongLink();
                    loadSongWord(list.get(0).getLrcLink());
                    loadImageView(list.get(0).getSongPicBig());


                    Intent intent = new Intent(getActivity().getBaseContext(), MusicService.class);
                    intent.putExtra("url", musicurl);
                    getActivity().getBaseContext().startService(intent);
                    Debug.startDebug("name", "开启服务");
                    getActivity().getBaseContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);
                    if (isFirst) {
                        myBinder.init(musicurl);
                    }
                    isFirst = true;
                    isClick = true;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    class MuseicReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Config.MUSIC_ACTION)) {
                String id = intent.getStringExtra("id");
                seceltPosition = intent.getIntExtra("position", 0);
                soungLists = intent.getStringArrayListExtra("songList");
                Debug.startDebug("debug", soungLists.size() + "数据类表");
                loadMusicInfo(id);
            } else if (intent.getAction().equals(Config.SERVICE_ACTION)) {
                int total = intent.getIntExtra("total", 0);

                int current = intent.getIntExtra("current", 0);
                time.setText(getTime(current, false) + "/" + getTime(total, false));
                String cuttentTime = getTime(current, false);
                for (String s : wordList) {

                    Log.d("dubug", cuttentTime + "    " + s);

                    if (s.contains(cuttentTime)) {
                        songWord.setText(s.substring(10));
                    }
                }

                textView.setText(list.get(0).getArtistName() + "     " + list.get(0).getSongName());
                seekBar.setMax(total);
                seekBar.setProgress(current);
                if (songImage != null) {
                    imageView.setImageBitmap(songImage);

                }
            }


        }
    }


    public String getTime(int time, boolean add) {

        //线程不安全
        StringBuilder builder = new StringBuilder();
        int m = time / 1000 / 60;
        int s = (time / 1000) % 60;
        int ss = (time % 100);
        if (add) {
            builder.append(m / 10).append(m % 10).append(":").append(s / 10).append(s % 10).append(".").append(ss / 10).append(ss % 10);
        } else {
            builder.append(m / 10).append(m % 10).append(":").append(s / 10).append(s % 10);
        }
        return builder.toString();


    }
}
