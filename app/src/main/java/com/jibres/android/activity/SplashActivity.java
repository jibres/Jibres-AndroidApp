package com.jibres.android.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.jibres.android.R;
import com.jibres.android.activity.intro.IntroActivity;
import com.jibres.android.activity.language.LanguageActivity;
import com.jibres.android.api.Api;
import com.jibres.android.function.AddUserTemp;
import com.jibres.android.function.AppDetailJson;
import com.jibres.android.managers.AppManager;
import com.jibres.android.managers.JsonManager;
import com.jibres.android.managers.UrlManager;
import com.jibres.android.managers.UrlManager1;
import com.jibres.android.utility.ColorUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    View background;
    TextView app_name, slug,meta;
    LottieAnimationView animation_bg;
    ImageView logo;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        idFinder();
        setDefaultLanguage();
        setValueSplash();

        Api.endPoint(getApplicationContext(), getEndPoint -> {
            if (getEndPoint){
                Api.android(getApplicationContext(), getUrl -> {
                    Api.splash(getApplicationContext(), splashIsSet -> {
                        setValueSplash();
                    });
                });
            }
        });
    }


    void setValueSplash(){
        try {
            String  from = "#ffffff" ,
                    to   = "#ffffff"
            ;
            JSONObject object = new JSONObject(JsonManager.getJsonSplash(getApplication()));
            if (!object.isNull("logo")){
                Glide.with(this).load(object.getString("logo")).into(logo);
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
        return AppManager.getAppLanguage(getApplicationContext());
    }
    void setAppLanguage(String key){
        AppManager.get(context).setAppLanguage(key);
    }
    void setEndPoint(String url){
        UrlManager.save_endPoint(context,url);
    }














    Context context;




    @Override
    protected void onResume() {
        super.onResume();
        helperSplash(true);
    }

    protected void onCreatess(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setURL();
        idFinder();
        ColorUtil.setGradient(background,"#b76cd6","#6d3fc3");

        new AppDetailJson(getApplicationContext(), new AppDetailJson.Listener() {
                    @Override
                    public void isDeprecated() {
                        AppManager.get(getApplicationContext()).setDeprecated(true);
                        deprecatedDialog();
                    }
                    @Override
                    public void onReceived(boolean hasUpdate) {
                        new AddUserTemp(getApplication(), new AddUserTemp.AddUserTempListener() {
                            @Override
                            public void onReceived() {
                                AppManager.get(getApplicationContext()).setDeprecated(false);
                                helperSplash(false);
                                if (hasUpdate){
                                    AppManager.get(getApplicationContext()).setUpdate(true);
                                }else {
                                    AppManager.get(getApplicationContext()).setUpdate(false);
                                }

                            }
                            @Override
                            public void onFiled() {
                                Dialog_WebView(false);
                                Log.e("amingoli", "onReceived: AddUserTemp ERROR");
                            }
                        });
                    }
                    @Override
                    public void onFiled(boolean hasInternet) {
                        Dialog_WebView(false);
                        Log.e("amingoli", "onReceived: AddUserTemp ERROR hasNet: "+hasInternet);
                    }
                });
    }

    private void helperSplash(boolean onResume){
        if (onResume){
            switch (AppManager.getSplash(getApplicationContext())){
                case 0:
                    firstChangeLanguage();
                    break;
                case 1:
                    goIntro();
                    break;
                case 2:
                    changeLanguage();
                    break;
            } 
        }else {
            switch (AppManager.getSplash(getApplicationContext())){
                case 0:
                    firstChangeLanguage();
                    break;
                case 1:
                    goIntro();
                    break;
                case 2:
                    changeLanguage();
                    break;
                default:
                    mainActivity();
                    break;
            }
        }
        
    }

    private void changeLanguage() {
        AppManager.get(getApplicationContext()).save_splash(1);
        Intent intent = new Intent(this, LanguageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void firstChangeLanguage() {
        if (AppManager.getSplash(getApplicationContext())== 0){
            String deviceLanguage = Locale.getDefault().getLanguage();
            if (deviceLanguage.equals("fa")){
                AppManager.get(getApplicationContext()).setAppLanguage(deviceLanguage);
                AppManager.get(getApplicationContext()).save_splash(1);
            }else {
                AppManager.get(getApplicationContext()).save_splash(2);
            }
        }
    }


    private void goIntro() {
        if (AppManager.getSplash(getApplicationContext()) == 1){
            Intent intent = new Intent(getApplication(), IntroActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        }
    }
    private void mainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    public void deprecatedDialog() {
        try {
            View view = findViewById(R.id.item_deprecated);
            Button btnUpdate = view.findViewById(R.id.btn);
            btnUpdate.setOnClickListener(view1 -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(UrlManager1.get.url_update(getApplication())));
                startActivity(intent);
            });
            findViewById(R.id.app_name).setVisibility(View.GONE);
            findViewById(R.id.desc).setVisibility(View.GONE);
            findViewById(R.id.animate_bg).setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
        }catch (Exception e){
            mainActivity();
            Log.e("amingoli", "deprecatedDialog: ",e );
        }
    }

    private void Dialog_WebView(boolean Cancelable) {
//        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
//        /*Title*/
//        builderSingle.setTitle("اینترنت نداری");
//        /*Message*/
//        builderSingle.setMessage("");
//        /*Button*/
//        builderSingle.setPositiveButton("تلاش مجدد",
//                /*Open Url*/
//                (dialog, which) -> {
//                    dialog.dismiss();
//                    finish();
//                    startActivity(getIntent());
//
//                });
//
//        builderSingle.setNeutralButton("خروج", (dialogInterface, i) -> finish());
//        builderSingle.setCancelable(Cancelable);
//        builderSingle.show();
    }


    void idFinder(){
        background = findViewById(R.id.splash_relative_layout);
        animation_bg = findViewById(R.id.animate_bg);
        logo = findViewById(R.id.logo);
        app_name = findViewById(R.id.app_name);
        slug = findViewById(R.id.desc);
        meta = findViewById(R.id.meta);
    }

    void setStringSplash(String AppName,String Desc){
        if (AppName != null && AppName.length()>1){
            app_name.setText(AppName);
        }
        if (slug != null && Desc.length()>1){
            slug.setVisibility(View.VISIBLE);
            slug.setText(Desc);
        }
    }




//    0000000


    void setURL(){

    }
}


/* AppLanguage :
        1- null             -> Device = en | ar | pe -> en
                            -X Device =      fa      -> fa
        2- if (jsonLanguage.equals(AppLanguage'fa') ) -> endPoint_sh fa
            en o = en
            fa o = fa
            fa x = en
            en x = fa
*
*
* */
