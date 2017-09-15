package com.example.administrator.tanmu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.tanmu.R;

/**
 * Created by andrew on 2017/9/17.
 */

public class AdapterSetting extends RecyclerView.Adapter<AdapterSetting.SettingHodler> {

    private onSettingClick settingClick;

    public AdapterSetting() {
        super();
    }

    @Override
    public SettingHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_setting_item,parent,false);
        SettingHodler hodler=new SettingHodler(view);
        return hodler;
    }

    public class SettingHodler extends RecyclerView.ViewHolder{

        private Button btn;
        public SettingHodler(View itemView) {
            super(itemView);
            btn=(Button) itemView.findViewById(R.id.setting_item);
        }
    }


    @Override
    public void onBindViewHolder(SettingHodler holder, final int position) {
        switch (position){
            case 0:
                holder.btn.setText("退出登陆");
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settingClick.onFirstClick(v,position);

                    }
                });
                break;
            case 1:
                holder.btn.setText("修改密码");
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settingClick.onSecondClick(v,position);
                    }
                });
                break;
        }

    }


    @Override
    public int getItemCount() {
        return 2;
    }

    public void setSettingClick(onSettingClick settingClick){
        this.settingClick=settingClick;
    }

    public interface onSettingClick{
        void onFirstClick(View view,int position);
        void onSecondClick(View view,int position);

    }
}
