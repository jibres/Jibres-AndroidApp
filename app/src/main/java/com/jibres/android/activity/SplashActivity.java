package com.jibres.android.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jibres.android.JibresApplication;
import com.jibres.android.R;
import com.jibres.android.activity.intro.IntroActivity;
import com.jibres.android.activity.intro.IntroApi;
import com.jibres.android.activity.language.LanguageActivity;
import com.jibres.android.activity.language.LanguageManager;
import com.jibres.android.function.AddUserTemp;
import com.jibres.android.function.Chake;
import com.jibres.android.managers.UrlManager;
import com.jibres.android.managers.UserManager;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        new AddUserTemp(getApplication(), new AddUserTemp.AddUserTempListener() {
            @Override
            public void onReceived() {
                Log.d("amingoli", "onReceived: AddUserTemp..");
            }

            @Override
            public void onFiled() {
                Log.e("amingoli", "onReceived: AddUserTemp ERROR");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Chake.userIsAddTemp(this)){
            switch (UserManager.getSplash(this)){
                case 0:
                    changeLanguage();
                    break;
                case 1:
                    goIntro();
                    break;
                default:
                    mainActivity();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }


    private void changeLanguage() {
        UserManager.get(getApplication()).save_splash(1);
        LanguageManager.getAppLanguage(getApplication());
        String deviceLanguage = Locale.getDefault().getLanguage();
        switch (deviceLanguage){
            case "fa":
            case "ar":
                LanguageManager.get(getApplication()).setAppLanguage(deviceLanguage);
                ((JibresApplication) getApplication()).refreshLocale(this);
                goIntro();
                break;
            default:
                Intent intent = new Intent(this, LanguageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
        }
    }
    private void goIntro() {
        new IntroApi();
        UserManager.get(getApplication()).save_splash(2);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplication(), IntroActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        },1200);

    }
    private void mainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    public void deprecatedDialog() {
        try {
            final AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
            /*Title*/
            builderSingle.setTitle(getString(R.string.update));
            /*Message*/
            builderSingle.setMessage(getString(R.string.update_warn));
            /*Button*/
            builderSingle.setPositiveButton(getString(R.string.update),
                    /*Open Url*/
                    (dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(UrlManager.get.url_update(this)));
                        startActivity(intent);
                    });
            builderSingle.setCancelable(false);
            if (!this.isFinishing()){
                builderSingle.show();
            }
        }catch (Exception e){
            mainActivity();
            Log.e("amingoli", "deprecatedDialog: ",e );
        }
    }
}
