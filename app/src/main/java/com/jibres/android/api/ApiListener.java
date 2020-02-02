package com.jibres.android.api;

public class ApiListener {
    public interface token {
        void onReceived(String token);
        void onFailed(String error);
    }
    public interface userAdd {
        void onReceived();
        void onMassage(String massage);
        void onFailed();
    }
    public interface json {
        void onReceived(boolean status , String value);
    }

}
