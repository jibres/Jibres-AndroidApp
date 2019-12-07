package com.jibres.android.utility.Firebase;


import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Sadra Isapanah Amlashi on 10/21/17.
 */

public class FCMInstanceIDService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("FCM", "New notification from: " + remoteMessage.getFrom());
    }
}