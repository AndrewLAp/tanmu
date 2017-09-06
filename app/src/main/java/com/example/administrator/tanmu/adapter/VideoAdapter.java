package com.example.administrator.tanmu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.tanmu.R;
import com.example.administrator.tanmu.object.VideoPath;
import com.example.administrator.tanmu.util.Utils;
import com.example.administrator.tanmu.view.SlidingButtonView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> implements SlidingButtonView.IonSlidingButtonListener{

    private Context mContext;

    private List<VideoPath> mDatas = new ArrayList();

    private SlidingButtonView mMenu = null;

    private onMyClick onMyClick;







    public VideoAdapter(Context context,List<VideoPath> list) {
        mContext = context;
        mDatas=list;
    }

    @Override
    public VideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final VideoAdapter.MyViewHolder holder, final int position) {
        final VideoPath videoPath=mDatas.get(position);
        holder.textView.setText(videoPath.getVideoName());
        holder.checkBox.setVisibility(videoPath.isVisibility);
        holder.checkBox.setChecked(videoPath.getIsCheck());
        //设置内容布局的宽为屏幕宽度
        holder.layout_content.getLayoutParams().width = Utils.getScreenWidth(mContext);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    closeMenu();//关闭菜单
                }
                onMyClick.Click(v,position,videoPath.getIsCheck());


            }
        });

        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                onMyClick.longClick(view,position);

                return true;
            }
        });

        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file=new File(videoPath.getVideoPath());
                if (file.exists()){
                    file.delete();
                    mDatas.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(0,mDatas.size());
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onMenuIsOpen(SlidingButtonView view) {
        mMenu = view;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        mMenu.closeMenu();
        mMenu = null;

    }

    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if(mMenu != null){
            return true;
        }
        return false;
    }

    public void setOnMyClick(onMyClick onMyClick){
        this.onMyClick=onMyClick;
    }

    public interface onMyClick{
        void longClick(View view,int position);
        void Click(View view,int position,Boolean isCheck);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView btn_Delete;
        public TextView textView;
        public ViewGroup layout_content;
        public CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn_Delete = (TextView) itemView.findViewById(R.id.tv_delete);
            textView = (TextView) itemView.findViewById(R.id.video_path);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            layout_content = (ViewGroup) itemView.findViewById(R.id.layout_content);

            ((SlidingButtonView) itemView).setSlidingButtonListener(VideoAdapter.this);
        }
    }

}