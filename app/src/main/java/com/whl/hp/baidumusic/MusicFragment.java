package com.whl.hp.baidumusic;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whl.hp.baidumusic.bean.SoungList;
import com.whl.hp.baidumusic.tool.Config;
import com.whl.hp.baidumusic.tool.Debug;
import com.whl.hp.baidumusic.tool.NUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp-whl on 2015/9/20.
 */
public class MusicFragment extends ListFragment {


    public static MusicFragment newInstance(String url) {
        MusicFragment m = new MusicFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        m.setArguments(args);
        return m;
    }

    public String url="";
    public List<SoungList> soungLists;
    public ArrayList<String> songIDs;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        url = String.format(Config.MUSICLIST_URL, getArguments().getString("url"));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().registerReceiver(new MyReceiver(), new IntentFilter(Config.CHANEL_ACTION));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadData();
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Config.MUSIC_ACTION);
                intent.putExtra("id", soungLists.get(position).getSongid());
                intent.putStringArrayListExtra("songList", songIDs);
                intent.putExtra("position", position);
                getActivity().sendBroadcast(intent);
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
                viewPager.setCurrentItem(3);


            }
        });

    }


    private void loadData() {
        NUtils.get(NUtils.TYPE_TXT, url, new NUtils.AbsCallback() {
            @Override
            public void response(String url, byte[] bytes) {
                Debug.startDebug("chanlName",url);
                super.response(url, bytes);
                try {
                    String json = new String(bytes, "utf-8");
                    JSONObject obj1 = new JSONObject(json);
                    JSONObject obj2 = obj1.getJSONObject("result");
                    JSONArray array = obj2.getJSONArray("songlist");
                    TypeToken<List<SoungList>> token = new TypeToken<List<SoungList>>() {
                    };
                    Gson gson = new Gson();
                    soungLists = gson.fromJson(array.toString(), token.getType());
                    songIDs = new ArrayList<String>();
                    for (SoungList s : soungLists) {
                        songIDs.add(s.getSongid());
                    }
                    setListAdapter(new MusicAdapter(getActivity(), soungLists));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            url = String.format(Config.MUSICLIST_URL, intent.getStringExtra(Config.CHANEL_NAME));
            Debug.startDebug("chanlName",url);
            loadData();
        }
    }
}
