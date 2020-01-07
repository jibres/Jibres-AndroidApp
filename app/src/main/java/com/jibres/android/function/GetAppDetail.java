package com.jibres.android.function;

import android.content.Context;

import com.jibres.android.api.Api;
import com.jibres.android.api.ApiListener;

public class GetAppDetail {
    private Context context;

    public GetAppDetail(Context context) {
        this.context = context;

        Api.getAppDetail(context, new ApiListener.appDetail() {
            @Override
            public void onReceived(boolean status, String value) {
                if (status && value != null){

                }
            }
        });
    }
}
