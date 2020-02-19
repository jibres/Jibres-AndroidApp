package com.jibres.android.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.jibres.android.JibresApplication;
import com.jibres.android.managers.AppManager;
import com.jibres.android.managers.JsonManager;
import com.jibres.android.managers.UrlManager;
import com.jibres.android.utility.SecretReadFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Api {
    private static String TAG = "amingoli-api";
    public static void endPoint(Context context, ApiListener.connected listener){
        StringRequest request =
                new StringRequest(Request.Method.GET, UrlManager.endPoint(context),
                        response -> {
                            try {
                                JSONObject mainObject = new JSONObject(response);
                                if (mainObject.getBoolean("ok")){
                                    JSONObject result = mainObject.getJSONObject("result");
                                    Iterator<?> keys = result.keys();
                                    while (keys.hasNext()) {
                                        String key = (String) keys.next();
                                        JSONObject lang_key = result.getJSONObject(key);
                                        if (keys.hasNext() && AppManager.getAppLanguage(context) != null){
                                            if (result.get(key) instanceof JSONObject) {
                                                if (AppManager.getAppLanguage(context).equals(key)){
                                                    UrlManager.save_endPoint(
                                                            context,
                                                            lang_key.getString("endpoint"));
                                                    listener.onReceived(true);
                                                    break;
                                                }
                                            }
                                        }else {
                                            if (result.get(key) instanceof JSONObject) {
                                                UrlManager.save_endPoint(
                                                        context,
                                                        lang_key.getString("endpoint"));
                                                listener.onReceived(true);
                                                break;
                                            }
                                        }
                                    }
                                }else {
                                    listener.onReceived(false);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                listener.onReceived(false);
                            }
                        }, e -> listener.onReceived(false))
                {
                    @Override
                    public Map<String, String> getHeaders()  {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("store", SecretReadFile.store(context));
                        return headers;
                    }
                };
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        JibresApplication.getInstance().addToRequestQueue(request);
    }

    public static void android(Context context, ApiListener.connected listener){
        StringRequest request =
                new StringRequest(Request.Method.GET, UrlManager.android(context),
                        response -> {
                            try {
                                String  update = null, language = null, splash = null,
                                        intro = null, homepage = null, menu = null, ad = null;
                                JSONObject mainObject = new JSONObject(response);
                                if (mainObject.getBoolean("ok")){
                                    JSONObject result = mainObject.getJSONObject("result");
                                    JSONObject url = result.getJSONObject("url");
                                    if (!url.isNull("update")){
                                        update = url.getString("update");
                                    }
                                    if (!url.isNull("language")){
                                        language = url.getString("language");
                                    }
                                    if (!url.isNull("splash")){
                                        splash = url.getString("splash");
                                    }
                                    if (!url.isNull("intro")){
                                        intro = url.getString("intro");
                                    }
                                    if (!url.isNull("homepage")){
                                        homepage = url.getString("homepage");
                                    }
                                    if (!url.isNull("menu")){
                                        menu = url.getString("menu");
                                    }
                                    if (!url.isNull("ad")){
                                        ad = url.getString("ad");
                                    }
                                    UrlManager.save.context(context)
                                            .save_url(update,language,splash,intro,homepage,menu,ad);
                                    listener.onReceived(true);

                                }else {
                                    listener.onReceived(false);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                listener.onReceived(false);
                            }
                        }, e -> listener.onReceived(false))
                {
                    @Override
                    public Map<String, String> getHeaders()  {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("store", SecretReadFile.store(context));
                        return headers;
                    }
                };
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        JibresApplication.getInstance().addToRequestQueue(request);
    }

    public static void splash(Context context, ApiListener.connected listener){
        StringRequest request =
                new StringRequest(Request.Method.GET, UrlManager.splash(context),
                        response -> {
                            try {
                                JSONObject mainObject = new JSONObject(response);
                                if (mainObject.getBoolean("ok")){
                                    JSONObject result = mainObject.getJSONObject("result");
                                    JsonManager.context(context)
                                            .setJsonSplash(String.valueOf(result));
                                    listener.onReceived(true);
                                }else {
                                    listener.onReceived(false);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                listener.onReceived(false);
                            }
                        }, e -> listener.onReceived(false))
                {
                    @Override
                    public Map<String, String> getHeaders()  {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("store", SecretReadFile.store(context));
                        return headers;
                    }
                };
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        JibresApplication.getInstance().addToRequestQueue(request);
    }

    public static void intro(Context context, ApiListener.connected listener){
        StringRequest request =
                new StringRequest(Request.Method.GET, UrlManager.intro(context),
                        response -> {
                            Log.d(TAG, "intro: "+response);
                            try {
                                JSONObject mainObject = new JSONObject(response);
                                if (mainObject.getBoolean("ok")){
                                    JSONObject result = mainObject.getJSONObject("result");
                                    JsonManager.context(context)
                                            .setJsonIntro(String.valueOf(result));
                                    listener.onReceived(true);
                                }else {
                                    listener.onReceived(false);
                                }
                            } catch (JSONException e) {
                                listener.onReceived(false);
                                e.printStackTrace();
                                Log.e(TAG, "intro > result: ",e );
                            }
                        }, e -> {
                    listener.onReceived(false);
                    e.printStackTrace();
                    Log.e(TAG, "intro > request: ",e );
                })
                {
                    @Override
                    public Map<String, String> getHeaders()  {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("store", SecretReadFile.store(context));
                        return headers;
                    }
                };
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        JibresApplication.getInstance().addToRequestQueue(request);
    }
}
