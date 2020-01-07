package com.jibres.android.function;

import android.content.Context;
import android.util.Log;

import com.jibres.android.activity.language.LanguageManager;
import com.jibres.android.api.Api;
import com.jibres.android.managers.JsonManager;
import com.jibres.android.managers.UrlManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetAppDetail {

    public GetAppDetail(Context context, Listener listener) {
        Api.getAppDetail(context, (status, value) -> {
            if (status && value != null && value.length()>20){
                try {
                    JSONObject result = new JSONObject(value);
                    listener.onReceived(true);
                    if (!result.isNull("lang_list")){
                        JSONObject lang_list = result.getJSONObject("lang_list");
                        LanguageManager.context(context).setJsonLanguage(String.valueOf(lang_list));
                    }
                    if (!result.isNull("version")){}
                    if (!result.isNull("intro")){
                        JSONArray intro = result.getJSONArray("intro");
                        JsonManager.context(context).setJsonIntro(String.valueOf(intro));
                        Log.d("amingoli", UrlManager.get.app_detail(context) +"GetAppDetail: (intro) "+intro);

                    }
                    if (!result.isNull("url")){}
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onReceived(false);
                }
            }else {
                listener.onReceived(false);
            }
        });
    }

    public interface Listener{
        void onReceived(boolean status);
    }
}
