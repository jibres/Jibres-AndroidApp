package com.jibres.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.jibres.android.function.AddUserTemp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new AddUserTemp(getApplication(), new AddUserTemp.AddUserTempListener() {
            @Override
            public void onReceived() {
                Log.d("amingoli", "onReceived: AddUserTemp..");
            }

            @Override
            public void onFiled() {
                Log.e("amingoli", "onReceived: AddUserTemp ERROR");
            }
        });
    }
}
