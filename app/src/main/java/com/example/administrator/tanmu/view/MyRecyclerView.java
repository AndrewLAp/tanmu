package com.example.administrator.tanmu.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.administrator.tanmu.adapter.AdapterScrollItem;

import java.lang.ref.WeakReference;

/**
 * Created by andrew on 2017/8/11.
 */

public class MyRecyclerView extends RecyclerView {

    private static final long TIME_AUTO_POLL = 5000;
    private static int width;
    private static int height;
    AutoPollTask autoPollTask;
    private boolean running;
    private boolean canRun;

    public MyRecyclerView(Context context) {
        super(context);
        autoPollTask = new AutoPollTask(this);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        autoPollTask = new AutoPollTask(this);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        autoPollTask = new AutoPollTask(this);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        height = this.getHeight();
        width = this.getWidth();
    }

    public void setAdapter(AdapterScrollItem adapter) {
        super.setAdapter(adapter);
        scrollToPosition(adapter.getItemRawCount() * 10000);//开始时的偏移量
    }

    //开启:如果正在运行,先停止->再开启
    public void start() {
        if (running)
            stop();
        canRun = true;
        running = true;
        postDelayed(autoPollTask, TIME_AUTO_POLL);
    }

    public void stop() {
        running = false;
        removeCallbacks(autoPollTask);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (running)
                    stop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (canRun)
                    start();
                break;
        }
        return super.onTouchEvent(e);
    }

    static class AutoPollTask implements Runnable {
        private final WeakReference<MyRecyclerView> mReference;

        //使用弱引用持有外部类引用->防止内存泄漏
        public AutoPollTask(MyRecyclerView reference) {
            this.mReference = new WeakReference<MyRecyclerView>(reference);
        }

        @Override
        public void run() {
            MyRecyclerView recyclerView = mReference.get();
            if (recyclerView != null && recyclerView.running && recyclerView.canRun) {
                recyclerView.smoothScrollBy(width, 0);
                recyclerView.postDelayed(recyclerView.autoPollTask, TIME_AUTO_POLL);
            }
        }
    }

}
