package com.jibres.android.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jibres.android.R;
import com.jibres.android.activity.intro.IntroActivity;
import com.jibres.android.api.Api;
import com.jibres.android.managers.AppManager;
import com.jibres.android.managers.JsonManager;
import com.jibres.android.managers.UrlManager;
import com.jibres.android.utility.ColorUtil;
import com.jibres.android.weight.BottomSheetFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    View background;
    TextView app_name, slug,meta;
    LottieAnimationView animation_bg;
    ImageView logo;
    int sleep = 500;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onWindowFocusChanged(true);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        idFinder();
        setDefaultLanguage();
        setValueSplash();
        API();
    }

    void API(){
        Api.endPoint(getApplicationContext(), getEndPoint -> {
            if (getEndPoint){
                Api.android(getApplicationContext(), getUrl -> {
                    if (getApiIntro()){
                        Api.intro(getApplicationContext(), introIsGet -> {
                            helperActivty();
                        });
                    }else {
                        helperActivty();
                    }
                    Api.splash(getApplicationContext(), splashIsSet -> {});
                });
            }else showBottomSheetDialogFragment();
        });
    }

    void helperActivty(){
        Intent intent;
        switch (getSplash()){
            case 0:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url",UrlManager.language(this));
                intent.putExtra("intro",true);
                AppManager.get(getApplication()).save_splash(1);
                break;
            case 1:
                intent = new Intent(this, IntroActivity.class);
                AppManager.get(getApplication()).save_splash(2);
                break;
            default:
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url",UrlManager.dashboard(getApplication()));
                break;
        }
        new Handler().postDelayed(() -> {
            finish();
            startActivity(intent);
            setValueSplash();
        },sleep);
    }

    void setValueSplash(){
        try {
            String from="#ffffff", to= "#ffffff";
            int style = 1;
            JSONObject object = new JSONObject(JsonManager.getJsonSplash(getApplication()));
            if (!object.isNull("theme")){
                switch (object.getString("theme")){
                    case "Jibres":
                        style = 1;
                        break;
                    default:
                        style = 2;
                        break;
                }
            }
            if (style==2){
                animation_bg.setVisibility(View.INVISIBLE);
            }else {
                animation_bg.setVisibility(View.VISIBLE);
            }
            if (!object.isNull("sleep")){
                sleep = object.getInt("sleep");
            }
            if (!object.isNull("logo")){
                try {
                    Glide.with(this)
                            .applyDefaultRequestOptions(new RequestOptions()
                                    .placeholder(R.drawable.logo)
                                    .error(R.drawable.logo))
                            .load(object.getString("logo"))
                            .into(logo);
                }catch (Exception e){

                }
            }
            if (!object.isNull("title")){
                app_name.setText(object.getString("title"));
            }
            if (!object.isNull("desc")){
                slug.setText(object.getString("desc"));
            }
            if (!object.isNull("meta")){
                meta.setText(object.getString("meta"));
            }
            if (!object.isNull("bg")){
                JSONObject bg = object.getJSONObject("bg");
                if (!bg.isNull("from"))
                    from = bg.getString("from");
                if (!bg.isNull("to"))
                    to = bg.getString("to");
            }
            if (!object.isNull("color")){
                JSONObject color = object.getJSONObject("color");
                if (!color.isNull("primary")){
                    app_name.setTextColor(Color.parseColor(color.getString("primary")));
                    slug.setTextColor(Color.parseColor(color.getString("primary")));
                }
                if (!color.isNull("secondary")){
                    meta.setTextColor(Color.parseColor(color.getString("secondary")));
                }
            }
            if (from!=null || to!=null) ColorUtil.setGradient(background,from,to);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void setDefaultLanguage(){
        if (getAppLanguage()==null){
            String deviceLanguage = Locale.getDefault().getLanguage();
            if (deviceLanguage.equals("fa")){
                AppManager.get(getApplicationContext()).setAppLanguage(deviceLanguage);
            }else {
                AppManager.get(getApplicationContext()).setAppLanguage("en");
            }
        }
    }

    String getAppLanguage(){
        return AppManager.getAppLanguage(getApplication());
    }
    void setAppLanguage(String key){
        AppManager.get(getApplication()).setAppLanguage(key);
    }
    void setEndPoint(String url){
        UrlManager.save_endPoint(getApplication(),url);
    }
    int getSplash(){
        return AppManager.get(getApplication()).getUserInfo_int().get(AppManager.splash);
    }
    boolean getApiIntro(){
        return getSplash()==0;
    }

    void idFinder(){
        background = findViewById(R.id.splash_relative_layout);
        animation_bg = findViewById(R.id.animate_bg);
        logo = findViewById(R.id.logo);
        app_name = findViewById(R.id.app_name);
        slug = findViewById(R.id.desc);
        meta = findViewById(R.id.meta);
    }

    public void showBottomSheetDialogFragment() {
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.setCancelable(false);
        bottomSheetFragment.setListener(() -> {
            bottomSheetFragment.dismiss();
            finish();
            startActivity(getIntent());
        });
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }
}
