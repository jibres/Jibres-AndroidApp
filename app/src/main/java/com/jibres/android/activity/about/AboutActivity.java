package com.jibres.android.activity.about;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jibres.android.R;
import com.jibres.android.activity.language.LanguageActivity;
import com.jibres.android.activity.profile.ProfileActivity;
import com.jibres.android.activity.setting.SettingAdapter;
import com.jibres.android.activity.setting.SettingModel;
import com.jibres.android.weight.DividerItemDecoratorWeighet;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SettingAdapter adapter;
    List<SettingModel> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        recyclerView = findViewById(R.id.recycler_view);
        item = new ArrayList<>();
        adapter = new SettingAdapter(item, this, this::onCliked);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        LinearLayoutManager sLayoutManager =
                new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);

        RecyclerView.ItemDecoration dividerItemDecoration =
                new DividerItemDecoratorWeighet(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.divider));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        GetLanguage();
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(sLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();
    }

    void GetLanguage() {
        item.add(new SettingModel(0,
                0,null,
                "درباره این نسخه",null,null,true));
        item.add(new SettingModel(0,
                0,null,
                "شرایط استفاده",null,null,true));
        item.add(new SettingModel(0,
                0,null,
                "حریم خصوصی",null,null,true));
    }

    private void onCliked(int status) {
        Intent intents = null;
        switch (status){
            case 16:
                intents = new Intent(this, LanguageActivity.class);
                break;
            case 10:
                intents = new Intent(this, ProfileActivity.class);
                break;
            case 20:
                intents = new Intent(this, AboutActivity.class);
                break;
        }
        if (intents!=null){
            intents.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intents);
        }
    }
}
