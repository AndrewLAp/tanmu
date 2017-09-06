package com.example.administrator.tanmu.object;

import cn.bmob.v3.BmobObject;

/**
 * Created by andrew on 2017/9/8.
 */

public class Person extends BmobObject {
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}