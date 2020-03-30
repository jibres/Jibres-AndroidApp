package com.jibres.android.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.jibres.android.utility.file.JsonReadFile;

import java.util.HashMap;
import java.util.Map;

public class JsonManager extends ContextWrapper {
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

    /**
     * App Info
     */
    public static final String json_splash = "json_splash";
    public static final String json_intro = "json_intro";

    public void setJsonSplash(String json) {
        editor.putString(json_splash, json);
        editor.apply();
    }

    public void setJsonIntro(String json) {
        editor.putString(json_intro, json);
        editor.apply();
    }

    public Map<String, String> getJson() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(json_intro, sharedPreferences.getString(json_intro, JsonReadFile.intro(this)));
        hashMap.put(json_splash, sharedPreferences.getString(json_splash, JsonReadFile.splash(this)));
        return hashMap;
    }

    public static String getJsonSplash(Context context) {
        return JsonManager
                .context(context)
                .getJson()
                .get(JsonManager.json_splash);
    }

    public static String getJsonIntro(Context context) {
        return JsonManager
                .context(context)
                .getJson()
                .get(JsonManager.json_intro);
    }


}
