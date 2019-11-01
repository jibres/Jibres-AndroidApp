package com.jibres.android.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jibres.android.Adaptor.Adaptor_Main;
import com.jibres.android.Item.item_Main;
import com.jibres.android.Item.item_link_2_4;
import com.jibres.android.R;
import com.jibres.android.Service.Notification;
import com.jibres.android.Static.tag;
import com.jibres.android.Static.value;
import com.jibres.android.api.apiV6;
import com.jibres.android.utility.Dialog;
import com.jibres.android.utility.SaveManager;
import com.jibres.android.utility.set_language_device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity  {


    RecyclerView recylerview;
    Adaptor_Main adaptor_main;
    LinearLayoutManager LayoutManager;
    ArrayList<item_Main> itemMains;
    ProgressBar progressBar;

    @Override
    protected void onResume() {
        super.onResume();
        new set_language_device(this);
        startService(new Intent(MainActivity.this, Notification.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apikey = SaveManager.get(this).getstring_appINFO().get(SaveManager.apiKey);
        String usercode = SaveManager.get(this).getstring_appINFO().get(SaveManager.userCode);
        String zonid = SaveManager.get(this).getstring_appINFO().get(SaveManager.zoneID);

        Log.d(tag.get_info_login,
                "\n Api Key: "+apikey+
                "\n User Code: "+usercode+
                "\n Zon ID: "+zonid);

        progressBar = findViewById(R.id.progress_mains);

        LinearLayout main_lay = findViewById(R.id.main_layouts);
        String AppLanguage = SaveManager.get(this).getstring_appINFO().get(SaveManager.appLanguage);
        if (Objects.requireNonNull(AppLanguage).equals("fa") || AppLanguage.equals("ar")){
            ViewCompat.setLayoutDirection(main_lay,ViewCompat.LAYOUT_DIRECTION_RTL);
        }else {
            ViewCompat.setLayoutDirection(main_lay,ViewCompat.LAYOUT_DIRECTION_LTR);
        }

        String url_app= getString(R.string.url_app);
        itemMains = new ArrayList<>();
        recylerview = findViewById(R.id.recyclerviews);

        adaptor_main = new Adaptor_Main(itemMains, this);
        LayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        apiV6.app(url_app,new apiV6.appListener() {
            @Override
            public void setting(String mode , String url) {
                Boolean userIsLogin = SaveManager.get(getApplication()).getboolen_appINFO().get(SaveManager.userIsLogin);
                if (!userIsLogin){
                    switch (mode){
                        case "native":
                            startActivity(new Intent(getApplication(),Enter.class));
                            finish();
                            break;
                        case "webview":
                            Intent goWebview = new Intent(getApplication(), Web_View.class);
                            goWebview.putExtra("url",url);
                            goWebview.putExtra("refresh",false);
                            startActivity(goWebview);
                            finish();
                            break;

                    }
                }

            }

            @Override
            public void lestener_baner(String image, String url) {
                Baner(image,url);
            }

            @Override
            public void lestener_link_1(String image, String url) {
                Link_1(image,url);
            }

            @Override
            public void lestener_link_2(String link2Array) {
                Link_2(link2Array);
            }

            @Override
            public void lestener_link_3_desc(String title,String desc,String image,String url) {
                Link_3_desc(image,title,desc, url);
            }

            @Override
            public void lestener_link_4(String link4Array) {
                Link_4(link4Array);
            }

            @Override
            public void lestener_title_link(String title,String image,String url) {
                Title_link(title,null,url);
            }

            @Override
            public void lestener_title_none(String title) {
                Title_none(title);
            }

            @Override
            public void lestener_salavat(String count) {
                salavat(null,count,null);
            }

            @Override
            public void lestener_hadith() {

            }

            @Override
            public void lestener_slider(String respone) {
                slaide(respone);
            }

            @Override
            public void lestener_news(String newsArray) {
                news(newsArray);
            }

            @Override
            public void lestener_hr() {
                hr(null);
            }

            @Override
            public void lestener_language() {
                changeLanguage();
            }

            @Override
            public void lestener_versionApp() {
                progressBar.setVisibility(View.GONE);
                recylerview.setVisibility(View.VISIBLE);
                version();
            }

            @Override
            public void error() {
                progressBar.setVisibility(View.VISIBLE);
                recylerview.setVisibility(View.GONE);
                Intent getintent = getIntent();
                new Dialog(MainActivity.this,getString(R.string.errorNet_title_snackBar),"",getString(R.string.errorNet_button_snackBar),true,getintent);
            }
        });






        recylerview.setAdapter(adaptor_main);

    }



    private void Baner(String img_url,String link){
        itemMains.add(new item_Main(item_Main.BANER,img_url,link,
                null,null,
                null,null,null,null,
                null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,
                null,
                null,null,null,
                null,null,null,
                null,null,null,null,
                null,
                null,null,null,null,
                null));
        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void slaide(String responeArray){

        itemMains.add(new item_Main(item_Main.SLIDE,
                null,null,
                null,null,
                null,null,null,null,
                null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,
                null,
                null,null,null,
                null,null,null,
                null,null,null,null,
                null,
                null,responeArray,null,null,
                null));

        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void Link_1(String img_url,String link){
        itemMains.add(new item_Main(item_Main.LINK_1,null,null,
                img_url,link,
                null,null,null,null,
                null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,
                null,
                null,null,null,
                null,null,null,
                null,null,null,null,
                null,
                null,null,null,null,
                null));
        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void Link_2(String responeArray){

        try {
            JSONArray link4Array = new JSONArray(responeArray);

            List<item_link_2_4> itemLink4 = new ArrayList<>();
            for (int i = 0; i < link4Array.length(); i++) {
                JSONObject object_link4 = link4Array.getJSONObject(i);
                String image = object_link4.getString("image");
                String url = object_link4.getString("url");
                itemLink4.add(new item_link_2_4(image,null,url,null));
            }

            itemMains.add(new item_Main(item_Main.LINK_2,null,null,
                    null,null,

                    itemLink4.get(0).getImage(),itemLink4.get(1).getImage(),
                    itemLink4.get(0).getUrl(),itemLink4.get(1).getUrl(),
                    null,null,null,null,
                    null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                    null,null,null,
                    null,
                    null,null,null,
                    null,null,null,
                    null,null,null,null,
                    null,
                    null,null,null,null,
                    null));
            recylerview.setLayoutManager(LayoutManager);
            recylerview.setItemAnimator(new DefaultItemAnimator());


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private void Link_3_desc(String img_url,String title,String desc,String link){
        itemMains.add(new item_Main(item_Main.LINK_Desc,
                null,null,
                null,null,
                null,null,null,null,
                img_url,title,desc,link,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,
                null,
                null,null,null,
                null,null,null,
                null,null,null,null,
                null,
                null,null,null,null,
                null));
        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void Link_4(String responeArray){

        try {
            JSONArray link4Array = new JSONArray(responeArray);

            List<item_link_2_4> itemLink4 = new ArrayList<>();
            for (int i = 0; i < link4Array.length(); i++) {
                String type_link4 = null;
                JSONObject object_link4 = link4Array.getJSONObject(i);
                String image = object_link4.getString("image");
                String text = object_link4.getString("text");
                String url = object_link4.getString("url");
                if (!object_link4.isNull("type")){
                    type_link4 = object_link4.getString("type");
                }


                itemLink4.add(new item_link_2_4(image,text,url,type_link4));
            }

            itemMains.add(new item_Main(item_Main.LINK_4,
                    null,null,
                    null,null,
                    null,null,null,null,
                    null,null,null,null,
                    itemLink4.get(0).getImage(),itemLink4.get(0).getTex(),itemLink4.get(0).getUrl(),itemLink4.get(0).getType(),
                    itemLink4.get(1).getImage(),itemLink4.get(1).getTex(),itemLink4.get(1).getUrl(),itemLink4.get(1).getType(),
                    itemLink4.get(2).getImage(),itemLink4.get(2).getTex(),itemLink4.get(2).getUrl(),itemLink4.get(2).getType(),
                    itemLink4.get(3).getImage(),itemLink4.get(3).getTex(),itemLink4.get(3).getUrl(),itemLink4.get(3).getType(),
                    null,null,null,
                    null,
                    null,null,null,
                    null,null,null,
                    null,null,null,null,
                    null,
                    null,null,null,null,
                    null));
            recylerview.setLayoutManager(LayoutManager);
            recylerview.setItemAnimator(new DefaultItemAnimator());


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void Title_link(String title,String go,String url){
        itemMains.add(new item_Main(item_Main.TITEL_link,null,null,
                null,null,

                null,null,null,null,
                null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                title,go,url,
                null,
                null,null,null,
                null,null,null,
                null,null,null,null,
                null,
                null,null,null,null,
                null));
        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void Title_none(String title){
        itemMains.add(new item_Main(item_Main.TITEL_NONE,null,null,
                null,null,

                null,null,null,null,
                null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,
                title,
                null,null,null,
                null,null,null,
                null,null,null,null,
                null,
                null,null,null,null,
                null));
        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void salavat(String title,String count,String readText){
        itemMains.add(new item_Main(item_Main.SALAVAT,null,null,
                null,null,

                null,null,null,null,
                null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,
                null,
                count,readText,title,
                null,null,null,
                null,null,null,null,
                null,
                null,null,null,null,
                null));
        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void hadith(String title,String link){
        itemMains.add(new item_Main(item_Main.NEWS_TEXT,null,null,
                null,null,

                null,null,null,null,
                null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,
                null,
                null,null,null,
                title,link,null,
                null,null,null,null,
                null,
                null,null,null,null,
                null));
        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void news(String responeArray){
        try {
            JSONArray newsArray = new JSONArray(responeArray);

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject object_news = newsArray.getJSONObject(i);
                JSONObject meta = object_news.getJSONObject("meta");
                String title_news = object_news.getString("title");
                String content_news = object_news.getString("content");
                String image_news = meta.getString("thumb");
                    content_news.replace("\n","");
                String id_news = object_news.getString("id");
                String url_news = object_news.getString("link");

                Spanned html_contentNews = Html.fromHtml(content_news);
                String text_news = String.valueOf(html_contentNews);
                if (text_news.length() > 110){
                    text_news = text_news.substring(0,110) + " ...";
                }

                itemMains.add(new item_Main(item_Main.NEWS,null,null,
                        null,null,

                        null,null,null,null,
                        null,null,null,null,
                        null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                        null,null,null,
                        null,
                        null,null,null,
                        null,null,null,
                        image_news,title_news,text_news,id_news,
                        null,
                        null,null,null,null,
                        null));
                recylerview.setLayoutManager(LayoutManager);
                recylerview.setItemAnimator(new DefaultItemAnimator());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private void hr(String img_url){
        itemMains.add(new item_Main(item_Main.HR,null,null,
                null,null,

                null,null,null,null,
                null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,
                null,
                null,null,null,
                null,null,null,
                null,null,null,null,
                img_url,
                null,null,null,null,
                null));
        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void changeLanguage(){
        itemMains.add(new item_Main(item_Main.LANGUAGE,null,null,
                null,null,

                null,null,null,null,
                null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,
                null,
                null,null,null,
                null,null,null,
                null,null,null,null,
                null,
                null,null,null,null,
                null));
        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void version(){
        itemMains.add(new item_Main(item_Main.VERSION,null,null,
                null,null,

                null,null,null,null,
                null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,
                null,
                null,null,null,
                null,null,null,
                null,null,null,null,
                null,
                null,null,null,null,
                value.versionName));
        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.tost_exit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 1500);
    }


}
