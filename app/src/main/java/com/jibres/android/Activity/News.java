package com.jibres.android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jibres.android.Adaptor.Adaptor_Main;
import com.jibres.android.Item.item_Main;
import com.jibres.android.R;
import com.jibres.android.api.apiV6;
import com.jibres.android.utility.Dialog;
import com.jibres.android.utility.SaveManager;
import com.jibres.android.utility.set_language_device;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class News extends AppCompatActivity {

    RecyclerView recylerviewss;
    Adaptor_Main adaptor_main;
    LinearLayoutManager LayoutManager;
    ArrayList<item_Main> itemMains;

    @Override
    protected void onResume() {
        new set_language_device(this);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        RelativeLayout news_mainLayout = findViewById(R.id.news_mainLayout);
        String AppLanguage = SaveManager.get(this).getstring_appINFO().get(SaveManager.appLanguage);
        if (AppLanguage.equals("fa") || AppLanguage.equals("ar")){
            ViewCompat.setLayoutDirection(news_mainLayout,ViewCompat.LAYOUT_DIRECTION_RTL);
        }else {
            ViewCompat.setLayoutDirection(news_mainLayout,ViewCompat.LAYOUT_DIRECTION_LTR);
        }

        itemMains = new ArrayList<>();
        recylerviewss = findViewById(R.id.recyclerview_news);
        adaptor_main = new Adaptor_Main(itemMains, this);
        LayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recylerviewss.setAdapter(adaptor_main);

        String url = SaveManager.get(getApplicationContext()).getstring_appINFO().get(SaveManager.apiV6_URL)+ com.jibres.android.Static.url.news;

        final String ID = getIntent().getStringExtra("id");

        getNews(url,ID);
    }

    private void getNews(String url,String ID){
        apiV6.news(url,ID,new apiV6.newsLinstener() {
            @Override
            public void resultValueNes(String respone) {
                try {
                    hiddenProgress();
                    JSONObject result = new JSONObject(respone);
                    String content = result.getString("content");
                    Spanned html_content = Html.fromHtml(content);
                    String title = result.getString("title");

                    JSONObject meta = result.getJSONObject("meta");
                    String thumb = meta.getString("thumb");
                    itemMains.add(new item_Main(item_Main.NEWS_TEXT,null,null,null,
                            null,null,null,

                            null,null,null,null,null,null,
                            null,null,null,null,null,
                            null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                            null,null,null,null,
                            null,
                            null,null,null,
                            title,String.valueOf(html_content),thumb,
                            null,null,null,null,
                            null,
                            null,null,null,null,
                            null));
                    recylerviewss.setLayoutManager(LayoutManager);
                    recylerviewss.setItemAnimator(new DefaultItemAnimator());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void resultGaleryNws(String responeArray) {
                itemMains.add(new item_Main(item_Main.SLIDE_NEWS,
                        null,null,null,
                        null,null,null,
                        null,null,null,null,null,null,
                        null,null,null,null,null,
                        null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                        null,null,null,null,
                        null,
                        null,null,null,
                        null,null,null,
                        null,null,null,null,
                        null,
                        null,responeArray,null,null,
                        null));

                recylerviewss.setLayoutManager(LayoutManager);
                recylerviewss.setItemAnimator(new DefaultItemAnimator());

            }

            @Override
            public void failedValueNes(String error) {
                Intent getintent = getIntent();
                new Dialog(News.this,getString(R.string.errorNet_title_snackBar),"",getString(R.string.errorNet_button_snackBar),false,getintent);
            }
        });
    }

    private void hiddenProgress(){
        try {
            ProgressBar progressBar = findViewById(R.id.showNews_progress);
            if (progressBar.getVisibility() == View.VISIBLE){
                progressBar.setVisibility(View.GONE);
            }
        }catch (Exception e){}
    }
}
