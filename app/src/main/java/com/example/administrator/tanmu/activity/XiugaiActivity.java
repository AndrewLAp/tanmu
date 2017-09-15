package com.example.administrator.tanmu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.tanmu.R;
import com.example.administrator.tanmu.object.Person;
import com.example.administrator.tanmu.view.TitleBar;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class XiugaiActivity extends AppCompatActivity {

    private TitleBar titleBar;
    private Button xiugai;
    private EditText oldp;
    private EditText newp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiugai);

        Bmob.initialize(this, "df610b845570afeeebc1a17eb36e726d");

        titleBar=(TitleBar)findViewById(R.id.xiugai_title);
        xiugai=(Button)findViewById(R.id.xiugai);
        oldp=(EditText)findViewById(R.id.oldpassword);
        newp=(EditText)findViewById(R.id.newpassword);

        titleBar.setTitleBarClickListetner(new TitleBar.titleBarClickListener() {
            @Override
            public void leftButtonClick() {
                Intent intent=new Intent(XiugaiActivity.this,dakaishiping.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void rightButtonClick() {

            }
        });

        xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String newpassword1 = oldp.getText().toString();
                final String newpassword2 = newp.getText().toString();

                Person person= BmobUser.getCurrentUser(Person.class);
                BmobUser bmobUser=new BmobUser();
                bmobUser.setObjectId(person.getObjectId());
                bmobUser.setSessionToken(person.getSessionToken());
                if (newpassword1.equals(newpassword2)){
                    bmobUser.setPassword(newpassword2);
                    bmobUser.update(bmobUser.getObjectId(),new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                Intent intent=new Intent(XiugaiActivity.this,dakaishiping.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(XiugaiActivity.this,"修改密码成功",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(XiugaiActivity.this,"修改密码失败",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }else {
                    Toast.makeText(XiugaiActivity.this,"密码确认不相符",Toast.LENGTH_SHORT).show();
                }


                }



        });

    }
}
