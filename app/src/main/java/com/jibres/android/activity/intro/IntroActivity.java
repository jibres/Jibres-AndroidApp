package com.jibres.android.activity.intro;

import android.content.Intent;
import android.graphics.Color;
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
import com.jibres.android.activity.SplashActivity;
import com.jibres.android.activity.WebViewActivity;
import com.jibres.android.managers.AppManager;
import com.jibres.android.managers.JsonManager;
import com.jibres.android.managers.UrlManager;
import com.jibres.android.utility.ColorUtil;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
    int style = 1;
    int padding = 0;

    List<IntroModel> itemIntroList;
    RecyclerViewPager recyclerView;
    IntroAdapter adaptorIntro;
    LinearLayoutManager layout;

    View bg_dots;
    TextView next, skip;

    String bg_from = "#ffffff";
    String bg_to = "#ffffff";
    String color_primary = "#eeeeee";
    String color_secondary = "#eeeeee";

    String lang_next = "Next";
    String lang_back = "Back";
    String lang_skip = "Skip";
    String lang_start = "GetStarted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
//        AppManager.get(getApplication()).save_splash(2);

        bg_dots = findViewById(R.id.bg_dots);
        next    = findViewById(R.id.next);
        skip    = findViewById(R.id.skip);

        itemIntroList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerViewPager_intro);
        adaptorIntro  = new IntroAdapter(this,itemIntroList);
        recyclerView.setAdapter(adaptorIntro);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        layout= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (getPage() == itemIntroList.size()-1){
                    next.setText(lang_start);
                    next.setTag("start");
                }else {
                    next.setText(lang_next);
                    next.setTag("next");
                }

                if (getPage() ==0){
                    skip.setText(lang_skip);
                    skip.setTag("skip");
                }else {
                    skip.setText(lang_back);
                    skip.setTag("back");
                }
            }
        });

        getJson();
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
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", UrlManager.dashboard(getApplication()));
                startActivity(intent);
                break;
        }
    }


    private void getJson(){
        try {
            JSONObject jsonObject = new JSONObject(JsonManager.getJsonIntro(this));

            if (!jsonObject.isNull("theme")){
                switch (jsonObject.getString("theme")){
                    case "Jibres":
                        style = 1;
                        break;
                    default:
                        style = 2;
                        break;
                }
            }
            if (style==2){
                padding = (int) getResources().getDimension(R.dimen._25sdp);
                recyclerView.setPadding(padding,0,padding,0);
            }else {
                recyclerView.setPadding(0,0,0,0);
            }

            if (!jsonObject.isNull("translation")){
                JSONObject translation = jsonObject.getJSONObject("translation");
                if (!translation.isNull("next"))
                    lang_next = translation.getString("next");
                if (!translation.isNull("prev"))
                    lang_back = translation.getString("prev");
                if (!translation.isNull("skip"))
                    lang_skip = translation.getString("skip");
                if (!translation.isNull("start"))
                    lang_start = translation.getString("start");
            }

            if (!jsonObject.isNull("bg")){
                JSONObject bg = jsonObject.getJSONObject("bg");
                if (!bg.isNull("from"))
                    bg_from = bg.getString("from");
                if (!bg.isNull("to"))
                    bg_to = bg.getString("to");
            }
            ColorUtil.setGradient(recyclerView,bg_from,bg_to);

            if (!jsonObject.isNull("color")){
                JSONObject color = jsonObject.getJSONObject("color");
                if (!color.isNull("primary"))
                    color_primary = color.getString("primary");
                if (!color.isNull("secondary"))
                    color_secondary = color.getString("secondary");
            }
            next.setTextColor(Color.parseColor(color_secondary));
            skip.setTextColor(Color.parseColor(color_secondary));

            if (!jsonObject.isNull("page")){
                JSONArray page = jsonObject.getJSONArray("page");
                for (int i = 0; i < page.length(); i++) {
                    JSONObject object = page.getJSONObject(i);

                    String image = null;
                    String title = null;
                    String desc  = null;

                    if (!object.isNull("image"))
                        image = object.getString("image");
                    if (!object.isNull("title"))
                        title = object.getString("title");
                    if (!object.isNull("desc"))
                        desc = object.getString("desc");

                    try {
                        itemIntroList.add(new IntroModel(style,image, title, desc,bg_from,bg_to,color_primary,color_primary));
                        recyclerView.setLayoutManager(layout);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.addItemDecoration(new DotsIndicatorRecyclerView());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}