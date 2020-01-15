package com.jibres.android.activity.tiket.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jibres.android.R;
import com.jibres.android.activity.tiket.adapter.TiketViewAdapter;
import com.jibres.android.activity.tiket.api.TiketApi;
import com.jibres.android.activity.tiket.api.TiketListener;
import com.jibres.android.activity.tiket.model.TiketViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TiketViewActivity extends AppCompatActivity {
    String id_tiket;

    RecyclerView recyclerView;
    TiketViewAdapter adapter;
    List<TiketViewModel> item;
    LinearLayoutManager sLayoutManager;
    int item_size = 0;

    EditText editMassage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_view);

        id_tiket = getIntent().getStringExtra("id");

        recyclerView = findViewById(R.id.recycler_view);
        item = new ArrayList<>();
        adapter = new TiketViewAdapter(item, this, this::onCliked);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        sLayoutManager =
                new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        GetLanguage();
        recyclerView.setLayoutManager(sLayoutManager);

        editMassage = findViewById(R.id.massage);

    }

    void GetLanguage() {
        TiketApi.viewTiket(getApplicationContext(), id_tiket, new TiketListener.viewTiket() {
            @Override
            public void onReceived(String value) {
                try {
                    JSONArray result = new JSONArray(value);
                    for (int i = item_size; i < result.length(); i++) {
                        item_size = i;
                        JSONObject object = result.getJSONObject(i);

                        String title = null ,
                                avatar = null,
                                displayname = null,
                                file = null,
                                datecreated = null;

                        if (!object.isNull("datecreated")){
                            datecreated = object.getString("datecreated");
                        }
                        if (!object.isNull("title")){
                            title = object.getString("title")+"\n"+
                                    object.getString("content");
                        }else {
                            title = object.getString("content");
                        }
                        if (!object.isNull("avatar")){
                            avatar = object.getString("avatar");
                        }
                        if (!object.isNull("displayname")){
                            displayname = object.getString("displayname");
                        }
                        if (!object.isNull("file")){
                            file = object.getString("file");
                        }


                        object.getString("displayname");
                        object.getString("avatar");
                        object.getString("datecreated");
                        object.getString("content");
                        object.getString("title");

                        item.add(new TiketViewModel(avatar,
                                displayname,
                                title,
                                datecreated,
                                file));
                    }
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    adapter.notifyDataSetChanged();
                    sLayoutManager.scrollToPosition(item.size()-1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFiled(boolean hasNet) {
                Log.d("amingoli", "onFiled: "+hasNet);
            }
        });

    }

    private void onCliked(int status) {
    }

    public void send_massage(View view) {
        if (getEditMassage()!=null){
            TiketApi.replay(getApplicationContext(), id_tiket, getEditMassage(), null,
                    new TiketListener.replay() {
                        @Override
                        public void onReceived(String massage , boolean isSend) {
                            Toast.makeText(TiketViewActivity.this, massage, Toast.LENGTH_SHORT).show();
                            if (isSend){
                                item_size++;
                                editMassage.getText().clear();
                                editMassage.setText("");
                                new Handler().postDelayed(() -> GetLanguage(),200);
                            }
                        }

                        @Override
                        public void onFiled(boolean hasNet) {

                        }
                    });
        }

    }

    @SuppressLint("IntentReset")
    public void choose_file(View view) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK_ACTIVITY, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent,2);
    }


    String getEditMassage(){
        String text = editMassage.getText().toString();
        if (text.length()>1){
            return editMassage.getText().toString();
        }
        return null;
    }
}
