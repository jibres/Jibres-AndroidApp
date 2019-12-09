package com.jibres.android.api.account;

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
import com.jibres.android.R;
import com.jibres.android.Static.lookServer;
import com.jibres.android.Static.tag;
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
                                listener.onReceived();
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


    /**
     *  Set Edit Text
     */
    public static void edtText_number(final EditText number , final edtText_number_Listener listener){
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listener.onChange();
                if (number.length() >=7){
                    listener.onReceived();
                }else {
                    listener.onError();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    public interface edtText_number_Listener {
        void onReceived();
        void onChange();
        void onError();
    }
    /**
     * Edit Text Method
     */
    public static void edtText_verifyCode(final EditText edt_1 ,
                                    final EditText edt_2 ,
                                    final EditText edt_3,
                                    final EditText edt_4,
                                    final EditText edt_5,
                                    final edtText_verifyCode_Listener listener)
    {
        edt_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(tag.error, "beforeTextChanged: "+i+"|"+i1+"|"+i2);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(tag.error, "onTextChanged: "+i+"|"+i1+"|"+i2);
                if (charSequence.length() == 1){
                    edt_2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(tag.error, "afterTextChanged: "+editable);

            }
        });

        edt_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1){
                    edt_3.requestFocus();
                }else {
                    edt_1.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        edt_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1){
                    edt_4.requestFocus();
                }else {
                    edt_2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edt_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1){
                    edt_5.requestFocus();
                }else {
                    edt_3.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        edt_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String all_verifyCode =
                        edt_1.getText().toString()
                        +edt_2.getText().toString()
                        +edt_3.getText().toString()
                        +edt_4.getText().toString()
                        +edt_5.getText().toString();
                if (charSequence.length() == 1){
                    if (all_verifyCode.length() == 5){
                        listener.onReceived();
                    }else {
                        edt_1.getText().clear();
                        edt_2.getText().clear();
                        edt_3.getText().clear();
                        edt_4.getText().clear();
                        edt_5.getText().clear();
                        edt_1.requestFocus();
                        listener.onError();
                    }
                }else {
                    edt_4.requestFocus();
                    listener.onError();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edt_1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                    if(keyCode == KeyEvent.KEYCODE_DEL) {
                        edt_1.getText().clear();
                        return true;
                    }else if (keyCode == KeyEvent.KEYCODE_ENTER){
                        edt_2.requestFocus();
                    }else {
                        if (edt_1.length() == 1){
                            String n = null;
                            switch (keyCode){
                                case KeyEvent.KEYCODE_0:
                                    n="0";
                                    break;
                                case KeyEvent.KEYCODE_1:
                                    n="1";
                                    break;
                                case KeyEvent.KEYCODE_2:
                                    n="2";
                                    break;
                                case KeyEvent.KEYCODE_3:
                                    n="3";
                                    break;
                                case KeyEvent.KEYCODE_4:
                                    n="4";
                                    break;
                                case KeyEvent.KEYCODE_5:
                                    n="5";
                                    break;
                                case KeyEvent.KEYCODE_6:
                                    n="6";
                                    break;
                                case KeyEvent.KEYCODE_7:
                                    n="7";
                                    break;
                                case KeyEvent.KEYCODE_8:
                                    n="8";
                                    break;
                                case KeyEvent.KEYCODE_9:
                                    n="9";
                                    break;
                            }
                            if (n!=null){
                                edt_2.setText(n);
                            }
                            edt_2.requestFocus();
                        }
                    }
                }
                return false;
            }
        });

        edt_2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if(keyCode == KeyEvent.KEYCODE_DEL) {
                        edt_2.getText().clear();
                        edt_1.requestFocus();
                        return true;
                        //this is for backspace
                    }else {
                        if (edt_2.length() == 1){
                            String n = null;
                            switch (keyCode){
                                case KeyEvent.KEYCODE_0:
                                    n="0";
                                    break;
                                case KeyEvent.KEYCODE_1:
                                    n="1";
                                    break;
                                case KeyEvent.KEYCODE_2:
                                    n="2";
                                    break;
                                case KeyEvent.KEYCODE_3:
                                    n="3";
                                    break;
                                case KeyEvent.KEYCODE_4:
                                    n="4";
                                    break;
                                case KeyEvent.KEYCODE_5:
                                    n="5";
                                    break;
                                case KeyEvent.KEYCODE_6:
                                    n="6";
                                    break;
                                case KeyEvent.KEYCODE_7:
                                    n="7";
                                    break;
                                case KeyEvent.KEYCODE_8:
                                    n="8";
                                    break;
                                case KeyEvent.KEYCODE_9:
                                    n="9";
                                    break;
                            }
                            if (n!=null){
                                edt_3.setText(n);
                            }
                            edt_3.requestFocus();
                            return true;
                        }
                    }


                }
                return false;
            }
        });

        edt_3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if(keyCode == KeyEvent.KEYCODE_DEL) {
                        edt_3.getText().clear();
                        edt_2.requestFocus();
                        return true;
                        //this is for backspace
                    }else {
                        if (edt_3.length() == 1){
                            String n = null;
                            switch (keyCode){
                                case KeyEvent.KEYCODE_0:
                                    n="0";
                                    break;
                                case KeyEvent.KEYCODE_1:
                                    n="1";
                                    break;
                                case KeyEvent.KEYCODE_2:
                                    n="2";
                                    break;
                                case KeyEvent.KEYCODE_3:
                                    n="3";
                                    break;
                                case KeyEvent.KEYCODE_4:
                                    n="4";
                                    break;
                                case KeyEvent.KEYCODE_5:
                                    n="5";
                                    break;
                                case KeyEvent.KEYCODE_6:
                                    n="6";
                                    break;
                                case KeyEvent.KEYCODE_7:
                                    n="7";
                                    break;
                                case KeyEvent.KEYCODE_8:
                                    n="8";
                                    break;
                                case KeyEvent.KEYCODE_9:
                                    n="9";
                                    break;
                            }
                            if (n!=null){
                                edt_4.setText(n);
                            }
                            edt_4.requestFocus();
                            return true;
                        }
                    }
                }

                return false;
            }
        });

        edt_4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if(keyCode == KeyEvent.KEYCODE_DEL) {
                        edt_4.getText().clear();
                        edt_3.requestFocus();
                        return true;
                        //this is for backspace
                    }else if (keyCode == KeyEvent.KEYCODE_ENTER){
                        edt_5.requestFocus();
                        return true;
                    }else {
                        if (edt_4.length() == 1){
                            String n = null;
                            switch (keyCode){
                                case KeyEvent.KEYCODE_0:
                                    n="0";
                                    break;
                                case KeyEvent.KEYCODE_1:
                                    n="1";
                                    break;
                                case KeyEvent.KEYCODE_2:
                                    n="2";
                                    break;
                                case KeyEvent.KEYCODE_3:
                                    n="3";
                                    break;
                                case KeyEvent.KEYCODE_4:
                                    n="4";
                                    break;
                                case KeyEvent.KEYCODE_5:
                                    n="5";
                                    break;
                                case KeyEvent.KEYCODE_6:
                                    n="6";
                                    break;
                                case KeyEvent.KEYCODE_7:
                                    n="7";
                                    break;
                                case KeyEvent.KEYCODE_8:
                                    n="8";
                                    break;
                                case KeyEvent.KEYCODE_9:
                                    n="9";
                                    break;
                            }
                            if (n!=null){
                                edt_5.setText(n);
                            }
                            edt_5.requestFocus();
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        edt_5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if(keyCode == KeyEvent.KEYCODE_DEL) {
                        edt_5.getText().clear();
                        edt_4.requestFocus();
                        return true;
                        //this is for backspace
                    }else {
                        if (edt_5.length() == 1){
                            String all_verifyCode =
                                    edt_1.getText().toString()
                                    +edt_2.getText().toString()
                                    +edt_3.getText().toString()
                                    +edt_4.getText().toString()
                                    +edt_5.getText().toString();
                            if (all_verifyCode.length() == 5){
                                listener.onReceived();
                                return true;
                            }else {
                                edt_1.getText().clear();
                                edt_2.getText().clear();
                                edt_3.getText().clear();
                                edt_4.getText().clear();
                                edt_5.getText().clear();
                                edt_1.requestFocus();
                                listener.onError();
                            }
                        }
                    }
                }
                return false;
            }
        });

    }
    public interface edtText_verifyCode_Listener {
        void onReceived();
        void onError();
    }



}
