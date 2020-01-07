package com.jibres.android.activity.language;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.jibres.android.JibresApplication;
import com.jibres.android.managers.UrlManager;

import org.json.JSONObject;

public class LanguageApi {
    public LanguageApi(Context context) {
        StringRequest request =
                new StringRequest(Request.Method.GET, UrlManager.get.language(context), response -> {
                    try {
                        JSONObject mainObject = new JSONObject(response);
                        if (mainObject.getBoolean("ok")){
                            JSONObject result = mainObject.getJSONObject("result");
                            if (!result.isNull("version")){
                                JSONObject version = result.getJSONObject("version");
                                if (!version.isNull("last")){
                                    int last = version.getInt("last");
                                    Log.d("amingoli", "UpdateVersionApi: "+last);
                                    LanguageManager.get(context).setJsonLanguage(String.valueOf(last));
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        JibresApplication.getInstance().addToRequestQueue(request);
    }
}
