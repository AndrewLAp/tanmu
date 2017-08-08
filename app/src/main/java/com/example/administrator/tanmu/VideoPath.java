package com.example.administrator.tanmu;

import android.view.View;

/**
 * Created by Administrator on 2017/7/24.
 */

public class VideoPath {
    String videoPath;
    String videoName;
    Boolean isCheck=false;
    public int isVisibility= View.GONE;
    public String getVideoPath(){
        return videoPath;
    }
    public void setVideoPath(String path){
        this.videoPath=path;
    }
    public String getVideoName(){
        return videoName;
    }
    public void setVideoName(String name){
        this.videoName=name;
    }
    public Boolean getIsCheck(){
        return isCheck;
    }
    public void setIsCheck(Boolean isCheck){
        this.isCheck=isCheck;
    }


}
