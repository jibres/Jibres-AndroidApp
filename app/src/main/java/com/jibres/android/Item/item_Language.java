package com.jibres.android.Item;

public class item_Language {

    private String title;
    private String tag;
    private boolean chBoxVisibel;

    public item_Language(String title, String tag, boolean chBoxVisibel) {
        this.title = title;
        this.tag = tag;
        this.chBoxVisibel = chBoxVisibel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isChBoxVisibel() {
        return chBoxVisibel;
    }

    public void setChBoxVisibel(boolean chBoxVisibel) {
        this.chBoxVisibel = chBoxVisibel;
    }
}
