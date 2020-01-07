package com.jibres.android;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SaveManager extends ContextWrapper {

  SharedPreferences.Editor editor;
  SharedPreferences sharedPreferences;
  public static final String SH_PREF_NAME = "ShPerfManager_Payamres";


  @SuppressLint("CommitPrefEdits")
  private SaveManager(Context context) {
    super(context);
    sharedPreferences = getSharedPreferences(SH_PREF_NAME, MODE_PRIVATE);
    editor = sharedPreferences.edit();
  }
  public static SaveManager get(Context context) {
    return new SaveManager(context);
  }


  /** App Info */
  public static final String splash = "splash";
  public void save_splash(int id) {
    editor.putInt(splash, id);
    editor.apply();
  }

  public Map<String, Integer> getIntValue() {
    HashMap<String, Integer> hashMap = new HashMap<>();
    hashMap.put(splash, sharedPreferences.getInt(splash, 0 ));

    return hashMap;
  }


}


