package com.jibres.android.activity.security.sessions;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jibres.android.JibresApplication;
import com.jibres.android.keys;
import com.jibres.android.managers.AppManager;
import com.jibres.android.managers.UrlManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SessionsApi {
    private static String type = "terminateall";

    public static void list(Context context,sessionsListListener listener){
        StringRequest request =
                new StringRequest(Request.Method.GET, UrlManager.get.session(context),
                        response -> {
                            try {
                                JSONObject mainObject = new JSONObject(response);
                                if (mainObject.getBoolean("ok")){
                                    try {
                                        JSONArray result = mainObject.getJSONArray("result");
                                        listener.onReceived(String.valueOf(result));
                                    }catch (Exception e){
                                        listener.onReceived(null);
                                    }
                                }else {
                                    listener.onFiled(true);

                                }
                                if (!mainObject.isNull("msg")){
                                    JSONArray msg = mainObject.getJSONArray("msg");
                                    for (int i = 0 ; i<= msg.length();i++){
                                        JSONObject msg_object = msg.getJSONObject(i);
                                        if (!msg_object.isNull("text")){
                                            listener.onMassage(msg_object.getString("text"));
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                listener.onFiled(true);
                            }
                        }, e -> listener.onFiled(false))
                        // Send Headers
                {
                    @Override
                    public Map<String, String> getHeaders()  {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("appkey", keys.appkey);
                        headers.put("apikey", AppManager.getApikey(context));
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

    public interface sessionsListListener{
        void onReceived(String value);
        void onMassage(String value);
        void onFiled(boolean hasNet);
    }


    public static void remove(Context context, sessionsRemoveListener listener) {
        remove(context,null,false , listener);
    }
    public static void remove(Context context, String id, boolean removeAll ,
                              sessionsRemoveListener listener){
        if (removeAll){
            type = "terminate";
        }
        StringRequest request =
                new StringRequest(Request.Method.POST, UrlManager.get.session(context),
                        response -> {
                            Log.d("amingolisss", "remove: "+response);
                            try {
                                JSONObject mainObject = new JSONObject(response);
                                if (mainObject.getBoolean("ok")){
                                    try {
                                        if (mainObject.getBoolean("result")){
                                            listener.onReceived(true);
                                        }
                                    }catch (Exception e){
                                        listener.onReceived(false);
                                    }
                                }else {
                                    listener.onReceived(false);

                                }
                                if (!mainObject.isNull("msg")){
                                    JSONArray msg = mainObject.getJSONArray("msg");
                                    for (int i = 0 ; i<= msg.length();i++){
                                        JSONObject msg_object = msg.getJSONObject(i);
                                        if (!msg_object.isNull("text")){
                                            listener.onMassage(msg_object.getString("text"));
                                        }
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                listener.onFiled(true);
                            }
                        }, e -> listener.onFiled(false))
                {
                    @Override
                    public Map<String, String> getHeaders()  {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("appkey", keys.appkey );
                        headers.put("apikey", AppManager.getApikey(context) );
                        return headers;
                    }
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    public byte[] getBody() {
                        final Map<String,String> body = new HashMap<>();
                        body.put("type", type);
                        if (id !=null){
                            body.put("id", id);
                        }
                        return new Gson().toJson(body).getBytes(StandardCharsets.UTF_8);
                    }
                };
        request.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        JibresApplication.getInstance().addToRequestQueue(request);
    }

    public interface sessionsRemoveListener{
        void onReceived(boolean removed);
        void onMassage(String value);
        void onFiled(boolean hasNet);
    }
}
