package com.jibres.android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.jibres.android.Adaptor.Adaptor_Intro;
import com.jibres.android.Item.item_intro;
import com.jibres.android.R;
import com.jibres.android.Static.file;
import com.jibres.android.Static.format;
import com.jibres.android.utility.Dialog;
import com.jibres.android.utility.FileManager;
import com.jibres.android.utility.SaveManager;
import com.jibres.android.utility.set_language_device;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Intro extends AppCompatActivity {

    List<item_intro> itemIntroList;
    RecyclerViewPager recyclerViewPager;
    Adaptor_Intro adaptorIntro;

    Button nex, prav;
    String nex_string, pravs_string, skip_string;

    @Override
    protected void onResume() {
        new set_language_device(this);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        nex_string = getString(R.string.next);
        pravs_string = getString(R.string.prev);
        skip_string = getString(R.string.start);

        nex = findViewById(R.id.btn_next);
        prav = findViewById(R.id.btn_prav);



        itemIntroList = new ArrayList<>();
        recyclerViewPager  = findViewById(R.id.recyclerViewPager_intro);
        adaptorIntro  = new Adaptor_Intro(this,itemIntroList);
        recyclerViewPager.setAdapter(adaptorIntro);
        final LinearLayoutManager layout = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);


        try {
            String settingApp = FileManager.read_FromStorage(getApplication(), file.setting, format.json);
            JSONObject main_object = new JSONObject(settingApp);
            JSONObject result = main_object.getJSONObject("result");

            JSONArray intro = result.getJSONArray("intro");

            for (int i = 0; i < intro.length(); i++) {
                JSONObject object = intro.getJSONObject(i);
                String image = object.getString("image");
                String title = object.getString("title");
                String desc = object.getString("desc");

                String bg_from = object.getString("bg_from");
                String bg_to = object.getString("bg_to");
                String title_color = object.getString("title_color");
                String desc_color = object.getString("desc_color");

                itemIntroList.add(new item_intro(image,title,desc,bg_from,title_color,desc_color));
                recyclerViewPager.setLayoutManager(layout);
                recyclerViewPager.setItemAnimator(new DefaultItemAnimator());


                if (!object.isNull("btn")){
                    JSONArray btnArray = object.getJSONArray("btn");
                    for (int j = 0; j < btnArray.length(); j++) {
                        JSONObject btnObject = btnArray.getJSONObject(j);
                        String action = btnObject.getString("action");
                        String titleBtn = btnObject.getString("title");

                        switch (action){
                            case "next_img":
                                nex_string = titleBtn;
                                break;
                            case "prev":
                                pravs_string = titleBtn;
                                break;
                            case "start":
                                skip_string = titleBtn;
                                break;
                        }
                    }
                }
            }

            if (itemIntroList.size() == 1){
                nex.setText(nex_string);
                nex.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SaveManager.get(getApplicationContext()).change_intriOpen(true);
                        finish();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                });
            }
            else {
                nex.setText(nex_string);
                prav.setText(pravs_string);
                nex.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (page_intro() == itemIntroList.size()-1){
                            SaveManager.get(getApplicationContext()).change_intriOpen(true);
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            recyclerViewPager.smoothScrollToPosition(recyclerViewPager.getCurrentPosition() + 1);
                        }
                    }
                });
                prav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recyclerViewPager.smoothScrollToPosition(recyclerViewPager.getCurrentPosition() - 1);
                    }
                });
                recyclerViewPager.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
                    @Override
                    public void OnPageChanged(int i, int i1) {
                        if (recyclerViewPager.isScrollContainer()){
                            if (page_intro() == itemIntroList.size()-1){
                                nex.setText(skip_string);
                            }else {
                                nex.setText(nex_string);
                            }

                            if (page_intro() >=1){
                                prav.setVisibility(View.VISIBLE);
                            }else {
                                prav.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            Intent getintent = getIntent();
            new Dialog(Intro.this,getString(R.string.errorNet_title_snackBar),"",getString(R.string.errorNet_button_snackBar),false,getintent);
        }
    }

    private int page_intro(){
        return recyclerViewPager.getCurrentPosition();
    }


}