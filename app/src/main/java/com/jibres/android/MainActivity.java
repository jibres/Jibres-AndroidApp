package com.jibres.android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.jibres.android.curl.UrlManager;
import com.jibres.android.function.AddUserTemp;
import com.jibres.android.intro.IntroActivity;
import com.jibres.android.intro.IntroApi;
import com.jibres.android.language.LanguageActivity;
import com.jibres.android.language.LanguageManager;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


    private void changeLanguage() {
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
        SaveManager.get(getApplication()).save_splash(2);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplication(), IntroActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            finish();
        },700);

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
