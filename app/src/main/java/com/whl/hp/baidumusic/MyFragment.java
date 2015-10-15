package com.whl.hp.baidumusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.whl.hp.baidumusic.bean.Channel;
import com.whl.hp.baidumusic.tool.Config;
import com.whl.hp.baidumusic.tool.Debug;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp-whl on 2015/9/20.
 */
public class MyFragment extends Fragment {


    public static MyFragment newInstance(byte b[], int cid) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putByteArray("byte", b);
        args.putInt("cid", cid);
        fragment.setArguments(args);
        return fragment;
    }
    List<Channel> channels;
    private byte b[];
    private GridView gridView;
    private int cid;
    private ChanelAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        b = getArguments().getByteArray("byte");
        cid = getArguments().getInt("cid");
        Debug.startDebug("debug",cid+"");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chanel_layout, container,false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               ViewPager viewPager= (ViewPager) getActivity().findViewById(R.id.viewPager);
                viewPager.setCurrentItem(2);
                Intent intent=new Intent(Config.CHANEL_ACTION);
                intent.putExtra(Config.CHANEL_NAME, channels.get(position).getCh_name());

                getActivity().sendBroadcast(intent);


            }
        });


    }

    public void getData() {
        channels = new ArrayList();
        try {
            String json = new String(b, "utf-8");
            JSONObject obj1 = new JSONObject(json);
            JSONArray array = obj1.getJSONArray("result");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj2 = array.getJSONObject(i);
                if (obj2.getInt("cid") == cid) {
                    JSONArray obj3 = obj2.getJSONArray("channellist");
                    TypeToken<List<Channel>> token = new TypeToken<List<Channel>>() {
                    };
                    Gson gson = new Gson();
                    channels = gson.fromJson(obj3.toString(), token.getType());

                    adapter=new ChanelAdapter(getActivity(),channels);
                    gridView.setAdapter(adapter);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
