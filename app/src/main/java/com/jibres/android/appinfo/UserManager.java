package com.jibres.android.appinfo;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import com.jibres.android.BuildConfig;

import java.util.HashMap;
import java.util.Map;

public class UserManager extends ContextWrapper {

  SharedPreferences.Editor editor;
  SharedPreferences sharedPreferences;
  public static final String SH_PREF_NAME = "ShPerfManager_Jibres_UserManager";

  @SuppressLint("CommitPrefEdits")
  private UserManager(Context context) {
    super(context);
    sharedPreferences = getSharedPreferences(SH_PREF_NAME, MODE_PRIVATE);
    editor = sharedPreferences.edit();
  }
  public static UserManager get(Context context) {
    return new UserManager(context);
  }

  /** App Info */
  public static final String store = "store";

  public static final String apikey = "apikey";
  public static final String userCode = "userCode";
  public static final String zonId = "zonId";
  public static final String mobile = "mobile";

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

  public Map<String, String> getUserInfo() {
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put(store, sharedPreferences.getString(store, "y885"));
    hashMap.put(apikey, sharedPreferences.getString(apikey, null));
    hashMap.put(userCode, sharedPreferences.getString(userCode, null));
    hashMap.put(zonId, sharedPreferences.getString(zonId, null));
    hashMap.put(mobile, sharedPreferences.getString(mobile, null));

    return hashMap;
  }

  public static String versionName = BuildConfig.VERSION_NAME;
  public static int versionCode = BuildConfig.VERSION_CODE;

  public static String getStore(Context context){
    return UserManager.get(context).getUserInfo().get(UserManager.store);
  }

  public static String getApikey(Context context){
    return UserManager.get(context).getUserInfo().get(UserManager.apikey);
  }
  public static String getUserCode(Context context){
    return UserManager.get(context).getUserInfo().get(UserManager.userCode);
  }
  public static String getZonId(Context context){
    return UserManager.get(context).getUserInfo().get(UserManager.zonId);
  }
  public static String getMobile(Context context){
    return UserManager.get(context).getUserInfo().get(UserManager.mobile);
  }

}


