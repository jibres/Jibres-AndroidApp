package com.jibres.android.activity.language;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jibres.android.JibresApplication;
import com.jibres.android.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LanguageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LanguageAdapter adapter;
    List<LanguageModel> item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        recyclerView = findViewById(R.id.recycler_view);
        item = new ArrayList<>();
        adapter = new LanguageAdapter(item, this, this::restartActivity);
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

    /*Get Language*/
    void GetLanguage() {
        try {
            JSONObject jsonOffline = new JSONObject(LanguageManager.getJsonLanguage(this));
            JSONObject result = jsonOffline.getJSONObject("result");
            Iterator<?> keys = result.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONObject lang_key = result.getJSONObject(key);
                if (result.get(key) instanceof JSONObject) {
                    if (LanguageManager.getAppLanguage(getApplication())
                            .equals(lang_key.getString("name"))) {
                        item.add(new LanguageModel(
                                lang_key.getString("localname"),
                                lang_key.getString("name"),
                                true,
                                lang_key.getString("api_url")));
                    } else {
                        item.add(new LanguageModel(
                                lang_key.getString("localname"),
                                lang_key.getString("name"),
                                false,
                                lang_key.getString("api_url")));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void restartActivity() {
        ((JibresApplication) getApplication()).refreshLocale(this);
        finish();
    }
}