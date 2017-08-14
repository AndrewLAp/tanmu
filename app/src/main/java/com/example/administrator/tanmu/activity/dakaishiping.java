package com.example.administrator.tanmu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.tanmu.R;
import com.example.administrator.tanmu.adapter.AdapterMain;
import com.example.administrator.tanmu.adapter.AdapterViewPageMain;
import com.example.administrator.tanmu.view.LayoutDianshiju;
import com.example.administrator.tanmu.view.LayoutDianying;
import com.example.administrator.tanmu.view.LayoutDogman;
import com.example.administrator.tanmu.view.LayoutTuijian;
import com.example.administrator.tanmu.view.MyRecyclerView;

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



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dakaishiping);

        settings=(ImageView) findViewById(R.id.settings);
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




    private void initTab(){
        viewPager=(ViewPager)findViewById(R.id.vp_main);
        tabLayout=(TabLayout)findViewById(R.id.tab);

        tabList = new ArrayList<>();

        AdapterViewPageMain adapter = new AdapterViewPageMain(getSupportFragmentManager());

        adapter.addFragment(new LayoutTuijian());
        adapter.addFragment(new LayoutDianying());
        adapter.addFragment(new LayoutDianshiju());
        adapter.addFragment(new LayoutDogman());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabList.add(tabLayout.getTabAt(0));
        tabList.add(tabLayout.getTabAt(1));
        tabList.add(tabLayout.getTabAt(2));
        tabList.add(tabLayout.getTabAt(3));
        tabList.get(0).setText("推荐");
        tabList.get(1).setText("电影");
        tabList.get(2).setText("电视剧");
        tabList.get(3).setText("动漫");

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


}
