package com.example.administrator.tanmu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.administrator.tanmu.R;

/**
 * Created by andrew on 2017/8/5.
 */

public class SlidingButtonView extends HorizontalScrollView {
    private int mScrollWidth;
    private IonSlidingButtonListener mIonSlidingButtonListener;
    private Boolean isOpen = false;
    private Boolean once = false;
    private TextView mTextView_Delete;

    public SlidingButtonView(Context context) {
        super(context);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public SlidingButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    public SlidingButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(!once){
            mTextView_Delete = (TextView) findViewById(R.id.tv_delete);
            once = true;
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            this.scrollTo(0,0);
            //获取水平滚动条可以滑动的范围，即右侧按钮的宽度
            mScrollWidth = mTextView_Delete.getWidth();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                changeScrollx();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }



    /**
     * 按滚动条被拖动距离判断关闭或打开菜单
     */
    public void changeScrollx(){
        if(getScrollX() >= (mScrollWidth/2)){
            this.smoothScrollTo(mScrollWidth, 0);
            this.mIonSlidingButtonListener.onMenuIsOpen(this);
            isOpen = true;
        }else{
            this.smoothScrollTo(0, 0);
            isOpen = false;
        }
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mTextView_Delete.setTranslationX(l - mScrollWidth);
    }


    public void openMenu()
    {
        if (isOpen){
            return;
        }
        this.smoothScrollTo(mScrollWidth, 0);
        isOpen = true;
        mIonSlidingButtonListener.onMenuIsOpen(this);
    }

    public void closeMenu()
    {
        if (!isOpen){
            return;
        }
        this.smoothScrollTo(0, 0);
        isOpen = false;
    }

    public void setSlidingButtonListener(IonSlidingButtonListener listener){
        mIonSlidingButtonListener = listener;
    }

    public interface IonSlidingButtonListener{
        void onMenuIsOpen(SlidingButtonView view);
    }

}
