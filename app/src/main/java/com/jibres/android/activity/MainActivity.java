package com.jibres.android.activity;

import android.app.KeyguardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jibres.android.JibresApplication;
import com.jibres.android.R;
import com.jibres.android.activity.intro.IntroActivity;
import com.jibres.android.activity.notif.NotifViewActivity;
import com.jibres.android.activity.security.fingerprint.FingerprintActivity;
import com.jibres.android.activity.security.pincode.PincodeManagerActivity;
import com.jibres.android.managers.AppManager;
import com.jibres.android.managers.UrlManager;
import com.jibres.android.weight.BottomSheetFragment;

public class MainActivity extends AppCompatActivity {
    String[] ac = {"EnterActivity","LanguageActivity",
            "SettingsActivity","key","FingerpringActivity",
            "PinCode","ListTiket","NotifViewActivity",
            "BottomSheet","WebView","intro"
            /*"TestUplloadFile"*/
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("AppManager", "apikey: "+ AppManager.getApikey(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((JibresApplication) getApplication()).refreshLocale(this);

        LinearLayout layout = findViewById(R.id.linear_layout);
        TextView textView;
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,10,10,10);

        for (int i = 0; i < ac.length; i++) {
            textView = new TextView(this);
            textView.setText(ac[i]);
            int finalI = i;
            textView.setOnClickListener(view -> onClicks(ac[finalI]));
            textView.setLayoutParams(layoutParams);
            textView.setBackgroundResource(R.drawable.row_background);
            textView.setPadding(10,10,10,10);
            layout.addView(textView);
        }
    }

    private void onClicks(String activityName){
        Intent intent = null;
        switch (activityName){
            case "LanguageActivity":
                intent= new Intent(getApplication(), WebViewActivity_TEST.class);
                intent.putExtra("url", UrlManager.language(getApplication()));
                break;
            case "key":
                KeyguardManager km = (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intents = km.createConfirmDeviceCredentialIntent("TITLE","DESC");
                    startActivityForResult(intents,RESULT_FIRST_USER);
                }
                break;
            case "FingerpringActivity":
                intent= new Intent(getApplication(), FingerprintActivity.class);
                break;
            case "PinCode":
                intent= new Intent(getApplication(), PincodeManagerActivity.class);
                break;
            case "NotifViewActivity":
                intent= new Intent(getApplication(), NotifViewActivity.class);
                break;
            case "BottomSheet":
                showBottomSheetDialogFragment();
                break;
            case "WebView":
                dialog();
                break;
            case "intro":
                intent = new Intent(getApplication(), IntroActivity.class);
                break;
        }
        if (intent!=null){
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        }
    }

    public void showBottomSheetDialogFragment() {
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.setCancelable(true);
        bottomSheetFragment.setListener(() -> {
            finish();
            startActivity(getIntent());
        });
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }


    void dialog(){
        EditText inputEditTextField = new EditText(this);
        inputEditTextField.setText("jibres.ir");
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Go to WebView")
                .setMessage("Enter Your Url")
                .setView(inputEditTextField)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    String editTextInput = inputEditTextField.getText().toString();
                    if (!editTextInput.startsWith("https://")){
                        editTextInput = "https://"+editTextInput;
                    }
                    Intent intent = new Intent(this, WebViewActivity_TEST.class);
                    intent.putExtra("url",editTextInput);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }
}
