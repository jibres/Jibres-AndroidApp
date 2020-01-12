package com.jibres.android.managers;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.jibres.android.BuildConfig;

import java.util.HashMap;
import java.util.Map;

public class AppManager extends ContextWrapper {

  public static String versionName = BuildConfig.VERSION_NAME;
  public static int versionCode = BuildConfig.VERSION_CODE;

  SharedPreferences.Editor editor;
  SharedPreferences sharedPreferences;
  public static final String SH_PREF_NAME = "ShPerfManager_Jibres_UserManager";

  @SuppressLint("CommitPrefEdits")
  private AppManager(Context context) {
    super(context);
    sharedPreferences = getSharedPreferences(SH_PREF_NAME, MODE_PRIVATE);
    editor = sharedPreferences.edit();
  }
  public static AppManager get(Context context) {
    return new AppManager(context);
  }

  /** App Info */
  public static final String splash = "splash";

  public static final String look_status = "look_status";
  public static final String pin_code = "pin_code";

  public static final String store = "store";

  public static final String apikey = "apikey";
  public static final String userCode = "userCode";
  public static final String zonId = "zonId";
  public static final String mobile = "mobile";

  public static final String version_update = "version_update";
  public static final String version_deprecated = "version_deprecated";


  public void save_splash(int id) {
    editor.putInt(splash, id);
    editor.apply();
  }
  public void save_lookStatus(int status) {
    editor.putInt(look_status, status);
    editor.apply();
  }

  public void save_pinCode(String pinCode) {
    editor.putString(pin_code, pinCode);
    editor.apply();
  }

  public void saveStore(String STORE) {
    editor.putString( store, STORE);
    editor.apply();
  }

  public void saveUserInfo(String Apikey, String UserCode, String ZonId, String Mobile) {
    editor.putString( apikey, Apikey);
    editor.putString( userCode, UserCode );
    editor.putString( zonId, ZonId);
    editor.putString( mobile, Mobile);
    editor.apply();
  }

  public void setUpdate(Boolean hasUpdate) {
    editor.putBoolean(version_update, hasUpdate);
    editor.apply();
  }

  public void setDeprecated(Boolean isDeprecated) {
    editor.putBoolean(version_deprecated, isDeprecated);
    editor.apply();
  }


  public Map<String, String> getUserInfo() {
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put(store, sharedPreferences.getString(store, "y885"));
    hashMap.put(apikey, sharedPreferences.getString(apikey, null));
    hashMap.put(userCode, sharedPreferences.getString(userCode, null));
    hashMap.put(zonId, sharedPreferences.getString(zonId, null));
    hashMap.put(mobile, sharedPreferences.getString(mobile, null));

    hashMap.put(pin_code, sharedPreferences.getString(pin_code, null));
    return hashMap;
  }
  public Map<String, Integer> getUserInfo_int() {
    HashMap<String, Integer> hashMap = new HashMap<>();
    hashMap.put(splash, sharedPreferences.getInt(splash, 0));
    hashMap.put(look_status, sharedPreferences.getInt(look_status, 0));
    return hashMap;
  }

  public Map<String, Boolean> getUserInfo_boolean() {
    HashMap<String, Boolean> hashMap = new HashMap<>();
    hashMap.put(version_deprecated, sharedPreferences.getBoolean(version_deprecated, false));
    hashMap.put(version_update, sharedPreferences.getBoolean(version_update, false));
    return hashMap;
  }

  public static Integer getSplash(Context context){
    return AppManager.get(context).getUserInfo_int().get(AppManager.splash);
  }

  public static Integer getLookStatus(Context context){
    return AppManager.get(context).getUserInfo_int().get(AppManager.look_status);
  }
  public static String getPinCode(Context context){
    return AppManager.get(context).getUserInfo().get(AppManager.pin_code);
  }

  public static String getStore(Context context){
    return AppManager.get(context).getUserInfo().get(AppManager.store);
  }

  public static String getApikey(Context context){
    return AppManager.get(context).getUserInfo().get(AppManager.apikey);
  }
  public static String getUserCode(Context context){
    return AppManager.get(context).getUserInfo().get(AppManager.userCode);
  }
  public static String getZonId(Context context){
    return AppManager.get(context).getUserInfo().get(AppManager.zonId);
  }
  public static String getMobile(Context context){
    return AppManager.get(context).getUserInfo().get(AppManager.mobile);
  }

  public static Boolean hasUpdate(Context context){
    return AppManager.get(context).getUserInfo_boolean().get(AppManager.version_update);
  }
  public static Boolean isDeprecated(Context context){
    return AppManager.get(context).getUserInfo_boolean().get(AppManager.version_deprecated);
  }

}


