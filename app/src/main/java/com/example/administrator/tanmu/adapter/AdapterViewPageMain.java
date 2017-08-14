package com.example.administrator.tanmu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 2017/8/13.
 */

public class AdapterViewPageMain extends FragmentPagerAdapter {
    private List<Fragment> fragmentList=new ArrayList<>();

    public AdapterViewPageMain(FragmentManager fm) {
        super(fm);
    }
    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
