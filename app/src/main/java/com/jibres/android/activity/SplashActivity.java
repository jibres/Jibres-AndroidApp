package com.jibres.android.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jibres.android.JibresApplication;
import com.jibres.android.R;
import com.jibres.android.activity.intro.IntroActivity;
import com.jibres.android.activity.language.LanguageActivity;
import com.jibres.android.activity.language.LanguageManager;
import com.jibres.android.function.AddUserTemp;
import com.jibres.android.function.Chake;
import com.jibres.android.function.GetAppDetail;
import com.jibres.android.managers.UrlManager;
import com.jibres.android.managers.UserManager;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    Boolean seceondStart = false;

    @Override
    protected void onStart() {
        super.onStart();
        seceondStart = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetAppDetail(getApplicationContext(), status -> {
            if (status){
                new AddUserTemp(getApplication(), new AddUserTemp.AddUserTempListener() {
                    @Override
                    public void onReceived() {
                        if (Chake.userIsAddTemp(getApplicationContext())){
                            switch (UserManager.getSplash(getApplicationContext())){
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
                    public void onFiled() {
                        Log.e("amingoli", "onReceived: AddUserTemp ERROR");
                    }
                });
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (UserManager.getSplash(getApplicationContext()) == 0){
            changeLanguage();
        }
    }

    private void changeLanguage() {
        UserManager.get(getApplication()).save_splash(1);
        String deviceLanguage = Locale.getDefault().getLanguage();
        switch (deviceLanguage){
            case "fa":
            case "ar":
                LanguageManager.context(getApplicationContext()).setAppLanguage(deviceLanguage);
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
        UserManager.get(getApplication()).save_splash(2);
        Intent intent = new Intent(getApplication(), IntroActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();

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
