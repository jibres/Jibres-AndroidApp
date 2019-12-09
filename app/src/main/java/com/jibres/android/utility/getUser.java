package com.jibres.android.utility;

import android.content.Context;

public class getUser {

    public static String apiKey(Context context){
        return SaveManager.get(context).getstring_appINFO().get(SaveManager.apiKey);
    }

    public static String zoneID(Context context){
        return SaveManager.get(context).getstring_appINFO().get(SaveManager.zoneID);
    }

    public static String userCode(Context context){
        return SaveManager.get(context).getstring_appINFO().get(SaveManager.userCode);
    }

    public static String mobile(Context context){
        return SaveManager.get(context).getstring_appINFO().get(SaveManager.mobile);
    }
}
