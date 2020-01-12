package com.jibres.android.activity.tiket.model;

public class TiketListModel {
    String id,title,content,status,avatar,displayname;
    boolean solved;

    public TiketListModel(String id, String title, String content, String status, String avatar, String displayname, boolean solved) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.avatar = avatar;
        this.displayname = displayname;
        this.solved = solved;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}
