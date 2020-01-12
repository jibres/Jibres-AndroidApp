package com.jibres.android.activity.tiket.api;

public class TiketListener {
    public interface listTiket{
        void onReceived(String result,int total_page);
        void onFiled(boolean hasNet);
    }
}
