package com.jibres.android.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jibres.android.R;
import com.jibres.android.activity.intro.IntroActivity;
import com.jibres.android.activity.language.LanguageActivity;
import com.jibres.android.activity.language.LanguageManager;
import com.jibres.android.function.AddUserTemp;
import com.jibres.android.function.AppDetailJson;
import com.jibres.android.managers.UrlManager;
import com.jibres.android.managers.UserManager;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        helperSplash(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new AppDetailJson(getApplicationContext(), new AppDetailJson.Listener() {
                    @Override
                    public void isDeprecated() {
                        deprecatedDialog();
                    }
                    @Override
                    public void onReceived(boolean hasUpdate) {
                        new AddUserTemp(getApplication(), new AddUserTemp.AddUserTempListener() {
                            @Override
                            public void onReceived() {
                                helperSplash(false);
                                if (hasUpdate){}
                            }
                            @Override
                            public void onFiled() {
                                Log.e("amingoli", "onReceived: AddUserTemp ERROR");
                            }
                        });
                    }
                    @Override
                    public void onFiled(boolean hasInternet) {
                    }
                });
    }

    private void helperSplash(boolean onResume){
        Log.d("amingolis", "helperSplash: "+UserManager.getSplash(getApplicationContext()));
        if (onResume){
            switch (UserManager.getSplash(getApplicationContext())){
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
            switch (UserManager.getSplash(getApplicationContext())){
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
        UserManager.get(getApplicationContext()).save_splash(1);
        Intent intent = new Intent(this, LanguageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void firstChangeLanguage() {
        if (UserManager.getSplash(getApplicationContext())== 0){
            String deviceLanguage = Locale.getDefault().getLanguage();
            if (deviceLanguage.equals("fa")){
                LanguageManager.context(getApplicationContext()).setAppLanguage(deviceLanguage);
                UserManager.get(getApplicationContext()).save_splash(1);
            }else {
                UserManager.get(getApplicationContext()).save_splash(2);
            }
        }
    }


    private void goIntro() {
        if (UserManager.getSplash(getApplicationContext()) == 1){
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
                intent.setData(Uri.parse(UrlManager.get.url_update(getApplication())));
                startActivity(intent);
            });
            findViewById(R.id.progress).setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
        }catch (Exception e){
            mainActivity();
            Log.e("amingoli", "deprecatedDialog: ",e );
        }
    }
}
