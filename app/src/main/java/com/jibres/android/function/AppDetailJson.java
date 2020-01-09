package com.jibres.android.function;

import android.content.Context;

import com.jibres.android.activity.language.LanguageManager;
import com.jibres.android.api.Api;

import org.json.JSONException;
import org.json.JSONObject;

import static com.jibres.android.managers.AppManager.versionCode;

public class AppDetailJson {

    public AppDetailJson(Context context, Listener listener) {
        Api.getAppDetail(context, (status, value) -> {
            if (value != null && value.length()>20){
                try {
                    JSONObject result = new JSONObject(value);
                    if (!result.isNull("version")){
                        JSONObject version = result.getJSONObject("version");
                        if (!version.isNull("deprecated") &&
                                versionCode <= version.getInt("deprecated")) {
                            listener.isDeprecated();
                        }else {
                            if (!version.isNull("last") &&
                                    versionCode < version.getInt("last")){
                                listener.onReceived(true);
                            }else {
                                listener.onReceived(false);
                            }
                        }
                    }
                    if (!result.isNull("lang_list")){
                        JSONObject lang_list = result.getJSONObject("lang_list");
                        LanguageManager.context(context).setJsonLanguage(String.valueOf(lang_list));
                    }

                    if (!result.isNull("url")){}
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onFiled(true);
                }
            }else {
                listener.onFiled(false);
            }
        });
    }

    public interface Listener{
        void isDeprecated();
        void onReceived(boolean hasUpdate);
        void onFiled(boolean hasInternet);
    }
}
