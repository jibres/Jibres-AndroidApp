package com.jibres.android.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.jibres.android.R;
import com.jibres.android.Static.value;
import com.jibres.android.utility.SaveManager;
import com.jibres.android.utility.set_language_device;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Web_View extends AppCompatActivity {

    boolean errorUrl = true;
    boolean errorNet = false;
    Map<String, String> send_headers = new HashMap<>();
    private String URL = null;
    int oneStart = 0;

    SwipeRefreshLayout swipeRefreshLayout;
    WebView webView_object;

    String[] url_pay = {"https://jeebres.ir/pay/","https://jeebres.ir/ar/pay/","https://jeebres.ir/en/pay/","https://jeebres.ir/pay/"};
    String[] url_del = {"https://jeebres.ir/delneveshte","https://jeebres.ir/ar/delneveshte","https://jeebres.ir/en/delneveshte","https://jeebres.ir/delneveshte"};
    String[] url_news = {"https://jeebres.ir/blog/","https://jeebres.ir/ar/blog/","https://jeebres.ir/en/blog/","https://jeebres.ir/blog/"};
    String url_site = "https://jeebres.ir";


    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private final static int FCR = 1;
    private final static int FILECHOOSER_RESULTCODE = 1;


    @Override
    protected void onResume() {
        errorUrl = true;
        new set_language_device(this);
        super.onResume();
    }

    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        URL = getIntent().getStringExtra("url");
        try {
            setContentView(R.layout.activity_web_view);


            String apikey = SaveManager.get(this).getstring_appINFO().get(SaveManager.apiKey);
            String usercode = SaveManager.get(this).getstring_appINFO().get(SaveManager.userCode);
            String zonid = SaveManager.get(this).getstring_appINFO().get(SaveManager.zoneID);



            send_headers.put("x-app-request", "android");
            send_headers.put("apikey",apikey);
            send_headers.put("usercode",usercode);
            send_headers.put("zonid",zonid);
            send_headers.put("versionCode",String.valueOf(value.versionCode));
            send_headers.put("versionName",value.versionName);

            swipeRefreshLayout = findViewById(R.id.swipRefresh_WebView);
            webView_object = findViewById(R.id.webView_WebView);
            WebSettings webSettings = webView_object.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setAllowContentAccess(true);




            if (URL != null){
                swipeRefreshLayout.setRefreshing(true);
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onRefresh() {
                        webView_object.loadUrl(webView_object.getUrl(), send_headers);
                    }
                });
                webView_object.loadUrl(URL, send_headers);
                webView_object.setWebChromeClient(new WebChromeClient(){

                    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                        if (mUMA != null) {
                            mUMA.onReceiveValue(null);
                        }
                        mUMA = filePathCallback;
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(Web_View.this.getPackageManager()) != null) {
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                                takePictureIntent.putExtra("PhotoPath", mCM);
                            } catch (IOException ex) {
                                Log.e("Webview", "Image file creation failed", ex);
                            }
                            if (photoFile != null) {
                                mCM = "file:" + photoFile.getAbsolutePath();
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                            } else {
                                takePictureIntent = null;
                            }
                        }

                        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        contentSelectionIntent.setType("*/*");
                        Intent[] intentArray;
                        if (takePictureIntent != null) {
                            intentArray = new Intent[]{takePictureIntent};
                        } else {
                            intentArray = new Intent[0];
                        }

                        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                        startActivityForResult(chooserIntent, FCR);
                        return true;
                    }

                });
                webView_object.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                        if (error.getDescription().equals("net::ERR_INTERNET_DISCONNECTED")){
                            if (oneStart == 0)
                            {
                                Dialog_WebView(false);
                                oneStart++;
                            }
                        }
                        Log.d("WebView_onReceivedError", "ErrorCode= "+error.getErrorCode() +" | ErrorDescription= "+error.getDescription());
                    }
                    // in refresh send header
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url, send_headers);
                        if (url.startsWith(WebView.SCHEME_TEL) ||
                                url.startsWith("sms:") ||
                                url.startsWith(WebView.SCHEME_MAILTO) ||
                                url.startsWith(WebView.SCHEME_GEO) ||
                                url.startsWith("maps:") ||
                                url.startsWith("tg:")
                        ) {
                            view.loadUrl(URL, send_headers);
                            errorUrl = false;
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
                            } catch (android.content.ActivityNotFoundException e) {
                            }
                        }else {
                            for (int i = 0; i < 3; i++) {
                                if (url.startsWith(url_pay[i])) {
                                    Log.d("WebView_onReceivedError", "url_pay");

                                    Intent browser = new Intent(Intent.ACTION_VIEW);
                                    browser.setData(Uri.parse(url));
                                    startActivity(browser);
                                    finish();
                                    return true;
                                }
                                else if (!url.substring(0,19).startsWith(url_site)){
                                    Log.d("WebView_onReceivedError", "substring(0,19)");

                                    Intent browser = new Intent(Intent.ACTION_VIEW);
                                    browser.setData(Uri.parse(url));
                                    startActivity(browser);
                                    finish();
                                }
                                else if (url.startsWith(url_del[i])){
                                    Log.d("WebView_onReceivedError", "url_del");

                                    startActivity(new Intent(Web_View.this,Delneveshte.class));
                                    finish();
                                }
                                else if ((url.startsWith(url_news[i])))
                                {
                                    Log.d("WebView_onReceivedError", "url_news");

                                    startActivity(new Intent(Web_View.this,ListNews.class));
                                    finish();
                                }
                            }
                        }

                        return false;
                    }
                    @Override
                    public void onPageFinished(WebView view, String url)
                    {

                        swipeRefreshLayout.setRefreshing(false);
                        if
                        (
                           webView_object.getVisibility() == View.INVISIBLE
                           && !errorNet
                        )
                            {
                                webView_object.setVisibility(View.VISIBLE);
                            }
                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Intent brower = new Intent(Intent.ACTION_VIEW);
            brower.setData(Uri.parse(URL));
            startActivity(brower);
            finish();
        }
    }


    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        this.openFileChooser(uploadMsg, "*/*");
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        this.openFileChooser(uploadMsg, acceptType, null);
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        Web_View.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;
            //Check if response is positive
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == FCR) {
                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
            }
            mUMA.onReceiveValue(results);
            mUMA = null;
        } else {
            if (requestCode == FCR) {
                if (null == mUM) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }
        }
    }

    // Create an image file
    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);


    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        else if(webView_object.canGoBack())
        {
            webView_object.goBack();
        }else {
            super.onBackPressed();
            return;
        }


        doubleBackToExitPressedOnce = true;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 300);
    }


    private void Dialog_WebView(boolean Cancelable) {
        errorNet = true;
        webView_object.setVisibility(View.INVISIBLE);
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        /*Title*/
        builderSingle.setTitle(getString(R.string.errorNet_title_snackBar));
        /*Message*/
        builderSingle.setMessage("");
        /*Button*/
        builderSingle.setPositiveButton(getString(R.string.errorNet_button_snackBar),
                /*Open Url*/
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        swipeRefreshLayout.setRefreshing(true);
                        webView_object.reload();
                        errorNet = false;
                        oneStart = 0;

                    }
                });

        builderSingle.setNeutralButton(getString(R.string.later), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();

            }
        });
        builderSingle.setCancelable(Cancelable);
        builderSingle.show();
    }

}