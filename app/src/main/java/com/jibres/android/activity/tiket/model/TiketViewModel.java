package com.jibres.android.activity.tiket.model;

public class TiketViewModel {
    String avatar,title,massage,time;

    public TiketViewModel(String avatar, String title, String massage, String time) {
        this.avatar = avatar;
        this.title = title;
        this.massage = massage;
        this.time = time;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
