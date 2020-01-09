package com.jibres.android.activity.enter;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jibres.android.R;
import com.jibres.android.activity.MainActivity;
import com.jibres.android.managers.AppManager;
import com.jibres.android.utility.ColorUtil;

@SuppressLint("Registered")
public class EnterActivity extends AppCompatActivity {

    ImageView e_okNumber, e_errorNumber;
    ProgressBar e_progressNumber;
    TextView e_title,e_titleNumber,e_desc ,
            v_titel,v_titleNumber,v_number,v_editNumber,v_resnd;
    CountDownTimer downTimer;
    android.widget.EditText e_number,
            edtV1, edtV2, edtV3, edtV4, edtV5;
    View enterXML ,
            verifyXML;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        idFinder();

        ColorUtil.setGradient(enterXML, "#3395ff", "#d1395c");
        ColorUtil.setGradient(verifyXML, "#3395ff", "#d1395c");

        weight_EditText.enter_virifyCode(edtV1, edtV2, edtV3, edtV4, edtV5,
                new weight_EditText.enter_verifyCode_Listener() {
                    @Override
                    public void onReceived() {
                        verifyCode();
                    }

                    @Override
                    public void onError() {

                    }
                });

        weight_EditText.enter_number(e_number, new weight_EditText.enter_number_Listener() {
            @Override
            public void onReceived() {
                verifyNumber();
            }

            @Override
            public void onChange() {
                if (e_errorNumber.getVisibility() == View.VISIBLE){
                    e_errorNumber.setVisibility(View.GONE);
                }
                if (e_number.length() >=7){
                    e_okNumber.setVisibility(View.VISIBLE);
                }else {
                    e_okNumber.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
                e_okNumber.setVisibility(View.GONE);
                e_errorNumber.setVisibility(View.VISIBLE);
            }
        });
    }


    /** Connection To Server*/
    public void verifyNumber(){
        e_okNumber.setVisibility(View.GONE);
        e_progressNumber.setVisibility(View.VISIBLE);
        EnterApi.phone(this, getNumberPhone(), new EnterApi.enterPhone_Listener() {
            @Override
            public void onReceived() {
                enterXML.setVisibility(View.GONE);
                verifyXML.setVisibility(View.VISIBLE);
                e_progressNumber.setVisibility(View.GONE);

                v_number.setText(getNumberPhone());
                edtV1.requestFocus();

                if (downTimer !=null){
                    downTimer.cancel();
                    downTimer = null;
                }
                downTimer = new CountDownTimer(300000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        int seconds = (int) (millisUntilFinished / 1000) % 60 ;
                        int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                        int hours   = (int) ((millisUntilFinished / (1000*60*60)) % 24);
                        v_resnd.setText("Resend " +String.format("%d:%d",minutes,seconds));
                        if (v_resnd.isEnabled()){
                            v_resnd.setEnabled(false);
                        }
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        v_resnd.setText("Resend new code");
                        v_resnd.setText("Your Phone");
                        v_resnd.setEnabled(true);
                    }

                }.start();
            }

            @Override
            public void onMassage(String msg) {
                Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                e_progressNumber.setVisibility(View.GONE);
                e_errorNumber.setVisibility(View.VISIBLE);
            }
        });

    }

    private void verifyCode(){
        EnterApi.verifyCode(this, getNumberPhone(), getUserVerfycationCode(),
                new EnterApi.verifyCode_Listener() {
                    @Override
                    public void onReceived(String apiKey_new) {
                        AppManager.get(getApplicationContext())
                                .saveUserInfo(
                                        apiKey_new,
                                        AppManager.getUserCode(getApplicationContext()),
                                        AppManager.getZonId(getApplicationContext()),
                                        getNumberPhone());
                        nexActivity();
                    }

                    @Override
                    public void onMassage(String msg) {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError() {

                    }
                });
    }





    /** Static Method*/
    private void nexActivity() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private String getUserVerfycationCode(){
        return edtV1.getText().toString()
                + edtV2.getText().toString()
                + edtV3.getText().toString()
                + edtV4.getText().toString()
                + edtV5.getText().toString();
    }

    private String getNumberPhone(){
        return e_number.getText().toString().replace("+","");
    }

    private void idFinder(){
        /*Verify = v_ */
        verifyXML =findViewById(R.id.boxVerify_login);
        v_titel = verifyXML.findViewById(R.id.title);
        v_titleNumber = verifyXML.findViewById(R.id.titleNumber);
        v_number = verifyXML.findViewById(R.id.number);
        v_editNumber = verifyXML.findViewById(R.id.editNumber);
        v_resnd = verifyXML.findViewById(R.id.resend);
        edtV1 = findViewById(R.id.edt_verify1_login);
        edtV2 = findViewById(R.id.edt_verify2_login);
        edtV3 = findViewById(R.id.edt_verify3_login);
        edtV4 = findViewById(R.id.edt_verify4_login);
        edtV5 = findViewById(R.id.edt_verify5_login);

        /*Enter = e_ */
        enterXML = findViewById(R.id.boxNumberPhone_login);
        e_title = enterXML.findViewById(R.id.title);
        e_titleNumber = enterXML.findViewById(R.id.titleNumber);
        e_number = enterXML.findViewById(R.id.number);
        e_desc = enterXML.findViewById(R.id.desc);
        e_okNumber = enterXML.findViewById(R.id.ok);
        e_errorNumber = enterXML.findViewById(R.id.error);
        e_progressNumber = enterXML.findViewById(R.id.progress);
    }

    public void enter(View view) {
        switch (view.getTag().toString()){
            case "verifyNumber":
                e_errorNumber.setVisibility(View.GONE);
                e_progressNumber.setVisibility(View.GONE);
                enterXML.setVisibility(View.VISIBLE);
                e_okNumber.setVisibility(View.VISIBLE);
                verifyXML.setVisibility(View.GONE);
                break;
            case "verifyCode":
                verifyNumber();
                break;

            case "resendCode":
                if (view.isEnabled()){
                    verifyNumber();
                }
                break;
        }
    }
}
