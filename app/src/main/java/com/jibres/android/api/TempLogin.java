package com.jibres.android.api;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jibres.android.Static.lookServer;
import com.jibres.android.Static.url;
import com.jibres.android.Static.value;
import com.jibres.android.utility.Network;
import com.jibres.android.utility.SaveManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TempLogin {

    public static void Singing(final SingUpTampListener singUpTampListener, final Context context, final String token){
        final Map<String,String> device = new HashMap<>();
        device.put("model", Build.MODEL );
        device.put("serial", Build.SERIAL );
        device.put("manufacturer", Build.MANUFACTURER );
        device.put("version", value.versionName );
        device.put("hardware", Build.HARDWARE );
        device.put("type", Build.TYPE );
        device.put("board", Build.BOARD );
        device.put("id", Build.ID );
        device.put("product", Build.PRODUCT );
        device.put("device", Build.DEVICE );
        device.put("brand", Build.BRAND );
        final String deviceInfo = new Gson().toJson(device);
        /*Request*/
        StringRequest post_user_add = new StringRequest(Request.Method.POST, url.user_add, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                JSONObject mainObject,result ;
                JSONArray msg;
                boolean ok_getToken;
                try {
                    mainObject = new JSONObject(response);
                    ok_getToken = mainObject.getBoolean("ok");
                    if (ok_getToken){
                        result = mainObject.getJSONObject("result");
                        String usercode = result.getString("usercode");
                        String zoneid = result.getString("zoneid");
                        String apikey = result.getString("apikey");
                        /*Save Value*/
                        SaveManager.get(context).change_infoLOGIN(apikey,usercode,zoneid);
                        singUpTampListener.UserAddToServer(true);

                    }else {
                        msg = mainObject.getJSONArray("msg");
                        singUpTampListener.FiledUserAdd(false);
                        for (int i = 0 ; i<= msg.length();i++){
                            JSONObject msg_object = msg.getJSONObject(i);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    singUpTampListener.FiledUserAdd(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                singUpTampListener.FiledUserAdd(false);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders()  {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("appkey", lookServer.appkey );
                headers.put("token", token );
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return deviceInfo == null ? null : deviceInfo.getBytes(StandardCharsets.UTF_8);
            }
        };
        post_user_add.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Network.getInstance().addToRequestQueue(post_user_add);
    }

    public interface SingUpTampListener {
        void UserAddToServer(Boolean UserAddToServer);

        void FiledUserAdd(Boolean FiledUserAdd);
    }


}
