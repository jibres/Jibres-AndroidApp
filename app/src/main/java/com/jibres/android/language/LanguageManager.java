package com.jibres.android.language;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class LanguageManager extends ContextWrapper {

  SharedPreferences.Editor editor;
  SharedPreferences sharedPreferences;
  public static final String SH_PREF_NAME = "ShPerfManager_Jibres_LanguageManager";


  @SuppressLint("CommitPrefEdits")
  private LanguageManager(Context context) {
    super(context);
    sharedPreferences = getSharedPreferences(SH_PREF_NAME, MODE_PRIVATE);
    editor = sharedPreferences.edit();
  }
  public static LanguageManager get(Context context) {
    return new LanguageManager(context);
  }

  /** App Info */
  public static final String appLanguage = "appLanguage";

  public void setAppLanguage(String languageLocal) {
    editor.putString(appLanguage, languageLocal);
    editor.apply();
  }

  public Map<String, String> getAppLanguage_fromSaveManager() {
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put(appLanguage, sharedPreferences.getString(appLanguage, "en" ));
    return hashMap;
  }


  public static String getAppLanguage(Context context){
    return LanguageManager
            .get(context)
            .getAppLanguage_fromSaveManager()
            .get(LanguageManager.appLanguage);
  }


}


