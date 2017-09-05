package com.example.administrator.tanmu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.administrator.tanmu.R;
import com.example.administrator.tanmu.adapter.AdapterViewPageMain;
import com.example.administrator.tanmu.view.LayoutDianshiju;
import com.example.administrator.tanmu.view.LayoutDianying;
import com.example.administrator.tanmu.view.LayoutDogman;
import com.example.administrator.tanmu.view.LayoutTuijian;
import com.example.administrator.tanmu.view.LayoutZongyi;

import java.util.ArrayList;
import java.util.List;

public class dakaishiping extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private List<TabLayout.Tab> tabList;
    private PopupWindow mPopupWindow;
    private ImageView settings;
    private ImageView search;
    private LinearLayout search_layout;
    private LinearLayout layout_main;
    private DrawerLayout mDrawerLayout;
    private SwipeRefreshLayout swipeRefresh;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dakaishiping);


        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.draw_layout);
        layout_main=(LinearLayout)findViewById(R.id.layout_main);
        search_layout=(LinearLayout)findViewById(R.id.search_layout);
        search=(ImageView)findViewById(R.id.search);
        settings=(ImageView) findViewById(R.id.settings);


        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDatas();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (search_layout.getVisibility()==View.GONE){
                    search_layout.setVisibility(View.VISIBLE);
                    layout_main.setVisibility(View.GONE);
                    search.setImageResource(R.drawable.back);
                }else {
                    search_layout.setVisibility(View.GONE);
                    layout_main.setVisibility(View.VISIBLE);
                    search.setImageResource(R.drawable.sousuo);
                }

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showPopupWindow();
            }
        });


        initTab();


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);




        final Intent intent=new Intent(this,Video.class);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "打开本地视频", Snackbar.LENGTH_LONG)
                        .setAction("打开", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(intent);
                            }
                        }).show();
            }
        });

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.daohang);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    private void refreshDatas(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initTab(){
        viewPager=(ViewPager)findViewById(R.id.vp_main);
        tabLayout=(TabLayout)findViewById(R.id.tab);

        tabList = new ArrayList<>();

        AdapterViewPageMain adapter = new AdapterViewPageMain(getSupportFragmentManager());

        adapter.addFragment(new LayoutTuijian());
        adapter.addFragment(new LayoutDianying());
        adapter.addFragment(new LayoutDianshiju());
        adapter.addFragment(new LayoutDogman());
        adapter.addFragment(new LayoutZongyi());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabList.add(tabLayout.getTabAt(0));
        tabList.add(tabLayout.getTabAt(1));
        tabList.add(tabLayout.getTabAt(2));
        tabList.add(tabLayout.getTabAt(3));
        tabList.add(tabLayout.getTabAt(4));
        tabList.get(0).setText("推荐");
        tabList.get(1).setText("电影");
        tabList.get(2).setText("电视剧");
        tabList.get(3).setText("动漫");
        tabList.get(4).setText("综艺");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.setTabTextColors(
                        ContextCompat.getColor(dakaishiping.this, R.color.Black),
                        ContextCompat.getColor(dakaishiping.this, R.color.Green));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void showPopupWindow(){
        View contentView = LayoutInflater.from(dakaishiping.this).inflate(R.layout.popuplayout, null);
        mPopupWindow = new PopupWindow(contentView);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        mPopupWindow.setFocusable(true);
        mPopupWindow.showAsDropDown(settings,0,30);

        mPopupWindow.update();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if (keyCode==event.KEYCODE_BACK&&event.getRepeatCount()==0){
           if (search_layout.getVisibility()==View.VISIBLE){
               search_layout.setVisibility(View.GONE);
               layout_main.setVisibility(View.VISIBLE);
               search.setImageResource(R.drawable.sousuo);
               return true;
           }

       }
        return super.onKeyDown(keyCode, event);
    }


}
