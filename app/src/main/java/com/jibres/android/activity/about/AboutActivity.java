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
import com.jibres.android.activity.HtmlViewActivity;
import com.jibres.android.activity.setting.SettingAdapter;
import com.jibres.android.activity.setting.SettingModel;
import com.jibres.android.weight.DividerItemDecoratorWeighet;

import java.util.ArrayList;
import java.util.List;

import static com.jibres.android.activity.Constans.ON_CLICK_AC_ABOUT_APP;
import static com.jibres.android.activity.Constans.ON_CLICK_AC_HTML_VIEW_PRIVACYPOLICE;
import static com.jibres.android.activity.Constans.ON_CLICK_AC_HTML_VIEW_TREMOFUSE;

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
        item.add(new SettingModel(ON_CLICK_AC_ABOUT_APP,
                0,null,
                "درباره این نسخه",null,null,true));
        item.add(new SettingModel(ON_CLICK_AC_HTML_VIEW_TREMOFUSE,
                0,null,
                "شرایط استفاده",null,null,true));
        item.add(new SettingModel(ON_CLICK_AC_HTML_VIEW_PRIVACYPOLICE,
                0,null,
                "حریم خصوصی",null,null,true));
    }

    private void onCliked(int status) {
        Intent intents = null;
        switch (status){
            case 21:
                intents = new Intent(this, AboutAppActivity.class);
                break;
            case 24:
                intents = new Intent(this, HtmlViewActivity.class);
                intents.putExtra("url","https://jibres.com/fa/terms");
                intents.putExtra("title","شرایط استفاده");
                break;
            case 25:
                intents = new Intent(this, HtmlViewActivity.class);
                intents.putExtra("url","https://jibres.com/fa/privacy");
                intents.putExtra("title","حریم خصوصی");
                break;
        }
        if (intents!=null){
            intents.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intents);
        }
    }
}
