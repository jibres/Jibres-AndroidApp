package com.jibres.android.activity.securitysetting;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jibres.android.R;

import java.util.ArrayList;
import java.util.List;

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
        adapter = new SettingAdapter(item, this, this::restartActivity);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        LinearLayoutManager sLayoutManager =
                new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        GetLanguage();
        recyclerView.setLayoutManager(sLayoutManager);
    }

    void GetLanguage() {
        for (int i = 0; i < 50; i++) {
            item.add(new SettingModel(0,0,
                    "https://jibres.com/static/images/logo.png",
                    i+"- Javad Adib","98 919 519 1378","@JavadAdib",true));
            item.add(new SettingModel(0,R.drawable.logo_xml,
                    "",i+"- Setting Error",null,null,true));
            item.add(new SettingModel(0,0,
                    "",i+"- Setting Tester","Summery Test",null,true));
            item.add(new SettingModel(0,0,
                    "",i+"- Setting App",null,null,false));
            item.add(new SettingModel(0,R.drawable.logo_xml,
                    "",i+"- Setting Error",null,null,true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter.notifyDataSetChanged();

        }

    }

    private void restartActivity(int i) {
        Toast.makeText(this, ""+i, Toast.LENGTH_SHORT).show();
    }
}
