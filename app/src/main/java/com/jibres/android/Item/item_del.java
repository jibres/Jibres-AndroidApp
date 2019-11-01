package com.jibres.android.Item;

public class item_del {
    String name,text,day,plus,sex,id;
    boolean last_liked;

    public item_del(String name, String text, String day, String plus, String sex, String id, boolean last_liked) {
        this.name = name;
        this.text = text;
        this.day = day;
        this.plus = plus;
        this.sex = sex;
        this.id = id;
        this.last_liked = last_liked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPlus() {
        return plus;
    }

    public void setPlus(String plus) {
        this.plus = plus;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLast_liked() {
        return last_liked;
    }

    public void setLast_liked(boolean last_liked) {
        this.last_liked = last_liked;
    }
}
