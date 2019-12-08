package com.jibres.android.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.jibres.android.R;
import com.jibres.android.Static.lookServer;
import com.jibres.android.Static.tag;
import com.jibres.android.Static.url;
import com.jibres.android.api.Token;
import com.jibres.android.utility.Network;
import com.jibres.android.utility.SaveManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView tvTitleVerify, xtext_number_verify,tvResndVerify;
    EditText edtVerify_1, edtVerify_2, edtVerify_3, edtVerify_4, edtVerify_5, edtWriteNumber;
    Button btnNumber;
    View boxNumber,boxVerify;


    //    enter.xml
    ImageView next_img, error_img;
    TextView title,title_boxNumber,desc;
    EditText numberPhone;
    ProgressBar progress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findId_enter();
        findId_verify();
        EditTextSetMethode(edtVerify_1, edtVerify_2, edtVerify_3, edtVerify_4, edtVerify_5,numberPhone);

        boxNumber.setVisibility(View.VISIBLE);
        next_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next_img.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                step1_VerifyNumber();
            }
        });
    }

    private String getNumberPhone(){
        return numberPhone.getText().toString().replace("+","");
    }
    private String getApiKey(){
        return SaveManager.get(this).getstring_appINFO().get(SaveManager.apiKey);
    }
    private String getUserVerfycationCode(){
        return edtVerify_1.getText().toString()
                + edtVerify_2.getText().toString()
                + edtVerify_3.getText().toString()
                + edtVerify_4.getText().toString()
                + edtVerify_5.getText().toString();
    }

    /** Connection To Server*/
    /*Writ Number Phone*/
    private void step1_VerifyNumber(){
        Map<String, String> posting = new HashMap<>();
        posting.put("mobile", getNumberPhone());
        final String param = new Gson().toJson(posting);
        Token.GetToken(new Token.TokenListener() {
            @Override
            public void onTokenRecieved(final String token) {
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
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        boxNumber.setVisibility(View.GONE);
                                        progress.setVisibility(View.GONE);
                                        boxVerify.setVisibility(View.VISIBLE);
                                    }
                                },500);

                                xtext_number_verify.setText(getNumberPhone());
                                edtVerify_1.requestFocus();

                                new CountDownTimer(300000, 1000) {

                                    public void onTick(long millisUntilFinished) {
                                        int seconds = (int) (millisUntilFinished / 1000) % 60 ;
                                        int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                                        int hours   = (int) ((millisUntilFinished / (1000*60*60)) % 24);
                                        tvResndVerify.setText("Resend " +String.format("%d:%d",minutes,seconds));
                                        //here you can have your logic to set text to edittext
                                    }

                                    public void onFinish() {
                                        tvResndVerify.setText("Resend new code");
                                        tvResndVerify.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                step1_VerifyNumber();
                                            }
                                        });
                                    }

                                }.start();
                            }else {
                                msg = mainObject.getJSONArray("msg");
                                for (int i = 0 ; i<= msg.length();i++){
                                    JSONObject msg_object = msg.getJSONObject(i);
                                    Log.e(tag.error,"VerifyNumberFetcher HasNet: "+msg_object.getString("text"));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(tag.error,"VerifyNumberFetcher >JSONException "+e);
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.setVisibility(View.GONE);
                        error_img.setVisibility(View.VISIBLE);
                        Log.e(tag.error,"VerifyNumberFetcher > onErrorResponse  "+error);
                    }
                })
                        // Send Headers
                {
                    @Override
                    public Map<String, String> getHeaders()  {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("appkey", lookServer.appkey);
                        headers.put("token", token);
                        headers.put("apikey", getApiKey());
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
            public void onTokenFailed(String error) {
                progress.setVisibility(View.GONE);
                error_img.setVisibility(View.VISIBLE);
            }
        });
    }

    /*Writ Verify Code*/
    private void step2_VerifyCodeSMS(){
        Map<String, String> posting = new HashMap<>();
        posting.put("mobile", getNumberPhone());
        posting.put("verifycode", getUserVerfycationCode());
        final String param = new Gson().toJson(posting);
        Token.GetToken(new Token.TokenListener() {
            @Override
            public void onTokenRecieved(final String token) {
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
                                String apikeyNew = result.getString("apikey");
                                String usercode  =  SaveManager.get(getApplicationContext()).getstring_appINFO().get(SaveManager.userCode);
                                String zoneid    =  SaveManager.get(getApplicationContext()).getstring_appINFO().get(SaveManager.zoneID);
                                SaveManager.get(getApplicationContext()).change_mobile(getNumberPhone());

                                SaveManager.get(getApplicationContext()).change_infoLOGIN(apikeyNew,usercode,zoneid);
                                nexActivity();

                            }else {
                                msg = mainObject.getJSONArray("msg");
                                for (int i = 0 ; i<= msg.length();i++){
                                    JSONObject msg_object = msg.getJSONObject(i);
                                    Log.e(tag.error,"VerifyCodeFethcer: "+ msg_object.getString("text"));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(tag.error,"VerifyCodeFethcer >JSONException "+e);
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(tag.error,"VerifyCodeFethcer > onErrorResponse  "+error);
                    }
                })
                        // Send Headers
                {
                    @Override
                    public Map<String, String> getHeaders()  {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("appkey", lookServer.appkey);
                        headers.put("token", token);
                        headers.put("apikey", getApiKey());
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
            public void onTokenFailed(String error) {

            }
        });
    }

    private void nexActivity() {
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }

    /** Edit Text Method*/
    private void EditTextSetMethode(final EditText Verify_1 , final EditText Verify_2 , final EditText Verify_3, final EditText Verify_4, final EditText Verify_5, final EditText Number){
        Number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (error_img.getVisibility() == View.VISIBLE){
                    error_img.setVisibility(View.GONE);
                }

                if (Number.length() >=7){
                    next_img.setVisibility(View.VISIBLE);
                }else {
                    next_img.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Verify_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(tag.error, "beforeTextChanged: "+i+"|"+i1+"|"+i2);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(tag.error, "onTextChanged: "+i+"|"+i1+"|"+i2);
                if (charSequence.length() == 1){
                    Verify_2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(tag.error, "afterTextChanged: "+editable);

            }
        });

        Verify_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1){
                    Verify_3.requestFocus();
                }else {
                    Verify_1.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        Verify_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1){
                    Verify_4.requestFocus();
                }else {
                    Verify_2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Verify_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1){
                    Verify_5.requestFocus();
                }else {
                    Verify_3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Verify_5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final int edtLength = getUserVerfycationCode().length();
                if (charSequence.length() == 1){
                    if (edtLength == 5){
                        step2_VerifyCodeSMS();
                    }else {
                        Verify_1.getText().clear();
                        Verify_2.getText().clear();
                        Verify_3.getText().clear();
                        Verify_4.getText().clear();
                        Verify_5.getText().clear();
                        Verify_1.requestFocus();
                        Toast.makeText(getApplicationContext(), "Pleas write Code "+edtLength, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Verify_4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    /** Static Method*/
    /*Find Id*/
    private void findId_enter(){
        progress = findViewById(R.id.xprogress_edt_enter);
        error_img = findViewById(R.id.ximg_edt_error_enter);
        next_img = findViewById(R.id.ximg_edt_next_enter);
        title = findViewById(R.id.xtitle_enter);
        title_boxNumber = findViewById(R.id.xtitle_edt_number);
        desc = findViewById(R.id.xdesc_enter);
        numberPhone = findViewById(R.id.xedt_number_enter);
    }
    private void findId_verify(){
        /*Verify*/
        boxNumber = findViewById(R.id.boxNumberPhone_login);
        boxVerify=findViewById(R.id.boxVerify_login);
        tvTitleVerify=findViewById(R.id.textViewTitleVerify_login);
        xtext_number_verify =findViewById(R.id.xtext_number_verify);
        edtVerify_1 =findViewById(R.id.edt_verify1_login);
        edtVerify_2 =findViewById(R.id.edt_verify2_login);
        edtVerify_3 =findViewById(R.id.edt_verify3_login);
        edtVerify_4 =findViewById(R.id.edt_verify4_login);
        edtVerify_5 =findViewById(R.id.edt_verify5_login);
        tvResndVerify=findViewById(R.id.tvResndVerify_login);
    }
    /*Show Snack Bar In Error Json*/
    private void writeNumberAfter120sec(){
        btnNumber.setVisibility(View.GONE);
        final Snackbar snackbar = Snackbar.make(boxNumber, "", Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setDuration(12000);
        new CountDownTimer(12000, 1000) {

            public void onTick(long millisUntilFinished) {
                snackbar.setText("Try Again after: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                btnNumber.setVisibility(View.VISIBLE);
                edtWriteNumber.getText().clear();
                edtWriteNumber.requestFocus();
            }

        }.start();
        snackbar.show();
    }
    private void codeVerifyNotValid(){
        btnNumber.setVisibility(View.GONE);
        final Snackbar snackbar = Snackbar.make(boxNumber, "Code Not Valid", Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setDuration(3000);
        edtVerify_1.getText().clear();
        edtVerify_2.getText().clear();
        edtVerify_3.getText().clear();
        edtVerify_4.getText().clear();
        edtVerify_5.getText().clear();
        edtVerify_1.requestFocus();
        snackbar.show();
    }
}
