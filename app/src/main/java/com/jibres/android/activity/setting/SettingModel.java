package com.jibres.android.activity.setting;

public class SettingModel {

    int onClick,imageDrawble;
    String image,title,summery,desc;
    boolean showFlesh;

    public SettingModel(int onClick, int imageDrawble, String imageUrl, String title, String summery, String desc, boolean showFlesh) {
        this.onClick = onClick;
        this.imageDrawble = imageDrawble;
        this.image = imageUrl;
        this.title = title;
        this.summery = summery;
        this.desc = desc;
        this.showFlesh = showFlesh;
    }

    public int getOnClick() {
        return onClick;
    }

    public void setOnClick(int onClick) {
        this.onClick = onClick;
    }

    public int getImageDrawble() {
        return imageDrawble;
    }

    public void setImageDrawble(int imageDrawble) {
        this.imageDrawble = imageDrawble;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isShowFlesh() {
        return showFlesh;
    }

    public void setShowFlesh(boolean showFlesh) {
        this.showFlesh = showFlesh;
    }
}
