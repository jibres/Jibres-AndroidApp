package com.jibres.android.activity.language;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class LanguageManager extends ContextWrapper {
  private String defaultValue = "{\"fa\": {\"name\": \"fa\", \"direction\": \"rtl\", \"iso\": \"fa_IR\", \"localname\": \"فارسی\", \"country\": [\"Iran\"] }, \"en\": {\"name\": \"en\", \"direction\": \"ltr\", \"iso\": \"en_US\", \"localname\": \"English\", \"country\": [\"United Kingdom\", \"United States\"] } }";

  SharedPreferences.Editor editor;
  SharedPreferences sharedPreferences;
  public static final String SH_PREF_NAME = "ShPerfManager_Jibres_LanguageManager";

  @SuppressLint("CommitPrefEdits")
  private LanguageManager(Context context) {
    super(context);
    sharedPreferences = getSharedPreferences(SH_PREF_NAME, MODE_PRIVATE);
    editor = sharedPreferences.edit();
  }
  public static LanguageManager context(Context context) {
    return new LanguageManager(context);
  }

  /** App Info */
  public static final String jsonLanguage = "jsonLanguage";
  public static final String appLanguage = "appLanguage";

  public void setJsonLanguage(String json) {
    editor.putString(jsonLanguage, json);
    editor.apply();
  }

  public void setAppLanguage(String languageLocal) {
    editor.putString(appLanguage, languageLocal);
    editor.apply();
  }

  public Map<String, String> getAppLanguage_fromSaveManager() {
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put(appLanguage, sharedPreferences.getString(appLanguage, "en" ));
    hashMap.put(jsonLanguage, sharedPreferences.getString(jsonLanguage, defaultValue ));
    return hashMap;
  }


  public static String getAppLanguage(Context context){
    return LanguageManager
            .context(context)
            .getAppLanguage_fromSaveManager()
            .get(LanguageManager.appLanguage);
  }
  public static String getJsonLanguage(Context context){
    return LanguageManager
            .context(context)
            .getAppLanguage_fromSaveManager()
            .get(LanguageManager.jsonLanguage);
  }
}


