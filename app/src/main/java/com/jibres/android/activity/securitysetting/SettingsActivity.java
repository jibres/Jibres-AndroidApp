package com.jibres.android.activity.securitysetting;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jibres.android.R;
import com.jibres.android.activity.language.LanguageActivity;

import java.util.ArrayList;
import java.util.List;

import static com.jibres.android.activity.Constans.ON_CLICK_AC_LANGUAGE;


public class SettingsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SettingAdapter adapter;
    List<SettingModel> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        recyclerView = findViewById(R.id.recycler_view);
        item = new ArrayList<>();
        adapter = new SettingAdapter(item, this, this::onCliked);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        LinearLayoutManager sLayoutManager =
                new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        GetLanguage();
        recyclerView.setLayoutManager(sLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }

    void GetLanguage() {

        item.add(new SettingModel(0,
                0,null,
                "App Setting",null,null,false));
        item.add(new SettingModel(ON_CLICK_AC_LANGUAGE,
                0,null,
                "Change Language",null,null,true));

        item.add(new SettingModel(0,0,
                "https://jibres.com/static/images/logo.png",
                "Javad Adib","98 919 519 1378","@JavadAdib",true));


    }

    private void onCliked(int status) {
        Intent intents = null;
        switch (status){
            case 16:
                intents = new Intent(this, LanguageActivity.class);
                break;
        }
        if (intents!=null){
            intents.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intents);
        }
    }
}
