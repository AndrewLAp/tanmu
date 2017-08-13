package com.example.administrator.tanmu.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.administrator.tanmu.R;
import com.example.administrator.tanmu.adapter.AdapterMain;
import com.example.administrator.tanmu.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class dakaishiping extends AppCompatActivity {
    private AdapterMain myAdapter;
    private FloatingActionButton floatingActionButton;
    private List<Integer> mdata=new ArrayList<>();
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private Boolean isRuning;
    private MyRecyclerView recyclerView_scroll;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dakaishiping);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(Color.BLACK);
        }

        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_main);
        recyclerView_scroll=(MyRecyclerView)findViewById(R.id.recycler_scroll);

        intData();
        myAdapter=new AdapterMain(mdata,this);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);


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

        toolbar.setOverflowIcon(getDrawable(R.drawable.settings));
    }

    private void intData(){
        for (int i=0;i<10;i++){
            mdata.add(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Toast.makeText(this,"You clicked Backup",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }
}
