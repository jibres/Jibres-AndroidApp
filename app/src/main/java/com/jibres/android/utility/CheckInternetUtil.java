package com.jibres.android.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import java.io.IOException;
import java.util.Objects;

public class CheckInternetUtil {
    boolean i = false;

    public CheckInternetUtil(Context context, Listener listener) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo =
                Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            isOnline();
        } else {
        }

        new Handler().postDelayed(() -> {
            if (i) {
                listener.onReceived(true);
            } else {
                listener.onReceived(false);
            }
        }, 1500);
    }

    public interface Listener {
        void onReceived(boolean status);

        void onMassage(String msg);
    }

    private boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            i = true;
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
