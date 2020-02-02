package com.jibres.android.activity.intro;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.jibres.android.R;
import com.jibres.android.managers.AppManager;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    List<IntroModel> itemIntroList;
    RecyclerViewPager recyclerView;
    IntroAdapter adaptorIntro;

    TextView next, skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        AppManager.get(getApplication()).save_splash(3);

        String i = "https://is3-ssl.mzstatic.com/image/thumb/Purple111/v4/07/2e/5d/072e5d73-6a71-2110-64f3-107eda8a1698/source/512x512bb.jpg";

        next = findViewById(R.id.next);
        skip = findViewById(R.id.skip);

        itemIntroList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewPager_intro);
        adaptorIntro  = new IntroAdapter(this,itemIntroList);
        recyclerView.setAdapter(adaptorIntro);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        final LinearLayoutManager layout = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (getPage() == itemIntroList.size()-1){
                    next.setText("GetStarted");
                    next.setTag("start");
                }else {
                    next.setText("Next");
                    next.setTag("next");
                }

                if (getPage() ==0){
                    skip.setText("Skip");
                    skip.setTag("skip");
                }else {
                    skip.setText("Back");
                    skip.setTag("back");
                }
            }
        });


        itemIntroList.add(new IntroModel(i));
        itemIntroList.add(new IntroModel(i));
        itemIntroList.add(new IntroModel(i));
        itemIntroList.add(new IntroModel(i));
        itemIntroList.add(new IntroModel(i));
        recyclerView.setLayoutManager(layout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DotsIndicatorRecyclerView());


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
    int getPage(){
        return recyclerView.getCurrentPosition();
    }

    public void btn_intro(View view) {
        int page = getPage();
        String tag = view.getTag().toString();
        switch (tag){
            case "next":
                recyclerView.smoothScrollToPosition(page+1);
                break;
            case "back":
                recyclerView.smoothScrollToPosition(page-1);
                break;
            default:
                finish();
                break;
        }
    }
}