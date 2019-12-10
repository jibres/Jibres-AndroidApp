package com.jibres.android.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.jibres.android.R;
import com.jibres.android.api.account.Enter;
import com.jibres.android.utility.SaveManager;
import com.jibres.android.utility.getUser;
import com.jibres.android.weight;

@SuppressLint("Registered")
public class LoginActivity extends AppCompatActivity {

    ImageView e_okNumber, e_errorNumber;
    ProgressBar e_progressNumber;
    TextView e_title,e_titleNumber,e_desc
            ,v_titel,v_titleNumber,v_number,v_editNumber,v_resnd;
    CountDownTimer downTimer;
    EditText e_number
            ,edtV1, edtV2, edtV3, edtV4, edtV5;
    View enterXML
         , verifyXML;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        idFinder();

        weight.EditTextVerify(edtV1, edtV2, edtV3, edtV4, edtV5,
                new weight.EditTextVerify_Listener() {
                    @Override
                    public void onReceived() {
                        verifyCode();
                    }

                    @Override
                    public void onError() {

                    }
                });

        Enter.edtText_number(e_number,
                new Enter.edtText_number_Listener() {
            @Override
            public void onReceived() {
                e_okNumber.setVisibility(View.VISIBLE);
            }

            @Override
            public void onChange() {
                if (e_errorNumber.getVisibility() == View.VISIBLE){
                    e_errorNumber.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
                e_errorNumber.setVisibility(View.GONE);
            }
        });
    }


    /** Connection To Server*/
    public void verifyNumber(){
        Enter.phone(this,
                getNumberPhone(),
                new Enter.enterPhone_Listener() {
            @Override
            public void onReceived() {
                enterXML.setVisibility(View.GONE);
                verifyXML.setVisibility(View.VISIBLE);
                e_progressNumber.setVisibility(View.GONE);

                v_number.setText(getNumberPhone());
                edtV1.requestFocus();

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
                        v_resnd.setEnabled(true);
                    }

                }.start();
            }

            @Override
            public void onMassage(String msg) {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                e_progressNumber.setVisibility(View.GONE);
                e_errorNumber.setVisibility(View.VISIBLE);
            }
        });

    }

    private void verifyCode(){
        Enter.verifyCode(this,
                getNumberPhone(),
                getUserVerfycationCode(),
                new Enter.verifyCode_Listener() {
                    @Override
                    public void onReceived(String apiKey_new) {
                        SaveManager.get(getApplicationContext())
                                .change_mobile(
                                        getNumberPhone()
                                );

                        SaveManager.get(getApplication())
                                .change_infoLOGIN(
                                        apiKey_new,
                                        getUser.userCode(getApplication()),
                                        getUser.zoneID(getApplication())
                                );

                        nexActivity();
                    }

                    @Override
                    public void onMassage(String msg) {
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError() {

                    }
                });
    }





    /** Static Method*/
    private void nexActivity() {
        finish();
        startActivity(new Intent(this,MainActivity.class));
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

    public void showVerify_Number(View view){
        e_errorNumber.setVisibility(View.GONE);
        e_progressNumber.setVisibility(View.GONE);
        downTimer.cancel();
        enterXML.setVisibility(View.VISIBLE);
        e_okNumber.setVisibility(View.VISIBLE);
        verifyXML.setVisibility(View.GONE);
    }
    public void showVerify_Code(View view){
        e_okNumber.setVisibility(View.GONE);
        e_progressNumber.setVisibility(View.VISIBLE);
        verifyNumber();
    }

    public void resndSMS(View view) {
        if (view.isEnabled()){
            verifyNumber();
        }
    }
}
