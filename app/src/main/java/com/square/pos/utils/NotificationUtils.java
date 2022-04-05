package com.square.pos.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;

import com.square.pos.R;
import com.square.pos.activity_motor.BikeRegistrationActivity;
import com.square.pos.activity.DashboardActivity;
import com.square.pos.activity.health.CitySumActivity;
import com.square.pos.activity.health.HealthViewActivity;
import com.square.pos.activity_pos.MyPoliciesActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 */

public class NotificationUtils {

    public static void creatMessageNotification(Context context, String title, String message,
                                                String notificationType) {
        SharedPreferences preferences = context.getSharedPreferences(String.valueOf(R.string.app_name),
                MODE_PRIVATE);
        String userId = preferences.getString(AppUtils.PRIMARY_ID, "");
        new Intent();
        Intent intent = new Intent(context, DashboardActivity.class);

        if (!TextUtils.isEmpty(notificationType))
        switch (notificationType) {
            case "1":
                intent = new Intent(context, BikeRegistrationActivity.class);
                preferences.edit().putString(AppUtils.VEHICLE_TYPE, "1").apply();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case "2":
                intent = new Intent(context, BikeRegistrationActivity.class);
                preferences.edit().putString(AppUtils.VEHICLE_TYPE, "2").apply();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case "3":
                intent = new Intent(context, BikeRegistrationActivity.class);
                preferences.edit().putString(AppUtils.VEHICLE_TYPE, "3").apply();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case "4":
                intent = new Intent(context, BikeRegistrationActivity.class);
                preferences.edit().putString(AppUtils.VEHICLE_TYPE, "4").apply();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case "5":
                preferences.edit().putString(AppUtils.VEHICLE_TYPE, "5").apply();
                intent = new Intent(context, BikeRegistrationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case "6":
                if (TextUtils.isEmpty(userId))
                    intent = new Intent(context, HealthViewActivity.class);
                else intent = new Intent(context, CitySumActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case "8":
                intent = new Intent(context, MyPoliciesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            default:
                intent = new Intent(context, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Uri defaultSoundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.notifications); //Here is FILE_NAME is the name of file that you want to play

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int notifyID = 1;
            String CHANNEL_ID = context.getResources().getString(R.string.app_name);// The id of the channel.
            CharSequence name = context.getString(R.string.app_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            @SuppressLint("WrongConstant") NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mNotificationManager.createNotificationChannel(mChannel);
            // Create a notification and set the notification channel.
            Notification notification = new Notification.Builder(context)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setSmallIcon(getNotificationIcon())
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setChannelId(CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setColor(context.getResources().getColor(R.color.colorAccent))
                    .setSound(defaultSoundUri)
                    .setShowWhen(true)
                    .setAutoCancel(true)
                    .setStyle(new Notification.BigTextStyle().bigText(message))
                    .build();
            mNotificationManager.notify(0, notification);
        } else {
            Notification notification = new NotificationCompat.Builder(context)
                    .setSmallIcon(getNotificationIcon())
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setContentTitle(title)
                    .setColor(context.getResources().getColor(R.color.colorAccent))
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
                    .setShowWhen(true)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                    .build();
            mNotificationManager.notify(0, notification);

//            Notification notification =
//                    new NotificationCompat.Builder(context)
//                            .setSmallIcon(R.drawable.logo)
//                            .setContentTitle("My notification")
//                            .setContentText("Hello World!")
//                            .build();
        }
    }

    public static int getNotificationIcon() {
        boolean useWhiteIcon = true;
        return R.drawable.ic_notification;
    }

}
