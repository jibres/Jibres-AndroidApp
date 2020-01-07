package com.jibres.android;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.jibres.android.activity.language.LanguageManager;

import java.util.Locale;

public class JibresApplication extends Application {

    public static final String TAG = JibresApplication.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static JibresApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized JibresApplication getInstance() {
        return mInstance;
    }

    public  RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void refreshLocale(@NonNull Context context) {
        final String language = LanguageManager.getAppLanguage(this);

        final Locale locale;
        if (language != null) {
            locale = new Locale(language);
//      locale = new Locale("ar");
        } else {
            // nothing to do...
            return;
        }

        updateLocale(context, locale);
        final Context appContext = context.getApplicationContext();
        if (context != appContext) {
            updateLocale(appContext, locale);
        }
    }

    private void updateLocale(@NonNull Context context, @NonNull Locale locale) {
        final Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLayoutDirection(config.locale);
        }
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}