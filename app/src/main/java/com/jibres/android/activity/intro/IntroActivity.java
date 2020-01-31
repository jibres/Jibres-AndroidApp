package com.jibres.android.activity.intro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import com.jibres.android.R;
import com.jibres.android.managers.AppManager;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    List<IntroModel> itemIntroList;
    RecyclerViewPager recyclerView;
    IntroAdapter adaptorIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        AppManager.get(getApplication()).save_splash(3);

        String i = "https://is3-ssl.mzstatic.com/image/thumb/Purple111/v4/07/2e/5d/072e5d73-6a71-2110-64f3-107eda8a1698/source/512x512bb.jpg";



        itemIntroList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewPager_intro);
        adaptorIntro  = new IntroAdapter(this,itemIntroList);
        recyclerView.setAdapter(adaptorIntro);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DotsIndicatorRecyclerView(getApplicationContext()));
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        final LinearLayoutManager layout = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);


        itemIntroList.add(new IntroModel(i));
        itemIntroList.add(new IntroModel(i));
        itemIntroList.add(new IntroModel(i));
        itemIntroList.add(new IntroModel(i));
        itemIntroList.add(new IntroModel(i));
        itemIntroList.add(new IntroModel(i));
        itemIntroList.add(new IntroModel(i));
        recyclerView.setLayoutManager(layout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        /*Api.getAppDetail(getApplicationContext(), (status, value) -> {
            if (status && value!=null){
                try {
                    JSONObject result = new JSONObject(value);
                    JSONArray intro = result.getJSONArray("intro");
                    for (int i = 0; i < intro.length(); i++) {
                        JSONObject object = intro.getJSONObject(i);
                        String image= null;
                        if (!object.isNull("image")){
                            image = object.getString("image");
                        }
                        String title = object.getString("title");
                        String desc = object.getString("desc");

                        String bg_from = object.getString("bg_from");
                        String bg_to = object.getString("bg_to");
                        String title_color = object.getString("title_color");
                        String desc_color = object.getString("desc_color");

                        itemIntroList.add(new IntroModel(image,title,desc,bg_from,bg_to,title_color,desc_color));
                        recyclerView.setLayoutManager(layout);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }

    private int page_intro(){
        return recyclerView.getCurrentPosition();
    }
}