package com.example.administrator.tanmu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.tanmu.R;
import com.example.administrator.tanmu.adapter.AdapterSetting;
import com.example.administrator.tanmu.object.Person;
import com.example.administrator.tanmu.view.DividerItemDecoration;
import com.example.administrator.tanmu.view.TitleBar;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class SettingActivity extends AppCompatActivity implements AdapterSetting.onSettingClick {


    private RecyclerView recyclerView;
    private AdapterSetting adapter;
    private TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Bmob.initialize(this, "df610b845570afeeebc1a17eb36e726d");

        titleBar=(TitleBar)findViewById(R.id.denglu_title);
        recyclerView=(RecyclerView)findViewById(R.id.setting_recycler);
        adapter=new AdapterSetting();
        adapter.setSettingClick(this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        titleBar.setTitleBarClickListetner(new TitleBar.titleBarClickListener() {
            @Override
            public void leftButtonClick() {
                Intent intent=new Intent(SettingActivity.this,dakaishiping.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void rightButtonClick() {

            }
        });


    }

    @Override
    public void onFirstClick(View view, int position) {
        Person person= BmobUser.getCurrentUser(Person.class);
        if (person!=null){
            BmobUser.logOut();
            Intent intent=new Intent(SettingActivity.this,dakaishiping.class);
            startActivity(intent);
            Toast.makeText(SettingActivity.this, "已退出登陆", Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(SettingActivity.this, "你未登陆", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSecondClick(View view, int position) {
        Person person= BmobUser.getCurrentUser(Person.class);
        if (person!=null){
            Intent intent=new Intent(SettingActivity.this,XiugaiActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(SettingActivity.this, "你未登陆", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent=new Intent(SettingActivity.this,dakaishiping.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
