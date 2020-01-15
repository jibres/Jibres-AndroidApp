package com.jibres.android.activity.tiket.api;

public class TiketListener {
    public interface listTiket{
        void onReceived(String result,int total_page);
        void onFiled(boolean hasNet);
    }

    public interface viewTiket{
        void onReceived(String result);
        void onFiled(boolean hasNet);
    }

    public interface replay{
        void onReceived(String massage, boolean massageIsSend);
        void onFiled(boolean hasNet);
    }

    public interface addTiket{
        void onReceived(String massage,boolean massageIsSend, String id);
        void onFiled(boolean hasNet);
    }

    public interface setStatus{
        void onReceived(String massage, boolean statusISset);
        void onFiled(boolean hasNet);
    }
}
