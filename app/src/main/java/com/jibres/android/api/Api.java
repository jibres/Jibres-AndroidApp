package com.jibres.android.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jibres.android.JibresApplication;
import com.jibres.android.managers.AppManager;
import com.jibres.android.managers.JsonManager;
import com.jibres.android.managers.UrlManager;
import com.jibres.android.managers.UrlManager1;
import com.jibres.android.keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.jibres.android.managers.AppManager.versionName;

public class Api {
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
                        }, e -> listener.onReceived(false));
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
                        }, e -> listener.onReceived(false));
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        JibresApplication.getInstance().addToRequestQueue(request);
    }

    public static void splash(Context context, ApiListener.connected listener){
        StringRequest request =
                new StringRequest(Request.Method.GET, UrlManager.android(context),
                        response -> {
                            try {
                                JSONObject mainObject = new JSONObject(response);
                                if (mainObject.getBoolean("ok")){
                                    JSONObject result = mainObject.getJSONObject("result");
                                    JsonManager.context(context).setJsonIntros(String.valueOf(result));
                                    listener.onReceived(true);
                                }else {
                                    listener.onReceived(false);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                listener.onReceived(false);
                            }
                        }, e -> listener.onReceived(false));
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        JibresApplication.getInstance().addToRequestQueue(request);
    }































































    public static void getAppDetail(Context context, ApiListener.json listener){
        StringRequest request =
                new StringRequest(Request.Method.GET, UrlManager1.get.app_detail(context),
                        response -> {
                            try {
                                JSONObject mainObject = new JSONObject(response);
                                if (mainObject.getBoolean("ok")){
                                    JSONObject result = mainObject.getJSONObject("result");
                                    listener.onReceived(true,String.valueOf(result));
                                }else {
                                    JSONArray msg = mainObject.getJSONArray("msg");
                                    for (int i = 0 ; i<= msg.length();i++){
                                        JSONObject msg_object = msg.getJSONObject(i);
                                        if (!msg_object.isNull("text")){
                                            listener.onReceived(false,
                                                    msg_object.getString("text") );
                                        }else {
                                            listener.onReceived(false,null);
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                listener.onReceived(false,null);
                            }
                        }, e -> listener.onReceived(false,null))
                        // Send Headers
                {
                    @Override
                    public Map<String, String> getHeaders()  {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("appkey", keys.appkey);
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

    public static void getToken(Context context,final ApiListener.token listener) {
        StringRequest request =
                new StringRequest(Request.Method.POST, UrlManager1.get.token(context),
                        response -> {
                            JSONObject mainObject,result;
                            JSONArray msg;
                            boolean ok_getToken;
                            try {
                                mainObject = new JSONObject(response);
                                ok_getToken = mainObject.getBoolean("ok");
                                if (ok_getToken){
                                    result = mainObject.getJSONObject("result");
                                    listener.onReceived(result.getString("token"));
                                }else {
                                    msg = mainObject.getJSONArray("msg");
                                    for (int i = 0 ; i<= msg.length();i++){
                                        JSONObject msg_object = msg.getJSONObject(i);
                                        if (!msg_object.isNull("text")){
                                            listener.onFailed(msg_object.getString("text"));
                                        }else {
                                            listener.onFailed(null);
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                listener.onFailed(null);
                            }
                        }, e -> listener.onFailed(null))
                        // Send Headers
                {
                    @Override
                    public Map<String, String> getHeaders()  {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("appkey", keys.appkey);
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

    public static void userAdd(final Context context ,
                               final String token ,
                               final ApiListener.userAdd listener) {
        StringRequest request =
                new StringRequest(Request.Method.POST, UrlManager1.get.add_user(context),
                        response -> {
                            JSONObject mainObject,result ;
                            JSONArray msg;
                            boolean ok_getToken;
                            try {
                                mainObject = new JSONObject(response);
                                ok_getToken = mainObject.getBoolean("ok");
                                if (ok_getToken){

                                    result = mainObject.getJSONObject("result");
                                    AppManager.get(context).saveUserInfo(
                                            result.getString("apikey"),
                                            result.getString("usercode"),
                                            result.getString("zoneid"),
                                            AppManager.getMobile(context));

                                    listener.onReceived();
                                    listener.onMassage("userIsAdded ="
                                            + AppManager.getApikey(context)
                                            + " | " + AppManager.getUserCode(context)
                                            + " | " + AppManager.getZonId(context)
                                            + " | " + AppManager.getMobile(context));
                                } else {
                                    listener.onFailed();
                                }
                                try {
                                    msg = mainObject.getJSONArray("msg");
                                    for (int i = 0 ; i<= msg.length();i++){
                                        JSONObject object = msg.getJSONObject(i);
                                        listener.onMassage(object.getString("text"));
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                listener.onFailed();
                            }
                        }, e -> listener.onFailed())
                {
                    @Override
                    public Map<String, String> getHeaders()  {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("appkey", keys.appkey );
                        headers.put("token", token );
                        return headers;
                    }
                    @Override
                    public String getBodyContentType() {
                        return "application/json_splash; charset=utf-8";
                    }

                    @SuppressLint("HardwareIds")
                    @Override
                    public byte[] getBody() {
                        final Map<String,String> device = new HashMap<>();
                        device.put("model", Build.MODEL );
                        device.put("serial", Build.SERIAL );
                        device.put("manufacturer", Build.MANUFACTURER );
                        device.put("version", versionName );
                        device.put("hardware", Build.HARDWARE );
                        device.put("type", Build.TYPE );
                        device.put("board", Build.BOARD );
                        device.put("id", Build.ID );
                        device.put("product", Build.PRODUCT );
                        device.put("device", Build.DEVICE );
                        device.put("brand", Build.BRAND );
                        return new Gson().toJson(device).getBytes(StandardCharsets.UTF_8);
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
