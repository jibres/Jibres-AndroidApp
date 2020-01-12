package com.jibres.android.activity.security.pincode;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jibres.android.R;

public class PincodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pincode);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "No No NO :)", Toast.LENGTH_SHORT).show();
    }
}
