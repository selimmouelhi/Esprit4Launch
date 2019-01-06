package com.example.selimmouelhi.esprit4launch.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.selimmouelhi.esprit4launch.R;


import com.example.selimmouelhi.esprit4launch.activities.HomeActivity;
import com.example.selimmouelhi.esprit4launch.entities.User;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.Map;

public class FireBaseNotificationService  extends FirebaseMessagingService {


    private static final String TAG = "firebaseTag";
    public static final String FOLLOWER_CHANNEL_ID = "follower_notif_id";
    public static final String FOLLOWER_CHANNEL_NAME = "follower_notification";
    public static final String FRIEND_CHANNEL_ID = "friend_notif_id";
    public static final String FRIEND_CHANNEL_NAME = "friend_notification";
    public static final String RESTAURANT_CHANNEL_ID = "restaurant_notif_id";
    public static final String RESTAURANT_CHANNEL_NAME = "restaurant_notification";
    public static final int FOLLOWER_TYPE = 1;
    public static final int FRIEND_TYPE = 2;
    public static final int RESTAURANT_TYPE = 3;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "onMessageReceived: ");
        final Map<String, String> data = remoteMessage.getData();
        System.out.println(data.get("notif_type"));

    //now do the work

        switch (Integer.valueOf(data.get("notif_type"))) {
            case FOLLOWER_TYPE:
                UserService.getInstance().getUserById(data.get("notif_user_id"), new UserService.GetUserByIdCallBack() {
                    @Override
                    public void onCompletion(User user) {

                                        Intent intent = new Intent(FireBaseNotificationService.this, HomeActivity.class);
                                       /* intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_ID, data.get("notif_id"));
                                        intent.putExtra(RecipeDetailsActivity.EXTRA_SHOULD_FINISH, false);*/

                                        sendNotification(FOLLOWER_CHANNEL_ID,
                                                FOLLOWER_CHANNEL_NAME,
                                                data.get("title"),
                                                data.get("body"),

                                                intent);


                    }
                });
                break;

            case FRIEND_TYPE:
                UserService.getInstance().getUserById(data.get("notif_user_id"), new UserService.GetUserByIdCallBack() {
                    @Override
                    public void onCompletion(User user) {

                        Intent intent = new Intent(FireBaseNotificationService.this, HomeActivity.class);
                                       /* intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_ID, data.get("notif_id"));
                                        intent.putExtra(RecipeDetailsActivity.EXTRA_SHOULD_FINISH, false);*/

                        sendNotification(FRIEND_CHANNEL_ID,
                                FRIEND_CHANNEL_NAME,
                                data.get("title"),
                                data.get("body"),

                                intent);


                    }
                });
                break;

            case RESTAURANT_TYPE:
                UserService.getInstance().getUserById(data.get("notif_user_id"), new UserService.GetUserByIdCallBack() {
                    @Override
                    public void onCompletion(User user) {

                        Intent intent = new Intent(FireBaseNotificationService.this, HomeActivity.class);
                                       /* intent.putExtra(RecipeDetailsActivity.EXTRA_RECIPE_ID, data.get("notif_id"));
                                        intent.putExtra(RecipeDetailsActivity.EXTRA_SHOULD_FINISH, false);*/

                        sendNotification(RESTAURANT_CHANNEL_ID,
                                RESTAURANT_CHANNEL_NAME,
                                data.get("title"),
                                data.get("body"),

                                intent);


                    }
                });
                break;

        }

    }


    /*
    private void sendNotification(String messageBody){

        Intent intent = new Intent(this,HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //build notification





        //show notif
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Esprit4Launch")
                .setContentText(messageBody)
                .setContentIntent(pendingIntent);
        notificationManager.notify(0,builder.build());

    }*/


    private void sendNotification(String channelId, String channelName, String title, String messageBody, Intent actionIntent) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{0, 100, 200, 100});


        if (actionIntent != null) {
            builder.setContentIntent(PendingIntent.getActivity(this, 0, actionIntent, 0));
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Log.d(TAG, "sendNotification: onMessageReceived >26");
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        } else {
            Log.d(TAG, "sendNotification: onMessageReceived <26");
            builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
        }

        notificationManager.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE), builder.build());
    }

    public void handleIntent(Intent intent) {
        Log.d(TAG, "handleIntent: ");
    }
}
