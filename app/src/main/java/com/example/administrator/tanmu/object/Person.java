package com.example.administrator.tanmu.object;


import cn.bmob.v3.BmobUser;

/**
 * Created by andrew on 2017/9/8.
 */

public class Person extends BmobUser {
    private Boolean sex;
    private String nick;
    private Integer age;

    public boolean getSex() {
        return this.sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}