package com.example.administrator.tanmu.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.administrator.tanmu.R;
import com.example.administrator.tanmu.object.VideoPath;

import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener{
    private static final String TAG = "MainActivity";
    private boolean showDanmaku;
    private DanmakuView danmakuView;
    private DanmakuContext danmakuContext;
    private RelativeLayout video_operation;
    private RelativeLayout operation_top;
    private LinearLayout operation;
    private int image_id[]={R.drawable.bofang,R.drawable.zanting};
    private int kg_tanmuid[]={R.drawable.dakai,R.drawable.guanbi};
    private ImageView image;
    private ImageView shangyige;
    private ImageView xiayige;
    private ImageView qiehuan_button;
    private ImageView tanmu_button;
    private TextView mytime;
    private ImageView kg_tanmu;
    private ImageView back;
    private VideoView videoView;
    private SeekBar seekBar;
    private TextView videoTimeText;
    private int i=0;
    private int j=0;
    private String videoPath;
    private String videoName;
    private List<VideoPath> videoList;
    private int video_position;
    private int videoTime;
    private int mColorInitAlpha = 150;
    private ColorDrawable video_operationCDrawable;
    private ColorDrawable operationBCDrawable;
    private ColorDrawable operationTCDrawable;

    GestureDetector gestureDetector;
    RelativeLayout mLayout;

    int width;
    int height;

    private BaseDanmakuParser parser=new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };



    final Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                    break;
                case 2:
                    Calendar c = Calendar.getInstance();
                    int hour = c.get(Calendar.HOUR_OF_DAY);
                    int minute = c.get(Calendar.MINUTE);
                    String sysTimeStr;
                    if (minute<10){
                        sysTimeStr=hour+":"+"0"+minute;
                    }else {
                        sysTimeStr=hour+":"+minute;
                    }

                    mytime.setText(sysTimeStr);
            }
        }
    };


    private StringBuilder mFormatBuilder= new StringBuilder();
    private Formatter mFormatter= new Formatter(mFormatBuilder, Locale.getDefault());
    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = totalSeconds / 60;
        mFormatBuilder.setLength(0);
        if (minutes>=100) {
            return mFormatter.format("%d:%02d", minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout=(RelativeLayout)findViewById(R.id.activity_main);


        gestureDetector=new GestureDetector(this,this);
        mLayout.setOnTouchListener(this);
        mLayout.setLongClickable(true);
        gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent motionEvent) {
                if (videoView.isPlaying()){
                    videoView.pause();
                }else {
                    videoView.start();
                }
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                return false;
            }
        });







        operation=(LinearLayout)findViewById(R.id.operation);
        final EditText editText=(EditText)findViewById(R.id.edit);
        final Button send=(Button)findViewById(R.id.send);

        video_operation=(RelativeLayout)findViewById(R.id.video_operation);
        operation_top=(RelativeLayout)findViewById(R.id.operation_top);
        image=(ImageView)findViewById(R.id.image);
        shangyige=(ImageView)findViewById(R.id.shangyige);
        xiayige=(ImageView)findViewById(R.id.xiayige);
        qiehuan_button=(ImageView)findViewById(R.id.qiehuan);
        mytime=(TextView)findViewById(R.id.mytime);
        tanmu_button=(ImageView)findViewById(R.id.tanmu_button);
        kg_tanmu=(ImageView)findViewById(R.id.kg_tanmu);
        seekBar=(SeekBar)findViewById(R.id.jindu);
        videoTimeText=(TextView)findViewById(R.id.videoTime);
        back=(ImageView)findViewById(R.id.Back);

        Drawable video_operationDrawable=video_operation.getBackground();
        video_operationCDrawable=(ColorDrawable)video_operationDrawable;
        video_operationCDrawable.setAlpha(mColorInitAlpha);

        Drawable operationDrawable=operation.getBackground();
        operationBCDrawable=(ColorDrawable)operationDrawable;
        operationBCDrawable.setAlpha(mColorInitAlpha);

        Drawable operationTDrawable=operation_top.getBackground();
        operationTCDrawable=(ColorDrawable)operationTDrawable;
        operationTCDrawable.setAlpha(mColorInitAlpha);






        videoView=(VideoView)findViewById(R.id.video);

        Intent intent=getIntent();
        videoPath=intent.getStringExtra("videoPath");
        videoName=intent.getStringExtra("videoName");
        video_position=intent.getIntExtra("position",0);
        videoList= Video.getmVideoList2();



        videoView.setVideoPath(videoPath);
        SharedPreferences pref=getSharedPreferences("VIDEO",MODE_PRIVATE);
        final int position=pref.getInt("TIME"+videoName,0);
        videoView.seekTo(position);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoTime=videoView.getDuration();
                seekBar.setMax(videoTime);
            }
        });



        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.seekTo(0);
                videoView.start();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){

                        seekBar.setProgress(videoView.getCurrentPosition());
                        Message msg=new Message();
                        msg.what=2;
                        handler.sendMessage(msg);
                        Thread.sleep(500);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        shangyige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video_position=video_position-1;
                if (video_position<0){
                    video_position=videoList.size()-1;
                }
                videoView.setVideoPath(videoList.get(video_position).getVideoPath());
                videoView.start();

            }
        });
        xiayige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                video_position=video_position+1;
                if (video_position==videoList.size()){
                    video_position=0;
                }
                videoView.setVideoPath(videoList.get(video_position).getVideoPath());
                videoView.start();
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i==0){
                    i++;
                    image.setImageResource(image_id[0]);
                    videoView.pause();
                }else {
                    i--;
                    image.setImageResource(image_id[1]);
                    videoView.start();
                }

            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    videoView.seekTo(i);
                }
                videoTimeText.setText(stringForTime(videoView.getCurrentPosition())+"/"+stringForTime(videoTime));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        danmakuView=(DanmakuView)findViewById(R.id.tanmu);
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                danmakuView.start();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });

        kg_tanmu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (j==0){
                    j++;
                    kg_tanmu.setImageResource(kg_tanmuid[1]);
                    showDanmaku=true;
                    generateSomeDanmaku();
                }else {
                    j--;
                    kg_tanmu.setImageResource(kg_tanmuid[0]);
                    showDanmaku=false;

                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProgress();
                finish();

            }
        });


        

        qiehuan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    qiehuan_button.setImageResource(R.drawable.suoxiao);
                    handler.sendEmptyMessageDelayed(1,3000);

                }else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                    qiehuan_button.setImageResource(R.drawable.quanping);
                    handler.sendEmptyMessageDelayed(1,3000);
                }
            }
        });

        tanmu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 video_operation.setVisibility(View.GONE);
                operation.setVisibility(View.VISIBLE);
            }
        });

        danmakuContext=DanmakuContext.create();
        danmakuView.prepare(parser,danmakuContext);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=editText.getText().toString();
                if (!TextUtils.isEmpty(content)){
                    addDanmaku(content,true);
                    editText.setText("");
                }
            }
        });
       getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if (i==View.SYSTEM_UI_FLAG_VISIBLE){
                    onWindowFocusChanged(true);
                }
            }
        });

    }
    private void addDanmaku(String content,boolean withBorder){
        BaseDanmaku danmaku=danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text=content;
        danmaku.padding=5;
        danmaku.textSize=sp2px(20);
        danmaku.textColor= Color.WHITE;
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withBorder){
            danmaku.borderColor=Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }
    private void generateSomeDanmaku(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (showDanmaku){
                    int time=new Random().nextInt(300);
                    String content=""+time+time;
                    addDanmaku(content,false);
                    try {
                        Thread.sleep(time);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private int sp2px(float spValue){
        float fontScale=getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue*fontScale+0.5f);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (danmakuView!=null&&danmakuView.isPrepared()){
            danmakuView.pause();
        }
        if (videoView!=null&&videoView.isPlaying()){
            videoView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveProgress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (danmakuView!=null&&danmakuView.isPrepared()&&danmakuView.isPaused()){
            danmakuView.resume();
        }
        if (videoView!=null&&!videoView.isPlaying()){
            videoView.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showDanmaku=false;
        if (danmakuView!=null){
            danmakuView.release();
            danmakuView=null;
        }
        saveProgress();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus&& Build.VERSION.SDK_INT>=19){
            View decorView=getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    |View.SYSTEM_UI_FLAG_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }
    public void saveProgress(){
        if (videoView.getCurrentPosition()!=0){
            int time=videoView.getCurrentPosition();
            SharedPreferences.Editor editor=getSharedPreferences("VIDEO",MODE_PRIVATE).edit();
            editor.putInt("TIME"+videoName,time);
            editor.apply();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                saveProgress();
                this.finish();
                return true;

        }
        return super.onKeyDown(keyCode, event);
    }




    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        // 检测屏幕的方向：纵向或横向
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //当前为横屏， 在此处添加额外的处理代码
            Log.d(TAG, "onConfigurationChanged: 我是横屏");

        }
        else if (newConfig.orientation==Configuration.ORIENTATION_PORTRAIT) {
            //当前为竖屏， 在此处添加额外的处理代码
            Log.d(TAG, "onConfigurationChanged: 我是竖屛");
        }


    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        if (video_operation.getVisibility()==View.GONE&&operation_top.getVisibility()==View.GONE){
            video_operation.setVisibility(View.VISIBLE);
            operation_top.setVisibility(View.VISIBLE);
            operation.setVisibility(View.GONE);
        }else {
            video_operation.setVisibility(View.GONE);
            operation_top.setVisibility(View.GONE);
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        float qishi_position_X = motionEvent.getX();
        float qishi_position_Y = motionEvent.getY();
        float jiesu_position_X = motionEvent1.getRawX();
        float jiesu_position_Y = motionEvent1.getRawY();
        float length_X = Math.abs(jiesu_position_X - qishi_position_X);
        float length_Y = Math.abs(jiesu_position_Y - qishi_position_Y);

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        width = outMetrics.widthPixels;
        height = outMetrics.heightPixels;

        if (width/4<qishi_position_X&&qishi_position_X<3*width/4&&length_X>length_Y){
            int progress = seekBar.getProgress();
            int kuaijin=(int)length_X*20;
            if (qishi_position_X<jiesu_position_X){
                videoView.seekTo(progress + kuaijin);
            }else {
                videoView.seekTo(progress-kuaijin);
            }
        }else{;
            if (qishi_position_X>3*width/4&&length_Y>length_X){
                AudioManager am=(AudioManager)getSystemService(this.AUDIO_SERVICE);
                int MaxVolum=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                int Volum=am.getStreamVolume(AudioManager.STREAM_MUSIC);
                float percent=length_Y/(height*(float)1.5);
                float fchangeVolum=MaxVolum*percent;
                if(fchangeVolum>MaxVolum){
                    fchangeVolum=MaxVolum;
                }else if (fchangeVolum<0){
                    fchangeVolum=0;
                }
                int ichangeVolum=(int)fchangeVolum;
                if (qishi_position_Y>jiesu_position_Y){
                    am.setStreamVolume(AudioManager.STREAM_MUSIC,Volum+ichangeVolum,AudioManager.FLAG_SHOW_UI);
                }else {
                    am.setStreamVolume(AudioManager.STREAM_MUSIC,Volum-ichangeVolum,AudioManager.FLAG_SHOW_UI);
                }
            }else if (qishi_position_X<width/4&&length_Y>length_X){
                int value = 0;
                ContentResolver cr = this.getContentResolver();
                try {
                    value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
                } catch (Settings.SettingNotFoundException e) {

                }
                float changLight=length_Y/height;
                WindowManager.LayoutParams lpa = this.getWindow().getAttributes();
                if (qishi_position_Y>jiesu_position_Y){
                    lpa.screenBrightness=+changLight;
                    this.getWindow().setAttributes(lpa);
                }else {
                    lpa.screenBrightness=-changLight;
                    this.getWindow().setAttributes(lpa);
                }



            }


        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        onTouchEvent(motionEvent);
        return gestureDetector.onTouchEvent(motionEvent);
    }
}
