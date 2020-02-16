package com.jibres.android.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class JsonManager extends ContextWrapper {

    public static String json_splash_defult = "{\"logo\": \"https://jibres.com/static/logo/icon-white/png/Jibres-Logo-icon-white-1024.png\", \"theme\": \"Jibres\", \"title\": \"Jibres\", \"desc\": \"Sell and Enjoy\", \"meta\": \"Powered by Ermile\", \"sleep\": 3000, \"bg\": {\"from\": \"#c80a5a\", \"to\": \"#c80a5a\"}, \"color\": {\"primary\": \"#ffffff\", \"secondary\": \"#eeeeee\"} }";
    public static String json_intro_defult = "{\"page\": [{\"title\": \"Hello\", \"desc\": \"Welcome to Jibres world\", \"image\": \"https://jibres.com/static/logo/icon-white/png/Jibres-Logo-icon-white-1024.png\"}, {\"title\": \"Sell and Enjoy\", \"desc\": \"All-in-one ecommerce platform\"}, {\"title\": \"Sometimes you need a big change.\", \"image\": \"https://jibres.com/static/logo/icon-white/png/Jibres-Logo-icon-white-1024.png\"}, [] ], \"translation\": {\"next\": \"Next\", \"prev\": \"Prev\", \"skip\": \"Skip\", \"start\": \"Get Start\"}, \"theme\": \"Jibres\", \"bg\": {\"from\": \"#c80a5a\", \"to\": \"#c80a5a\"}, \"color\": {\"primary\": \"#ffffff\", \"secondary\": \"#eeeeee\"} }";

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public static final String SH_PREF_NAME = "ShPerfManager_Jibres_JsonManager";

    @SuppressLint("CommitPrefEdits")
    private JsonManager(Context context) {
        super(context);
        sharedPreferences = getSharedPreferences(SH_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public static JsonManager context(Context context) {
        return new JsonManager(context);
    }

    /** App Info */
    public static final String json_splash = "json_splash";
    public static final String json_intro = "json_intro";

    public void setJsonSplash(String json) {
        editor.putString(JsonManager.json_splash, json);
        editor.apply();
    }
    public void setJsonIntro(String json) {
        editor.putString(JsonManager.json_intro, json);
        editor.apply();
    }

    public Map<String, String> getJson() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(json_intro, sharedPreferences.getString(json_intro,json_intro_defult));
        hashMap.put(json_splash, sharedPreferences.getString(json_splash,json_splash_defult));
        return hashMap;
    }

    public static String getJsonSplash(Context context){
        return JsonManager
                .context(context)
                .getJson()
                .get(JsonManager.json_splash);
    }
    public static String getJsonIntro(Context context){
        return JsonManager
                .context(context)
                .getJson()
                .get(JsonManager.json_intro);
    }


}
