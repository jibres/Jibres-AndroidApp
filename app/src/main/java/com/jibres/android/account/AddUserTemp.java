package com.jibres.android.account;

import android.content.Context;
import android.util.Log;

import com.jibres.android.api.Api;
import com.jibres.android.api.ApiListener;

public class AddUserTemp {
    private Context context;

    public AddUserTemp(Context context) {
        this.context = context;

        Api.getToken(context,new ApiListener.token() {
            @Override
            public void onReceived(String token) {
                Api.userAdd(context, token, new ApiListener.userAdd() {
                    @Override
                    public void onReceived() {
                        Log.d("amingoli", "AddUserTemp - onReceived");
                    }

                    @Override
                    public void onMassage(String massage) {
                        Log.d("amingoli", "AddUserTemp - onMassage: "+massage);
                    }

                    @Override
                    public void onFailed() {
                        Log.e("amingoli", "AddUserTemp - onFailed");
                    }
                });
            }

            @Override
            public void onFailed(String error) {
                Log.e("amingoli", "AddUserTemp (Token) - onFailed");
            }
        });
    }
}
