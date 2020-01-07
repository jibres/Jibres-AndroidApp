package com.jibres.android.function;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jibres.android.managers.UserManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Objects;

public class Chake {
    public static Boolean userIsAddTemp(Context context){
        return UserManager.getApikey(context) !=null
                && UserManager.getUserCode(context) !=null
                && UserManager.getZonId(context) !=null ;
    }

    public static Boolean userHasMobileNumber(Context context){
        return UserManager.getApikey(context) !=null
                && UserManager.getUserCode(context) !=null
                && UserManager.getZonId(context) !=null
                && UserManager.getMobile(context) !=null ;
    }

    public static boolean internet(Context context){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo =
                Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        if (activeNetworkInfo!=null && activeNetworkInfo.isConnected()){
            try {
                int timeoutMs = 1500;
                Socket sock = new Socket();
                SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);
                sock.connect(sockaddr, timeoutMs);
                sock.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }else {
            return false;
        }
    }
}
