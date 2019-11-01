package com.jibres.android.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.jibres.android.Activity.Enter;
import com.jibres.android.Activity.MainActivity;

import java.io.IOException;
import java.util.Objects;

public class broadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if(Objects.requireNonNull(action).equals("com.jibres.android.setting")){
            String state = Objects.requireNonNull(intent.getExtras()).getString("extra");

            switch (state){
                case "native":
                    context.startActivity(new Intent(context , Enter.class));
                break;
                case "webview":

                break;

                default:

                break;
            }

        }
    }
}
