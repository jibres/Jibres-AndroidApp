package com.jibres.android.activity.security.pincode;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jibres.android.R;
import com.jibres.android.managers.AppManager;

public class PincodeManagerActivity extends AppCompatActivity {
    int lookStatus =0;
    String pinCode = null;
    EditText editText;
    TextView desc ,textError;

    View boxError;

    boolean hasPin = false;
    boolean one = true;
    String one_pin = null;

    @Override
    protected void onStart() {
        super.onStart();
        hasPinCode();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincode_manager);
        lookStatus = AppManager.getLookStatus(this);
        pinCode = AppManager.getPinCode(this);

        View view = findViewById(R.id.pincode);
        editText = view.findViewById(R.id.edt_pin);
        desc = view.findViewById(R.id.desc);

        boxError = view.findViewById(R.id.box_error);
        textError = view.findViewById(R.id.text_error);

        editText.setInputType(InputType.TYPE_CLASS_NUMBER);

        findViewById(R.id.button).setOnClickListener(view1 -> onClickOk());




    }

    private void onClickOk(){
        if (editText.getText().length() ==4){
            error(null);
            if (hasPin){
                if (getTextEdt().equals(AppManager.getPinCode(this))){
                    hasPin = false;
                    desc.setText("کد جدید را وارد کنید");
                    editText.getText().clear();
                }else {
                    error("کد اشتباه است");
                }
            } else if (one || one_pin == null){
                one = false;
                one_pin = getTextEdt();
                editText.getText().clear();
                editText.requestFocus();
                desc.setText("دوباره کد جدید را وارد کنید");
            }else {
                if (one_pin.equals(getTextEdt())){
                    AppManager.get(this).save_lookStatus(2);
                    AppManager.get(this).save_pinCode(getTextEdt());
                    finish();
                }else {
                    error("کد مطابقت ندارد");
                }
            }
        }else {
            error("باید ۴ رقم وارد کنید");
        }
    }


    private String getTextEdt(){
        return editText.getText().toString();
    }




    private void hasPinCode(){
        if (AppManager.getLookStatus(this)!=0 ||
                AppManager.getPinCode(this)!=null){
            desc.setText("کد قبلی خود را وارد کنید");
            hasPin = true;
        }
    }

    private void error(String txt){
        if (txt!=null){
            boxError.setVisibility(View.VISIBLE);
            textError.setText(txt);
        }else {
            boxError.setVisibility(View.GONE);
        }
    }
}
