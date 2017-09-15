package com.example.administrator.tanmu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.tanmu.R;
import com.example.administrator.tanmu.view.TitleBar;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    private EditText mname, mpassword;
    private Button mlogin, mregi;
    private TitleBar denglu_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "df610b845570afeeebc1a17eb36e726d");
        setContentView(R.layout.activity_login);

        denglu_title = (TitleBar) findViewById(R.id.denglu_title);
        mname = (EditText) findViewById(R.id.sname);
        mpassword = (EditText) findViewById(R.id.spassword);
        mlogin = (Button) findViewById(R.id.login);
        mregi = (Button) findViewById(R.id.zhuche1);

        denglu_title.setTitleBarClickListetner(new TitleBar.titleBarClickListener() {
            @Override
            public void leftButtonClick() {
                finish();
            }

            @Override
            public void rightButtonClick() {

            }
        });

        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlogin();
            }
        });

        mregi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dregi();
            }
        });
    }


    //注册点击
    public void dregi() {
        Intent intentlogin = new Intent();
        intentlogin.setClass(LoginActivity.this, ZhucheActivity.class);
        startActivity(intentlogin);

    }

    private void dlogin() {
        BmobUser person = new BmobUser();
        final String name = mname.getText().toString();
        String password = mpassword.getText().toString();
        person.setUsername(name);
        person.setPassword(password);
        person.login(new SaveListener<BmobUser>() {

            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, dakaishiping.class);
                    SharedPreferences.Editor editor = getSharedPreferences("User", MODE_PRIVATE).edit();
                    editor.putString("username", name);
                    editor.apply();
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "账号密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
