package com.example.administrator.tanmu.activity;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.tanmu.R;
import com.example.administrator.tanmu.object.Person;
import com.example.administrator.tanmu.view.TitleBar;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class ZhucheActivity extends AppCompatActivity {

    private TitleBar zhuche_title;
    private Button zhuche;
    private EditText mname, mpassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuche);
        Bmob.initialize(this, "df610b845570afeeebc1a17eb36e726d");
        zhuche_title = (TitleBar) findViewById(R.id.zhuche_title);
        zhuche = (Button) findViewById(R.id.zhuche2);
        mname = (EditText) findViewById(R.id.name);
        mpassword = (EditText) findViewById(R.id.password);

        zhuche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regin();
            }
        });
        zhuche_title.setTitleBarClickListetner(new TitleBar.titleBarClickListener() {
            @Override
            public void leftButtonClick() {
                finish();

            }

            @Override
            public void rightButtonClick() {

            }
        });


    }

    private void regin() {
        zhuche();

    }

    private void zhuche() {
        final String name = mname.getText().toString();
        final String password = mpassword.getText().toString();
        Person person = new Person();
        person.setUsername(name);
        person.setPassword(password);

        final ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        person.signUp(new SaveListener<Person>() {
            @Override
            public void done(Person s, BmobException e) {
                if (e == null) {
                    Toast.makeText(ZhucheActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (name.length() == 0 || password.length() == 0) {
                    Toast.makeText(ZhucheActivity.this, "注册失败,用户名或账号不能为空", Toast.LENGTH_SHORT).show();
                } else if (cwjManager.getActiveNetworkInfo().isAvailable()) {
                    Toast.makeText(ZhucheActivity.this, "注册失败,请检查网络", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ZhucheActivity.this, "注册失败，用户已存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
