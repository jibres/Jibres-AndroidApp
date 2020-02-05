package com.jibres.android.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class UrlManager {
    private static String api = "https://core.jibres.com/r10";
    private static String android = "/android";

    public static String endPoint(Context context){
        return save.context(context).getstring_appINFO().get(save.endPoint_sh);
    }
    public static String android(Context context){
        return endPoint(context)+android;
    }

    /** URL */
    public static String update(Context context){
        return save.context(context).getstring_appINFO().get(save.update);
    }
    public static String language(Context context){
        return save.context(context).getstring_appINFO().get(save.language);
    }
    public static String splash(Context context){
        return save.context(context).getstring_appINFO().get(save.splash);
    }
    public static String intro(Context context){
        return save.context(context).getstring_appINFO().get(save.intro);
    }
    public static String dashboard(Context context){
        return save.context(context).getstring_appINFO().get(save.dashboard);
    }
    public static String menu(Context context){
        return save.context(context).getstring_appINFO().get(save.menu);
    }
    public static String ad(Context context){
        return save.context(context).getstring_appINFO().get(save.ad);
    }

    /** Save url */
    public static class save extends ContextWrapper {

        SharedPreferences.Editor editor;
        SharedPreferences sharedPreferences;
        public static final String SH_PREF_NAME = "SAVE_URL_JIBRES";

        @SuppressLint("CommitPrefEdits")
        private save(Context context) {
            super(context);
            sharedPreferences = getSharedPreferences(SH_PREF_NAME, MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        public static save context(Context context) {
            return new save(context);
        }

        public static final String endPoint_sh = "endPoint_sh";

        public static final String update = "update";
        public static final String language = "language";
        public static final String splash = "splash";
        public static final String intro = "intro";
        public static final String dashboard = "dashboard";
        public static final String menu = "menu";
        public static final String ad = "ad";

        public void save_endPoint(String url ) {
            if (url!=null){
                editor.putString(endPoint_sh, url);
                editor.apply();
            }
        }
        public void save_url(String Update,String Language,String Splash,String Intro,String Homepage,String Menu,String Ad ) {
            if (Update!=null){
                editor.putString(update, Update);
            }
            if (Language!=null){
                editor.putString(language, Language);
            }
            if (Splash!=null){
                editor.putString(splash, Splash);
            }
            if (Intro!=null){
                editor.putString(intro, Intro);
            }
            if (Homepage!=null){
                editor.putString(dashboard, Homepage);
            }
            if (Menu!=null){
                editor.putString(menu, Menu);
            }
            if (Ad!=null){
                editor.putString(ad, Ad);
            }
            editor.apply();
        }

        public Map<String, String> getstring_appINFO() {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(endPoint_sh, sharedPreferences.getString(endPoint_sh, api ));

            hashMap.put(update, sharedPreferences.getString(update,api+android+"/"+update ));
            hashMap.put(language, sharedPreferences.getString(language, api+android+"/"+language ));
            hashMap.put(splash, sharedPreferences.getString(splash, api+android+"/"+splash ));
            hashMap.put(intro, sharedPreferences.getString(intro, api+android+"/"+intro ));
            hashMap.put(dashboard, sharedPreferences.getString(dashboard, api+android+"/"+ dashboard));
            hashMap.put(menu, sharedPreferences.getString(menu, api+android+"/"+menu ));
            hashMap.put(ad, sharedPreferences.getString(ad, api+android+"/"+ad ));
            return hashMap;
        }
    }

    public static void save_endPoint(Context context,String url){
        save.context(context).save_endPoint(url);
    }
}
