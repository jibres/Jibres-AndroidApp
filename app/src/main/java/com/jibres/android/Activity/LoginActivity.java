package com.jibres.android.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
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

@SuppressLint("Registered")
public class LoginActivity extends AppCompatActivity {
    TextView tvTitleVerify, xtext_number_verify,tvResndVerify;
    EditText edtV1, edtV2, edtV3, edtV4, edtV5, edtNumber;
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

        Enter.edtText_verifyCode(edtV1, edtV2, edtV3, edtV4, edtV5,
                new Enter.edtText_verifyCode_Listener() {
            @Override
            public void onReceived() {
                verifyCode();
            }

            @Override
            public void onError() {

            }
        });

        Enter.edtText_number(numberPhone,
                new Enter.edtText_number_Listener() {
            @Override
            public void onReceived() {
                next_img.setVisibility(View.VISIBLE);
            }

            @Override
            public void onChange() {
                if (error_img.getVisibility() == View.VISIBLE){
                    error_img.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
                next_img.setVisibility(View.GONE);
            }
        });

        boxNumber.setVisibility(View.VISIBLE);
        next_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next_img.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                verifyNumber();
            }
        });
    }


    /** Connection To Server*/
    private void verifyNumber(){
        Enter.phone(this,
                getNumberPhone(),
                new Enter.enterPhone_Listener() {
            @Override
            public void onReceived() {
                boxNumber.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                boxVerify.setVisibility(View.VISIBLE);

                xtext_number_verify.setText(getNumberPhone());
                edtV1.requestFocus();

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
                                verifyCode();
                            }
                        });
                    }

                }.start();
            }

            @Override
            public void onMassage(String msg) {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                progress.setVisibility(View.GONE);
                error_img.setVisibility(View.VISIBLE);
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
        return numberPhone.getText().toString().replace("+","");
    }
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
        edtV1 =findViewById(R.id.edt_verify1_login);
        edtV2 =findViewById(R.id.edt_verify2_login);
        edtV3 =findViewById(R.id.edt_verify3_login);
        edtV4 =findViewById(R.id.edt_verify4_login);
        edtV5 =findViewById(R.id.edt_verify5_login);
        tvResndVerify=findViewById(R.id.tvResndVerify_login);
    }
}
