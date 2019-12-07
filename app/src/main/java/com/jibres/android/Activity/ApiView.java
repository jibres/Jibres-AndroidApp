package com.jibres.android.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jibres.android.R;
import com.jibres.android.api.apiV6;
import com.jibres.android.utility.Dialog;
import com.jibres.android.utility.set_language_device;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiView extends AppCompatActivity {

    TextView tite,text_news;
    ProgressBar progressBar;

    @Override
    protected void onResume() {
        new set_language_device(this);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_view);

        tite = findViewById(R.id.title_api);
        text_news = findViewById(R.id.text_api);
        progressBar = findViewById(R.id.progress_api);

        String getIntent = getIntent().getStringExtra("url");

        apiV6.api(urlApi(getIntent), new apiV6.apiListener() {
            @Override
            public void result(String respone) {
                try {
                    JSONObject main = new JSONObject(respone);
                    JSONObject result = main.getJSONObject("result");
                    String content = result.getString("content");
                    Spanned html_content = Html.fromHtml(content);
                    String title = result.getString("title");

                    progressBar.setVisibility(View.GONE);

                    tite.setText(title);
                    text_news.setText(html_content);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                Intent getintent = getIntent();
                new Dialog(ApiView.this, getString(R.string.errorNet_title_snackBar), "", getString(R.string.errorNet_button_snackBar), false, getintent);

            }
        });


    }

    private String urlApi(String url){
        switch (url){
            case "mission":
                return getString(R.string.url_mission);
            case "about":
                return getString(R.string.url_about);
            case "contact":
                return getString(R.string.url_contact);
            case "vision":
                return getString(R.string.url_vision);
        }
        return null;
    }
}
