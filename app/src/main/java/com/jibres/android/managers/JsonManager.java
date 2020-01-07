package com.jibres.android.managers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class JsonManager extends ContextWrapper {
    public static String intro_en = "\"intro\": [{\"title\": \"Jibres\", \"desc\": \"Jibres is not just an online accounting software; We try to create the best financial platform that has everything you need to sale and manage your financial life.\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": \"https://jibres.com/static/img/logo/icon/svg/Jibres-Logo-icon.svg\", \"btn\": [{\"title\": \"Next\", \"action\": \"next\"} ] }, {\"title\": \"Easy\", \"desc\": \"Easy to use\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": \"https://jibres.com/static/img/logo/icon/svg/Jibres-Logo-icon.svg\", \"btn\": [{\"title\": \"Prev\", \"action\": \"prev\"}, {\"title\": \"Next\", \"action\": \"next\"} ] }, {\"title\": \"Powerful\", \"desc\": \"Best application\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": \"https://jibres.com/static/img/logo/icon/svg/Jibres-Logo-icon.svg\", \"btn\": [{\"title\": \"Prev\", \"action\": \"prev\"}, {\"title\": \"Next\", \"action\": \"next\"} ] }, {\"title\": \"Enjoy\", \"desc\": \"Welcome to our collection\", \"bg_from\": \"#ffffff\", \"bg_to\": \"#ffffff\", \"title_color\": \"#000000\", \"desc_color\": \"#000000\", \"image\": \"https://jibres.com/static/img/logo/icon/svg/Jibres-Logo-icon.svg\", \"btn\": [{\"title\": \"Start\", \"action\": \"start\"} ] } ]";

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public static final String SH_PREF_NAME = "ShPerfManager_Jibres_JsonManager";

    @SuppressLint("CommitPrefEdits")
    private JsonManager(Context context) {
        super(context);
        sharedPreferences = getSharedPreferences(SH_PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public static JsonManager get(Context context) {
        return new JsonManager(context);
    }

    /** App Info */
    public static final String jsonIntro = "jsonIntro";

    public void setJsonIntro(String json) {
        editor.putString(jsonIntro, json);
        editor.apply();
    }

    public Map<String, String> getJsonIntro_fromSaveManager() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(jsonIntro, sharedPreferences.getString(jsonIntro,intro_en));
        return hashMap;
    }

    public static String getJsonIntro(Context context){
        return JsonManager
                .get(context)
                .getJsonIntro_fromSaveManager()
                .get(JsonManager.jsonIntro);
    }


}
