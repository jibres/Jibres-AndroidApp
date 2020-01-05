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
import com.jibres.android.appinfo.UserManager;
import com.jibres.android.curl.UrlManager;
import com.jibres.android.keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Api {
    public static void getToken(final ApiListener.token listener) {
        StringRequest request = new StringRequest(Request.Method.POST, UrlManager.get.token(),
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
                new StringRequest(Request.Method.POST, UrlManager.get.add_user(context),
                        response -> {
                            JSONObject mainObject,result ;
                            JSONArray msg;
                            boolean ok_getToken;
                            try {
                                mainObject = new JSONObject(response);
                                ok_getToken = mainObject.getBoolean("ok");
                                if (ok_getToken){

                                    result = mainObject.getJSONObject("result");
                                    UserManager.get(context).saveUserInfo(
                                            result.getString("apikey"),
                                            result.getString("usercode"),
                                            result.getString("zoneid"),
                                            UserManager.getMobile(context));

                                    listener.onReceived();
                                    Log.d("userAdd", "userAdd: "
                                            + UserManager.getApikey(context)
                                            + " | " + UserManager.getUserCode(context)
                                            + " | " + UserManager.getZonId(context)
                                            + " | " + UserManager.getMobile(context));
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
                        return "application/json; charset=utf-8";
                    }

                    @SuppressLint("HardwareIds")
                    @Override
                    public byte[] getBody() {
                        final Map<String,String> device = new HashMap<>();
                        device.put("model", Build.MODEL );
                        device.put("serial", Build.SERIAL );
                        device.put("manufacturer", Build.MANUFACTURER );
                        device.put("version", UserManager.versionName );
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
