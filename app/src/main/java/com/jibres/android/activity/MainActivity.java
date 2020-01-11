package com.jibres.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jibres.android.JibresApplication;
import com.jibres.android.R;
import com.jibres.android.activity.enter.EnterActivity;
import com.jibres.android.activity.language.LanguageActivity;
import com.jibres.android.activity.setting.SettingsActivity;

public class MainActivity extends AppCompatActivity {
    String[] ac = {"EnterActivity","LanguageActivity","SettingsActivity"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((JibresApplication) getApplication()).refreshLocale(this);

        LinearLayout layout = findViewById(R.id.linear_layout);
        TextView textView;
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,10,10,10);

        for (int i = 0; i < ac.length; i++) {
            textView = new TextView(this);
            textView.setText(ac[i]);
            int finalI = i;
            textView.setOnClickListener(view -> onClicks(ac[finalI]));
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundResource(R.drawable.row_background);
            textView.setPadding(10,10,10,10);
            layout.addView(textView);
        }
    }

    private void onClicks(String activityName){
        Intent intent = null;
        switch (activityName){
            case "EnterActivity":
                intent = new Intent(getApplication(), EnterActivity.class);
                break;
            case "LanguageActivity":
                intent= new Intent(getApplication(), LanguageActivity.class);
                break;
            case "SettingsActivity":
                intent= new Intent(getApplication(), SettingsActivity.class);
                break;
        }
        if (intent!=null){
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }
}
