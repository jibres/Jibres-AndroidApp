package com.jibres.android.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jibres.android.Adaptor.LanguageAdaptor;
import com.jibres.android.Item.item_Language;
import com.jibres.android.R;
import com.jibres.android.api.apiV6;
import com.jibres.android.utility.Dialog;
import com.jibres.android.utility.SaveManager;
import com.jibres.android.utility.set_language_device;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Language extends AppCompatActivity {


    RecyclerView relv_Language;
    List<item_Language> mItem ;
    LanguageAdaptor mAdapter;

    @Override
    protected void onResume() {
        new set_language_device(this);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        mItem = new ArrayList<>();
        String url = getString(R.string.url_language);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        /*add RecyclerView and Adapter*/
        relv_Language = findViewById(R.id.lv_Language);
        mAdapter = new LanguageAdaptor(mItem, this);

        /*Set*/
        LinearLayoutManager sLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        relv_Language.setLayoutManager(sLayoutManager);
        relv_Language.setItemAnimator(new DefaultItemAnimator());
        relv_Language.setHasFixedSize(true);
        relv_Language.setAdapter(mAdapter);
        GetLanguage(url);
    }

    /*Get Language*/
    void GetLanguage(String url) {
        apiV6.getLanguage(url,new apiV6.languageListener() {
            @Override
            public void result(String respone) {
                String appLanguage = SaveManager.get(getApplicationContext()).getstring_appINFO().get(SaveManager.appLanguage);
                try {
                    JSONObject jsonOffline = new JSONObject(respone);
                    boolean ok = jsonOffline.getBoolean("ok");
                    JSONObject result = jsonOffline.getJSONObject("result");
                    JSONObject lang_list = jsonOffline.getJSONObject("result");
                    Iterator<?> keys = lang_list.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        JSONObject lang_key = lang_list.getJSONObject(key);
                        if (lang_list.get(key) instanceof JSONObject) {
                            if (appLanguage.equals(lang_key.getString("name"))) {
                                mItem.add(new item_Language(
                                        lang_key.getString("localname"),
                                        lang_key.getString("name"),
                                        true));
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mItem.add(new item_Language(
                                        lang_key.getString("localname"),
                                        lang_key.getString("name"),
                                        false));
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                Intent getintent = getIntent();
                new Dialog(Language.this,getString(R.string.errorNet_title_snackBar),"",getString(R.string.errorNet_button_snackBar),false,getintent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
