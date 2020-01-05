package com.jibres.android.function;

import android.content.Context;
import android.util.Log;

import com.jibres.android.api.Api;
import com.jibres.android.api.ApiListener;

public class AddUserTemp {
    public AddUserTemp(Context context , AddUserTempListener listener) {
        if (!Chake.userIsAddTemp(context)){
            Api.getToken(context,new ApiListener.token() {
                @Override
                public void onReceived(String token) {
                    Api.userAdd(context, token, new ApiListener.userAdd() {
                        @Override
                        public void onReceived() {
                            listener.onReceived();
                            Log.d("amingoli", "AddUserTemp - onReceived");
                        }
                        @Override
                        public void onMassage(String massage) {
                            Log.d("amingoli", "AddUserTemp - onMassage: "+massage);
                        }
                        @Override
                        public void onFailed() {
                            listener.onFiled();
                            Log.e("amingoli", "AddUserTemp - onFailed");
                        }
                    });
                }
                @Override
                public void onFailed(String error) {
                    listener.onFiled();
                    Log.e("amingoli", "AddUserTemp (Token) - onFailed");
                }
            });
        }else {
            listener.onReceived();
        }
    }
    public interface AddUserTempListener{
        void onReceived();
        void onFiled();
    }
}
