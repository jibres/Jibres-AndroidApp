package com.jibres.android.utility.Firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.jibres.android.Activity.Splash;
import com.jibres.android.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static com.jibres.android.utility.Firebase.Attribuites.FCM_ACTION_CLICK_NOTIFICATION;
import static com.jibres.android.utility.Firebase.Attribuites.FCM_ACTION_NEW_NOTIFICATION;

/**
 * Created by Sadra Isapanah Amlashi on 10/21/17.
 */

public class FCMService extends FirebaseMessagingService {

    private static final String TAG = FCMService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "New notification from: " + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Notification message: " + remoteMessage.getNotification());
            notificationMessageReceived(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0) {
            Log.i(TAG, "Data Payload: " + remoteMessage.getData());
            dataMessageReceived(remoteMessage.getData());
        }

    }


    private void notificationMessageReceived(String title, String body){

        Intent intent = new Intent(this, Splash.class); //Open activity if clicked on notification
        PendingIntent pendingIntent;

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction(FCM_ACTION_CLICK_NOTIFICATION);
        pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500,500,500,500,500}; //Notification vibration pattern

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); //Notification alert sound

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE,1,1)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logo_xml)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary));


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(getNotify(), notificationBuilder.build());

    }

    private void dataMessageReceived(java.util.Map<String, String> body) {

        try{

            JSONObject bodyObjects = new JSONObject(body);

            if(bodyObjects.getString("type").equals("banner")){
                showNotificationWithBanner(bodyObjects.getString("title"), bodyObjects.getString("message"), bodyObjects.getString("banner_url"));
            }else if(bodyObjects.getString("type").equals("dialog_message")){
                broadcastTheMessage(bodyObjects.getString("title"), bodyObjects.getString("message"));
            }

        }catch (Exception e){
            Log.e("There is a problem","Exception: "+e);
        }

    }

    private void showNotificationWithBanner(String title, String message, String bannerURL){

        Intent intent = new Intent(this, Splash.class); //Open activity if clicked on notification
        PendingIntent pendingIntent;

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setAction(FCM_ACTION_CLICK_NOTIFICATION);
        pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        long[] pattern = {500,500,500,500,500}; //Notification vibration pattern

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); //Notification alert sound

        //Style for showing notification With Banner
        Bitmap remote_picture = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
        try {
            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(bannerURL).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        notiStyle.bigPicture(remote_picture);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setLights(Color.BLUE,1,1)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setSmallIcon(getNotificationIcon())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setStyle(notiStyle);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(getNotify(), notificationBuilder.build());

    }

    private void broadcastTheMessage(String title, String message){

        Intent notification = new Intent(FCM_ACTION_NEW_NOTIFICATION);
        notification.putExtra("title", title);
        notification.putExtra("message", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(notification);
    }

    //Random and unique notification ID
    public int getNotify(){
        return Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(new Date()));
    }

    //In LOLLIPOP and later the small icon has white tint
    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.logo_xml : R.drawable.logo_xml;
    }

}