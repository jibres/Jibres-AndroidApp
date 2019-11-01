package com.jibres.android.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.jibres.android.R;
import com.jibres.android.Static.value;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckVersion {
    public static Boolean Deprecated(Activity activity,Context context, String respones){
        return false;
        /*try {
            JSONObject respone = new JSONObject(respones);
            JSONObject result = respone.getJSONObject("result");
            JSONObject url = result.getJSONObject("url");
            JSONObject version = result.getJSONObject("version");
            *//*Url For Update*//*
            final String urlUpdate = url.getString("update");
            *//*Deprecate Value*//*
            int deprecatedVersion = version.getInt("deprecated");
            String deprecated_title = version.getString("deprecated_title");
            String deprecated_desc = version.getString("deprecated_desc");
            String deprecated_btnTitle = context.getString(R.string.update_now);
            *//*Update Value*//*
            int lastVersion = version.getInt("last");
            String update_title = version.getString("update_title");
            String update_desc = version.getString("update_desc");
            if (value.versionCode <= deprecatedVersion){
                SaveManager.get(context).change_deprecatedVersion(true);
                Intent openURL = new Intent ( Intent.ACTION_VIEW );
                openURL.setData (Uri.parse( urlUpdate ));
                new Dialog(activity,deprecated_title,deprecated_desc,deprecated_btnTitle,false,openURL);
                return true;

            }else {
                SaveManager.get(context).change_deprecatedVersion(false);
                updateVersion(context,lastVersion);
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }*/
    }

    /*Check Update Version*/
    private static void updateVersion(Context context,int UpdateVersion){
        if (value.versionCode < UpdateVersion){
            SaveManager.get(context).change_hasNewVersion(true);
        }
        else {
            SaveManager.get(context).change_hasNewVersion(false);
        }
    }
}
