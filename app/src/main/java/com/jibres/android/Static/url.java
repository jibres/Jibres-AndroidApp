package com.jibres.android.Static;

public class url {

    public static String local = "https://jeebres.ir";
    public static String api_v1 = "/api/v1";
    public static String myStore = "/y884";

    public static String curl = local+api_v1+myStore;

    public static String post = curl +"/posts";
    public static String app = curl +"/app";
    public static String language = curl +"/language";
    public static String posts = "/posts";
    public static String salawat = "/salawat";
    public static String news = "/posts/get?id=";
    public static String del = "/delneveshte";
    public static String like_del = "/delneveshte/like";
    public static String send_del = "/delneveshte/add";


    public static String token = curl +"/account/token";
    public static String user_add = curl +"/account/android/add";
    public static String enter = curl +"/account/enter";
    public static String verify = curl +"/account/enter/verify";
}
