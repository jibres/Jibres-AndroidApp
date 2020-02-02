package com.jibres.android.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

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

    /** App Info */
    public static final String json_splash = "json_splash";

    public void setJsonIntros(String json) {
        editor.putString(JsonManager.json_splash, json);
        editor.apply();
    }

    public Map<String, String> getJsonIntro_fromSaveManager() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(json_splash, sharedPreferences.getString(json_splash,null));
        return hashMap;
    }

    public static String getJsonIntro(Context context){
        return JsonManager
                .context(context)
                .getJsonIntro_fromSaveManager()
                .get(JsonManager.json_splash);
    }


}
