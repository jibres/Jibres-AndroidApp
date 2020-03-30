package com.jibres.android.activity.intro;

public class IntroModel {
    private String image, title, subTitle, desc, bg_color_start, bg_color_end, colot_title, colot_desc = null;
    private int style = 1;

    public IntroModel(String image, String title, String subTitle, String desc, String bg_color_start, String bg_color_end, String colot_title, String colot_desc, int style) {
        this.image = image;
        this.title = title;
        this.subTitle = subTitle;
        this.desc = desc;
        this.bg_color_start = bg_color_start;
        this.bg_color_end = bg_color_end;
        this.colot_title = colot_title;
        this.colot_desc = colot_desc;
        this.style = style;
    }

    public IntroModel(String title, String subTitle, String desc, String bg_color_start, String bg_color_end, String colot_title, String colot_desc) {
        this.style = 1;
        this.title = title;
        this.subTitle = subTitle;
        this.desc = desc;
        this.bg_color_start = bg_color_start;
        this.bg_color_end = bg_color_end;
        this.colot_title = colot_title;
        this.colot_desc = colot_desc;
    }

    public IntroModel(String image, String title, String subTitle, String desc, String bg_color_start, String bg_color_end, String colot_title, String colot_desc) {
        this.style = 2;
        this.image = image;
        this.title = title;
        this.subTitle = subTitle;
        this.desc = desc;
        this.bg_color_start = bg_color_start;
        this.bg_color_end = bg_color_end;
        this.colot_title = colot_title;
        this.colot_desc = colot_desc;
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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBg_color_start() {
        return bg_color_start;
    }

    public void setBg_color_start(String bg_color_start) {
        this.bg_color_start = bg_color_start;
    }

    public String getBg_color_end() {
        return bg_color_end;
    }

    public void setBg_color_end(String bg_color_end) {
        this.bg_color_end = bg_color_end;
    }

    public String getColot_title() {
        return colot_title;
    }

    public void setColot_title(String colot_title) {
        this.colot_title = colot_title;
    }

    public String getColot_desc() {
        return colot_desc;
    }

    public void setColot_desc(String colot_desc) {
        this.colot_desc = colot_desc;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }
}
