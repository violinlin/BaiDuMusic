package com.whl.hp.baidumusic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.whl.hp.baidumusic.bean.RadioEntity;
import com.whl.hp.baidumusic.tool.Config;
import com.whl.hp.baidumusic.tool.Debug;
import com.whl.hp.baidumusic.tool.NUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    ViewPager viewPager;
    List<Fragment> fragments;
    ActionBar actionBar;
    List<RadioEntity> radioEntities;
    MyFragmentAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        initActionBar();
        Debug.isDebug = true;

    }

    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        radioEntities = new ArrayList();
        loadData();
    }

    //获取音乐频道的内容
    private void loadData() {
        NUtils.get(NUtils.TYPE_TXT, Config.RADIO_URL, new NUtils.AbsCallback() {
            @Override
            public void response(String url, byte[] bytes) {
                super.response(url, bytes);
                String json = new String(bytes);
                try {
                    JSONObject obj1 = new JSONObject(json);
                    JSONArray array = obj1.getJSONArray("result");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj2 = array.getJSONObject(i);
                        String title = obj2.getString("title");
                        Debug.startDebug("debug", title);
                        int cid = obj2.getInt("cid");

                        RadioEntity radioEntity = new RadioEntity();
                        radioEntity.setTitle(title);
                        radioEntity.setCid(cid);
                        radioEntities.add(radioEntity);

                    }
                    fragments = new ArrayList<Fragment>();
                    for (RadioEntity r : radioEntities) {
                        Debug.startDebug("debug", r.getCid() + "");
                        actionBar.addTab(actionBar.newTab().setText(r.getTitle()).setTabListener(MainActivity.this));
                        fragments.add(new MyFragment().newInstance(bytes, r.getCid()));
                    }
                    actionBar.addTab(actionBar.newTab().setText("音乐列表").setTabListener(MainActivity.this));
                    actionBar.addTab(actionBar.newTab().setText("播放列表").setTabListener(MainActivity.this));
                    adapter = new MyFragmentAdapter(getSupportFragmentManager());
                    fragments.add(MusicFragment.newInstance("public_tuijian_zhongguohaoshengyin"));
                    fragments.add(new PlayFragment());
                    viewPager.setAdapter(adapter);
                    initViewPager();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void initViewPager() {

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
