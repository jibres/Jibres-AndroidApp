package com.jibres.android.activity.intro;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.jibres.android.JibresApplication;
import com.jibres.android.keys;
import com.jibres.android.managers.JsonManager;
import com.jibres.android.managers.UrlManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IntroApi {
  public IntroApi(Context context) {
    StringRequest request =
        new StringRequest(Request.Method.GET, UrlManager.get.app_detail(context), response -> {
          try {
            JSONObject mainObject = new JSONObject(response);
            if (mainObject.getBoolean("ok")){
              JSONObject result = mainObject.getJSONObject("result");

              JSONArray intro = result.getJSONArray("intro");
              JsonManager.get(context).setJsonIntro(String.valueOf(intro));
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }, Throwable::printStackTrace)
        {
          @Override
          public Map<String, String> getHeaders()  {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("appkey", keys.appkey);
            return headers;
          }
        };
    request.setRetryPolicy(new DefaultRetryPolicy(
        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    JibresApplication.getInstance().addToRequestQueue(request);
  }
}
