package com.jibres.android.activity.enter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jibres.android.JibresApplication;
import com.jibres.android.api.Api;
import com.jibres.android.api.ApiListener;
import com.jibres.android.keys;
import com.jibres.android.managers.AppManager;
import com.jibres.android.managers.UrlManager1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EnterApi {

    public static void phone(final Context context, String phoneNumber , final enterPhone_Listener listener){
        Api.getToken(context,new ApiListener.token() {
            @Override
            public void onReceived(final String token) {
                StringRequest getVerify =
                        new StringRequest(Request.Method.POST,
                                UrlManager1.get.enter_mobile(context), response -> {
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
                        }, error -> {
                            error.printStackTrace();
                            listener.onReceived();
                            listener.onError();
                        })
                                // Send Headers
                        {
                            @Override
                            public Map<String, String> getHeaders()  {
                                HashMap<String, String> headers = new HashMap<>();
                                headers.put("appkey", keys.appkey);
                                headers.put("apikey", AppManager.getApikey(context));
                                headers.put("token", token);
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";
                            }
                            @Override
                            public byte[] getBody() {
                                Map<String, String> posting = new HashMap<>();
                                posting.put("mobile", phoneNumber);
                                return new Gson().toJson(posting).getBytes(StandardCharsets.UTF_8);
                            }

                        };
                JibresApplication.getInstance().addToRequestQueue(getVerify);
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
        Api.getToken(context,new ApiListener.token() {
            @Override
            public void onReceived(final String token) {
                StringRequest getVerify =
                        new StringRequest(Request.Method.POST,
                                UrlManager1.get.verify_code(context), response -> {
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
                        }, error -> {
                            error.printStackTrace();
                            listener.onError();
                        })
                                // Send Headers
                        {
                            @Override
                            public Map<String, String> getHeaders()  {
                                HashMap<String, String> headers = new HashMap<>();
                                headers.put("appkey", keys.appkey);
                                headers.put("apikey", AppManager.getApikey(context));
                                headers.put("token", token);
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8";
                            }

                            @Override
                            public byte[] getBody() {
                                Map<String, String> posting = new HashMap<>();
                                posting.put("mobile", phoneNumber);
                                posting.put("verifycode", verifyCode);
                                return new Gson().toJson(posting).getBytes(StandardCharsets.UTF_8);
                            }

                        };
                JibresApplication.getInstance().addToRequestQueue(getVerify);
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
