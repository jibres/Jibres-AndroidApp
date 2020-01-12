package com.jibres.android.activity.tiket.api;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.jibres.android.JibresApplication;
import com.jibres.android.keys;
import com.jibres.android.managers.AppManager;
import com.jibres.android.managers.UrlManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TiketApi {
    public static void list(Context context,String page,TiketListener.listTiket listener){
        if (page==null){
            page = "1";
        }
        StringRequest request =
                new StringRequest(Request.Method.GET, UrlManager.get.tiket_list(context,page),
                        response -> {
                            try {
                                JSONObject mainObject = new JSONObject(response);
                                if (mainObject.getBoolean("ok")){
                                    JSONObject pagination = mainObject.getJSONObject("pagination");
                                    int total_page = pagination.getInt("total_page");
                                    JSONArray result = mainObject.getJSONArray("result");
                                    listener.onReceived(String.valueOf(result),total_page);
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

    public static void viewTiket(Context context,String tiket,TiketListener.viewTiket listener){
        StringRequest request =
                new StringRequest(Request.Method.GET, UrlManager.get.tiket_view(context,tiket),
                        response -> {
                            try {
                                JSONObject mainObject = new JSONObject(response);
                                if (mainObject.getBoolean("ok")){
                                    JSONArray result = mainObject.getJSONArray("result");
                                    listener.onReceived(String.valueOf(result));
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
}
