package com.jibres.android.api;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jibres.android.Static.lookServer;
import com.jibres.android.Static.url;
import com.jibres.android.utility.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Token {

    public static void GetToken(final TokenListener tokenListener) {

        StringRequest getToken = new StringRequest(Request.Method.POST, url.token, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                JSONObject mainObject,result;
                JSONArray msg;
                boolean ok_getToken;
                try {
                    mainObject = new JSONObject(response);
                    ok_getToken = mainObject.getBoolean("ok");
                    if (ok_getToken){
                        result = mainObject.getJSONObject("result");
                        tokenListener.onTokenRecieved(result.getString("token"));
                    }else {
                        msg = mainObject.getJSONArray("msg");
                        for (int i = 0 ; i<= msg.length();i++){
                            JSONObject msg_object = msg.getJSONObject(i);
                            tokenListener.onTokenFailed(msg_object+"");

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    tokenListener.onTokenFailed("JSONException: "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                tokenListener.onTokenFailed("VolleyError: "+e);
            }
        })
                // Send Headers
        {
            @Override
            public Map<String, String> getHeaders()  {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("appkey", lookServer.appkey);
                return headers;
            }

        };
        getToken.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Network.getInstance().addToRequestQueue(getToken);

    }

    public interface TokenListener {
        void onTokenRecieved(String token);

        void onTokenFailed(String error);
    }
}

