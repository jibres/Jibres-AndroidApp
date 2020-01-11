package com.jibres.android.activity.security.sessions;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.jibres.android.R;

public class SessionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        getSessionList();

        removeSession(null,true);
    }

    private void getSessionList(){
        SessionsApi.list(getApplicationContext(), new SessionsApi.sessionsListListener() {
            @Override
            public void onReceived(String value) {
                if (value !=null){
                    Log.d("amingoli", "onReceived: (getSessionList) "+value);
                }else {
                    Log.d("amingoli", "onReceived: (getSessionList) No Have Session ");
                }
            }

            @Override
            public void onMassage(String value) {
                Log.d("amingoli", "onMassage: (getSessionList) "+value);
            }

            @Override
            public void onFiled(boolean hasNet) {
                Log.d("amingoli", "onFiled: (getSessionList) "+hasNet);
            }
        });
    }

    private void removeSession(String id,boolean removeAllSession){
        if (id !=null && !removeAllSession){
            SessionsApi.remove(getApplicationContext(), id, false
                    , new SessionsApi.sessionsRemoveListener() {
                @Override
                public void onReceived(boolean removed) {
                    Log.d("amingoli", "onReceived: (removeSession) "+removed);
                }

                @Override
                public void onMassage(String value) {
                    Log.d("amingoli", "onMassage: (removeSession) "+value);
                }

                @Override
                public void onFiled(boolean hasNet) {
                    Log.d("amingoli", "onFiled: (removeSession) "+hasNet);
                }
            });
        }else {
            SessionsApi.remove(getApplicationContext(), new SessionsApi.sessionsRemoveListener() {
                @Override
                public void onReceived(boolean removed) {
                    Log.d("amingoli", "onReceived-removeAllSession: (removeSession) "+removed);
                }
                @Override
                public void onMassage(String value) {
                    Log.d("amingoli", "onMassage-removeAllSession: (removeSession) "+value);
                }
                @Override
                public void onFiled(boolean hasNet) {
                    Log.d("amingoli", "onFiled-removeAllSession: (removeSession) "+hasNet);
                }
            });
        }

    }
}
