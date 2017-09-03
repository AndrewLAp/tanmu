package com.example.administrator.tanmu.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.tanmu.R;
import com.example.administrator.tanmu.adapter.AdapterMain;
import com.example.administrator.tanmu.object.Yingyong;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 2017/8/13.
 */

public class LayoutTuijian extends Fragment {
    private AdapterMain myAdapter;
    private List<Integer> mdata=new ArrayList<>();
    private RecyclerView recyclerView;
    private Context context;
    View layoutTUijian;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutTUijian = inflater.inflate(R.layout.layout_tuijian, container, false);

        context=getContext();
        recyclerView=(RecyclerView)layoutTUijian.findViewById(R.id.recycler_main);

        intData();
        myAdapter=new AdapterMain(mdata,context);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(context,6);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);


        return layoutTUijian;
    }

    private void intData(){
        mdata.clear();
        for (int i=0;i<12;i++){
            mdata.add(i);
        }

    }

}
