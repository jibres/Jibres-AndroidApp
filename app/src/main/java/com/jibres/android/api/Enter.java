package com.jibres.android.api;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jibres.android.Static.lookServer;
import com.jibres.android.Static.url;
import com.jibres.android.api.Token;
import com.jibres.android.utility.Network;
import com.jibres.android.utility.getUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Enter {

    public static void phone(final Context context, String phoneNumber , final enterPhone_Listener listener){
        Map<String, String> posting = new HashMap<>();
        posting.put("mobile", phoneNumber);
        final String param = new Gson().toJson(posting);

        Token.GetToken(new Token.TokenListener() {
            @Override
            public void onReceived(final String token) {
                StringRequest getVerify = new StringRequest(Request.Method.POST, url.enter, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        JSONObject mainObject;
                        JSONArray msg;
                        boolean ok_getVerify;
                        try {
                            mainObject = new JSONObject(response);
                            ok_getVerify = mainObject.getBoolean("ok");
                            if (ok_getVerify){
                                listener.onReceived();
                            }else {
                                listener.onError();
                            }

                            msg = mainObject.getJSONArray("msg");
                            for (int i = 0 ; i<= msg.length();i++){
                                JSONObject msg_object = msg.getJSONObject(i);
                                if (!msg_object.isNull("text")){
                                    listener.onMassage(msg_object.getString("text"));
                                }
                            }

                        } catch (JSONException error) {
                            error.printStackTrace();
                            listener.onError();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        listener.onReceived();
                        listener.onError();
                    }
                })
                        // Send Headers
                {
                    @Override
                    public Map<String, String> getHeaders()  {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("appkey", lookServer.appkey);
                        headers.put("apikey", getUser.apiKey(context));
                        headers.put("token", token);
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        return param == null ? null : param.getBytes(StandardCharsets.UTF_8);
                    }

                };
                Network.getInstance().addToRequestQueue(getVerify);
            }

            @Override
            public void onFailed(String error) {
                listener.onError();
                if (error != null){
                    listener.onMassage(error);
                }
            }
        });
    }

    public interface enterPhone_Listener{
        void onReceived();
        void onMassage(String msg);
        void onError();
    }

    public static void verifyCode(final Context context , String phoneNumber , String verifyCode , final verifyCode_Listener listener){
        Map<String, String> posting = new HashMap<>();
        posting.put("mobile", phoneNumber);
        posting.put("verifycode", verifyCode);
        final String param = new Gson().toJson(posting);
        Token.GetToken(new Token.TokenListener() {
            @Override
            public void onReceived(final String token) {
                StringRequest getVerify = new StringRequest(Request.Method.POST, url.verify, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        JSONObject mainObject,result;
                        JSONArray msg;
                        boolean ok_getVerify;
                        try {
                            mainObject = new JSONObject(response);
                            ok_getVerify = mainObject.getBoolean("ok");
                            if (ok_getVerify){
                                result = mainObject.getJSONObject("result");
                                String apiKey_new = result.getString("apikey");
                                listener.onReceived(apiKey_new);
                            }else {
                                listener.onError();
                            }

                            msg = mainObject.getJSONArray("msg");
                            for (int i = 0 ; i<= msg.length();i++){
                                JSONObject msg_object = msg.getJSONObject(i);
                                if (!msg_object.isNull("text")){
                                    listener.onMassage(msg_object.getString("text"));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onError();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        listener.onError();
                    }
                })
                        // Send Headers
                {
                    @Override
                    public Map<String, String> getHeaders()  {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("appkey", lookServer.appkey);
                        headers.put("apikey", getUser.apiKey(context));
                        headers.put("token", token);
                        return headers;
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        return param == null ? null : param.getBytes(StandardCharsets.UTF_8);
                    }

                };
                Network.getInstance().addToRequestQueue(getVerify);
            }

            @Override
            public void onFailed(String error) {
                listener.onError();
                if (error != null){
                    listener.onMassage(error);
                }
            }
        });

    }
    public interface verifyCode_Listener{
        void onReceived(String apiKey_new);
        void onMassage(String msg);
        void onError();
    }

}
