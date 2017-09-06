package com.example.administrator.tanmu.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.tanmu.R;
import com.example.administrator.tanmu.adapter.VideoAdapter;
import com.example.administrator.tanmu.object.VideoPath;
import com.example.administrator.tanmu.view.DividerItemDecoration;
import com.example.administrator.tanmu.view.TitleBar;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Video extends AppCompatActivity implements VideoAdapter.onMyClick {
    public static List<VideoPath> mVideoList2;
    private RecyclerView recyclerView;
    private  List<VideoPath> mVideoList=new ArrayList<>();
    private  VideoAdapter adapter;
    private LinearLayout piliang_operation;
    private Button delete;
    private Button selecteAll;
    private TitleBar titleBar;
    private Boolean isShow=false;
    private Boolean isSelecteAll=false;

    public static List<VideoPath> getmVideoList2() {
        return mVideoList2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && this.checkSelfPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            this.requestPermissions(new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
        }else{
            readVideoPath();
        }

        titleBar=(TitleBar)findViewById(R.id.title);
        delete=(Button)findViewById(R.id.delete);
        selecteAll=(Button)findViewById(R.id.selectAll);
        piliang_operation=(LinearLayout)findViewById(R.id.piliang_operation);
        recyclerView=(RecyclerView)findViewById(R.id.recycle);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new VideoAdapter(this,mVideoList);
        adapter.setOnMyClick(this);
        recyclerView.setAdapter(adapter);
        mVideoList2=mVideoList;



        titleBar.setTitleBarClickListetner(new TitleBar.titleBarClickListener() {
            @Override
            public void leftButtonClick() {
                finish();
            }

            @Override
            public void rightButtonClick() {
                if (!isShow){
                    piliang_operation.setVisibility(View.VISIBLE);
                    for (int i=0;i<mVideoList.size();i++){
                        mVideoList.get(i).isVisibility=View.VISIBLE;
                    }
                    adapter.notifyDataSetChanged();
                    isShow=true;
                    titleBar.setBtnRightText("完成");

                }else {
                    piliang_operation.setVisibility(View.GONE);
                    for (int i=0;i<mVideoList.size();i++){
                        mVideoList.get(i).isVisibility=View.GONE;
                        mVideoList.get(i).setIsCheck(false);
                    }
                    adapter.notifyDataSetChanged();
                    isShow=false;
                    titleBar.setBtnRightText("编辑");
                    delete.setText("删除");
                    selecteAll.setText("全选");
                    isSelecteAll=false;
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Iterator<VideoPath> it = mVideoList.iterator();
                while (it.hasNext()){
                    VideoPath vp=it.next();
                    if (vp.getIsCheck()){
                        File file=new File(vp.getVideoPath());
                        file.delete();
                        it.remove();
                        adapter.notifyItemRangeRemoved(0,mVideoList.size());
                        adapter.notifyItemRangeChanged(0,mVideoList.size());
                    }
                }
                delete.setText("删除");
            }
        });

        selecteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSelecteAll) {
                    for (int i = 0; i < mVideoList.size(); i++) {
                        if (mVideoList.get(i).getIsCheck() != true) {
                            mVideoList.get(i).setIsCheck(true);
                        }
                        selecteAll.setText("取消全选");
                        isSelecteAll = true;
                        delete.setText("删除"+"("+mVideoList.size()+")");
                    }
                } else {
                    for (int i = 0; i < mVideoList.size(); i++) {
                        if (mVideoList.get(i).getIsCheck() == true) {
                            mVideoList.get(i).setIsCheck(false);
                        }
                        selecteAll.setText("全选");
                        isSelecteAll = false;
                        delete.setText("删除");
                    }

                }
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void readVideoPath(){
        ContentResolver contentResolver = this.getContentResolver();
        String[] projection = new String[]{MediaStore.Video.Media.DATA};
        Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int fileNum = cursor.getCount();

        for(int counter = 0; counter < fileNum; counter++){
            VideoPath path=new VideoPath();
            String videopath=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            File file=new File(videopath);
            if (file.exists()){
                int i=videopath.lastIndexOf("/")+1;
                String name=videopath.substring(i);
                path.setVideoName(name);
                path.setVideoPath(videopath);
                mVideoList.add(path);
            }
            cursor.moveToNext();
        }
        cursor.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED){
                    readVideoPath();
                }else {
                    Toast.makeText(this,"you denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    @Override
    public void longClick(View view, int position) {
        if (!isShow){
            piliang_operation.setVisibility(View.VISIBLE);
            for (int i=0;i<mVideoList.size();i++){
                mVideoList.get(i).isVisibility=View.VISIBLE;
            }
            mVideoList.get(position).setIsCheck(true);
            adapter.notifyDataSetChanged();
            isShow=true;
            delete.setText("删除"+"("+1+")");
            titleBar.setBtnRightText("完成");
        }
    }

    @Override
    public void Click(View view, int position, Boolean isCheck) {
        if (isShow){
            mVideoList.get(position).setIsCheck(!isCheck);
            adapter.notifyDataSetChanged();
            int j=0;
            for (int i=0;i<mVideoList.size();i++){
                if (mVideoList.get(i).getIsCheck()==true){
                    j++;
                }
            }
            if (j!=0){
                delete.setText("删除"+"("+j+")");
            }else {
                delete.setText("删除");
            }

        }else {
            Intent intent=new Intent(this,MainActivity.class);
            intent.putExtra("videoPath",mVideoList.get(position).getVideoPath());
            intent.putExtra("videoName",mVideoList.get(position).getVideoName());
            intent.putExtra("position",position);
            this.startActivity(intent);
        }

    }
}
