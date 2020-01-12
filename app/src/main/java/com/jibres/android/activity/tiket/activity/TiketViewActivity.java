package com.jibres.android.activity.tiket.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jibres.android.R;
import com.jibres.android.activity.setting.SettingAdapter;
import com.jibres.android.activity.setting.SettingModel;
import com.jibres.android.activity.tiket.api.TiketApi;
import com.jibres.android.activity.tiket.api.TiketListener;
import com.jibres.android.weight.DividerItemDecoratorWeighet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TiketViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SettingAdapter adapter;
    List<SettingModel> item;

    RecyclerView.ItemDecoration dividerItemDecoration;
    LinearLayoutManager sLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_view);

        recyclerView = findViewById(R.id.recycler_view);
        item = new ArrayList<>();
        adapter = new SettingAdapter(item, this, this::onCliked);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        sLayoutManager =
                new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);

        dividerItemDecoration =
                new DividerItemDecoratorWeighet(ContextCompat.getDrawable(getApplicationContext(),
                        R.drawable.divider));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        GetLanguage(getIntent().getStringExtra("id"));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(sLayoutManager);
    }

    void GetLanguage(String tiket) {
        TiketApi.viewTiket(getApplicationContext(), tiket, new TiketListener.viewTiket() {
            @Override
            public void onReceived(String value) {

                try {
                    JSONArray result = new JSONArray(value);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject object = result.getJSONObject(i);

                        String title = null ,
                                avatar = null,
                                displayname = null,
                                datecreated = null;

                        if (!object.isNull("datecreated")){
                            datecreated = object.getString("datecreated");
                        }
                        if (!object.isNull("title")){
                            title = object.getString("title");
                        }
                        if (!object.isNull("avatar")){
                            avatar = object.getString("avatar");
                        }
                        if (!object.isNull("displayname")){
                            displayname = object.getString("displayname");
                        }


                        object.getString("displayname");
                        object.getString("avatar");
                        object.getString("datecreated");
                        object.getString("content");
                        object.getString("title");

                        item.add(new SettingModel(900, 0,
                                avatar,
                                displayname,
                                title,
                                object.getString("content")
                                        +datecreated,
                                false));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        adapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFiled(boolean hasNet) {

            }
        });

    }

    private void onCliked(int status) {
    }
}
