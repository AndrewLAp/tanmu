package com.example.administrator.tanmu.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.tanmu.R;

/**
 * Created by andrew on 2017/8/13.
 */

public class LayoutDianying extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutDianying=inflater.inflate(R.layout.layout_dianying, container, false);
        return layoutDianying;
    }
}
