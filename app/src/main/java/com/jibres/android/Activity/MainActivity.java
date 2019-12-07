package com.jibres.android.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.jibres.android.utility.changeNumber;
import com.jibres.android.utility.set_language_device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    Boolean hasNewVersion = false;
    LinearLayout mainLayout;
    RecyclerView recylerview;
    Adaptor_Main adaptor_main;
    LinearLayoutManager LayoutManager;
    ArrayList<item_Main> itemMains;
    ProgressBar progressBar;

    LinearLayout updateBox;
    TextView updateBox_title,updateBox_desc,updateBox_skip;
    Button updateBox_btnUpdate;

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
        hasNewVersion = SaveManager.get(getApplication()).getboolen_appINFO().get(SaveManager.hasNewVersion);


        Log.d(tag.get_info_login,
                "\n Api Key: "+apikey+
                "\n User Code: "+usercode+
                "\n Zon ID: "+zonid);

        mainLayout = findViewById(R.id.main_layout);
        progressBar = findViewById(R.id.progress_main);
        updateBox = findViewById(R.id.updateBox);
        updateBox_title = findViewById(R.id.updateBox_title);
        updateBox_desc = findViewById(R.id.updateBox_desc);
        updateBox_skip = findViewById(R.id.updateBox_skip);
        updateBox_btnUpdate = findViewById(R.id.updateBox_btnUpdate);

        String AppLanguage = SaveManager.get(this).getstring_appINFO().get(SaveManager.appLanguage);
        if (AppLanguage.equals("fa") || AppLanguage.equals("ar")){
            ViewCompat.setLayoutDirection(mainLayout,ViewCompat.LAYOUT_DIRECTION_RTL);
        }else {
            ViewCompat.setLayoutDirection(mainLayout,ViewCompat.LAYOUT_DIRECTION_LTR);
        }

        String url_app= getString(R.string.url_app);
        itemMains = new ArrayList<>();
        recylerview = findViewById(R.id.recyclerview);

        adaptor_main = new Adaptor_Main(itemMains, this);
        LayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        apiV6.app0(getApplicationContext(),new apiV6.appListener() {
            @Override
            public void lestener_GetRespone(String result) {
                if (result != null){
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void lestener_Updateversion(String url, String title, String desc) {
                if (hasNewVersion){
                    UpdateBox(url,title,desc,null,null);
                }else {
                    recylerview.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void lestener_baner(String image, String url,String type) {
                Baner(image,url,type);
            }

            @Override
            public void lestener_link_1(String image, String url,String type) {
                Link_1(image,url,type);
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
            public void lestener_title_link(String title,String image,String url,String type) {
                Title_link(title,null,url,type);
            }

            @Override
            public void lestener_title_none(String title) {
                Title_none(title);
            }

            @Override
            public void lestener_salavat(int count) {
                salawat(null,count,null);
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

    private void Baner(String img_url,String link,String type){
        itemMains.add(new item_Main(item_Main.BANER,img_url,link,type,
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
                null,null,null,null,
                null));
        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void slaide(String responeArray){

        itemMains.add(new item_Main(item_Main.SLIDE,
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

        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void Link_1(String img_url,String link,String type){
        itemMains.add(new item_Main(item_Main.LINK_1,null,null,null,
                img_url,link,type,
                null,null,null,null,null,null,
                null,null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,null,
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
                String target = null;
                if (!object_link4.isNull("target")){
                    target = object_link4.getString("target");
                }
                itemLink4.add(new item_link_2_4(image,null,url,target));
            }

            itemMains.add(new item_Main(item_Main.LINK_2,null,null,null,
                    null,null,null,
                    itemLink4.get(0).getImage(),itemLink4.get(1).getImage(),
                    itemLink4.get(0).getUrl(),itemLink4.get(1).getUrl(),
                    itemLink4.get(0).getType(),itemLink4.get(1).getType(),
                    null,null,null,null,null,
                    null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                    null,null,null,null,
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
                null,null,null,
                null,null,null,
                null,null,null,null,null,null,
                img_url,title,desc,link,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,null,
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
                    null,null,null,
                    null,null,null,
                    null,null,null,null,null,null,
                    null,null,null,null,null,
                    itemLink4.get(0).getImage(),itemLink4.get(0).getTex(),itemLink4.get(0).getUrl(),itemLink4.get(0).getType(),
                    itemLink4.get(1).getImage(),itemLink4.get(1).getTex(),itemLink4.get(1).getUrl(),itemLink4.get(1).getType(),
                    itemLink4.get(2).getImage(),itemLink4.get(2).getTex(),itemLink4.get(2).getUrl(),itemLink4.get(2).getType(),
                    itemLink4.get(3).getImage(),itemLink4.get(3).getTex(),itemLink4.get(3).getUrl(),itemLink4.get(3).getType(),
                    null,null,null,null,
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

    private void Title_link(String title,String go,String url,String type){
        itemMains.add(new item_Main(item_Main.TITEL_link,null,null,null,
                null,null,null,

                null,null,null,null,null,null,
                null,null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                title,go,url,type,
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
        itemMains.add(new item_Main(item_Main.TITEL_NONE,null,null,null,
                null,null,null,

                null,null,null,null,null,null,
                null,null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,null,
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

    private void salawat(String title, int counts, String readText){
        Integer count_INT = SaveManager.get(getApplicationContext()).getInt_appINFO().get(SaveManager.salawatCount);
        if (counts > count_INT){
            count_INT = counts;
            SaveManager.get(getApplication()).change_salawatCount(counts);
        }
        String count_spilit = changeNumber.splitDigits(count_INT);
        itemMains.add(new item_Main(item_Main.SALAVAT,null,null,null,
                null,null,null,

                null,null,null,null,null,null,
                null,null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,null,
                null,
                count_spilit,readText,title,
                null,null,null,
                null,null,null,null,
                null,
                null,null,null,null,
                null));
        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void hadith(String title,String link){
        itemMains.add(new item_Main(item_Main.NEWS_TEXT,null,null,null,
                null,null,null,

                null,null,null,null,null,null,
                null,null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,null,
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
                String url_news = object_news.getString("curl");

                Spanned html_contentNews = Html.fromHtml(content_news);
                String text_news = String.valueOf(html_contentNews);
                if (text_news.length() > 110){
                    text_news = text_news.substring(0,110) + " ...";
                }

                itemMains.add(new item_Main(item_Main.NEWS,null,null,null,
                        null,null,null,

                        null,null,null,null,null,null,
                        null,null,null,null,null,
                        null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                        null,null,null,null,
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
            Log.e("news", "news: ",e );
        }



    }

    private void hr(String img_url){
        itemMains.add(new item_Main(item_Main.HR,null,null,null,
                null,null,null,

                null,null,null,null,null,null,
                null,null,null,null,null,
                null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
                null,null,null,null,
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
        itemMains.add(new item_Main(item_Main.LANGUAGE,null,null,null,
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
                null,null,null,null,
                null));
        recylerview.setLayoutManager(LayoutManager);
        recylerview.setItemAnimator(new DefaultItemAnimator());
    }

    private void version(){
        itemMains.add(new item_Main(item_Main.VERSION,null,null,null,
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

    private void UpdateBox(String urlUpdate, String title, String desc, String btn, String skip){
        progressBar.setVisibility(View.GONE);
        recylerview.setVisibility(View.GONE);
        updateBox.setVisibility(View.VISIBLE);
        updateBox.animate().alpha(1).setDuration(500);

        if (urlUpdate == null){
            urlUpdate = "";
        }
        if (title != null){
            updateBox_title.setText(title);
        }
        if (desc != null){
            updateBox_desc.setText(desc);
            updateBox_desc.setVisibility(View.VISIBLE);
        }
        if (btn != null){
            updateBox_btnUpdate.setText(btn);

        }
        if (skip != null){
            updateBox_skip.setText(skip);
        }

        updateBox_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goneUpdateBox();
            }
        });

        final String finalUrlUpdate = urlUpdate;
        updateBox_btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent brower = new Intent(Intent.ACTION_VIEW);
                brower.setData(Uri.parse(finalUrlUpdate));
                startActivity(brower);
                goneUpdateBox();
            }
        });
    }

    private void goneUpdateBox(){
        updateBox.animate().alpha(0).setDuration(200);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateBox.setVisibility(View.GONE);
                recylerview.setVisibility(View.VISIBLE);
            }
        }, 200);
    }
}