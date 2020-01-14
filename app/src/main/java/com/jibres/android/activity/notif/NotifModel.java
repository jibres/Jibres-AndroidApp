package com.jibres.android.activity.notif;

public class NotifModel {
    boolean readUser;
    String name,title,desc,date;

    public NotifModel(boolean readUser, String name, String title, String desc, String date) {
        this.readUser = readUser;
        this.name = name;
        this.title = title;
        this.desc = desc;
        this.date = date;
    }

    public boolean isReadUser() {
        return readUser;
    }

    public void setReadUser(boolean readUser) {
        this.readUser = readUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
