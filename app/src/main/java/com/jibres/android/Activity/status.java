package com.jibres.android.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jibres.android.R;
import com.jibres.android.utility.set_language_device;

public class status extends AppCompatActivity {

    String status,amount = null;

    @Override
    protected void onResume() {
        new set_language_device(this);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        finish();
       /* Uri data = getIntent().getData();
        Uri uri = Uri.parse(String.valueOf(data));
        if (uri.getQueryParameter("status") != null){
            status = uri.getQueryParameter("status");
            amount = uri.getQueryParameter("amount");
            Toast.makeText(this, status+"|"+amount, Toast.LENGTH_SHORT).show();
        }*/
    }
}
