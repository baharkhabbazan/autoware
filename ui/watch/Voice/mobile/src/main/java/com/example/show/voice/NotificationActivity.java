package com.example.show.voice;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.view.View;

public class NotificationActivity extends Activity {

    public static final String EXTRA_VOICE_REPLY = "extra_voice_realy";
    private static final String GROUP_KEY = "com.polysfactory.androidwearsamplejp.notification.group";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.activity_notification);
        findViewById(R.id.basic_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBasicNotification(context);
            }
        });
        findViewById(R.id.stacking_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStackingNotification(context);
            }
        });
        findViewById(R.id.voice_input_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotificationWithVoiceInputAction(context);
            }
        });
        findViewById(R.id.page_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotificationWithPages(context);
            }
        });
    }

    private void sendBasicNotification(Context context) {
        Intent intent = new Intent(context, NotificationLandingActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification n = new NotificationCompat.Builder(context)
                .setContentTitle("????????????????????????????????????")
                .setContentText("xx????????????????????????????????????????????????")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent)
                .addAction(R.drawable.ic_launcher, "??????", pIntent)
                .addAction(R.drawable.ic_launcher, "??????", pIntent)
                .build();


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, n);
    }

    private void sendStackingNotification(Context context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        Notification card1 = new NotificationCompat.Builder(context)
                .setContentText("?????????????????????")
                .setSmallIcon(R.drawable.ic_launcher)
                .setGroup(GROUP_KEY)
                .setSortKey("0")
                .build();
        notificationManager.notify(101, card1);

        Notification card2 = new NotificationCompat.Builder(context)
                .setContentText("?????????????????????????????????????????????")
                .setSmallIcon(R.drawable.ic_launcher)
                .setGroup(GROUP_KEY)
                .setSortKey("1")
                .build();
        notificationManager.notify(102, card2);

        Notification card3 = new NotificationCompat.Builder(context)
                .setContentText("??????????????????????????????????????????web money????????????????????????????????????????????????")
                .setSmallIcon(R.drawable.ic_launcher)
                .setGroup(GROUP_KEY)
                .setSortKey("2")
                .build();
        notificationManager.notify(103, card3);

        Notification summary = new NotificationCompat.Builder(context)
                .setContentTitle("?????????????????????3???")
                .setSmallIcon(R.drawable.ic_launcher)
                .setGroup(GROUP_KEY)
                .setGroupSummary(true)
                .build();
        notificationManager.notify(100, summary);
    }

    private void sendNotificationWithVoiceInputAction(Context context) {
        String replyLabel = "????????????????????????";
        String[] replyChoices = {"??????", "?????????", "?????????"};

        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(replyLabel)
                .setChoices(replyChoices)
                .build();

        Intent replyIntent = new Intent(this, VoiceInputReceiverActivity.class);
        PendingIntent replyPendingIntent =
                PendingIntent.getActivity(this, 0, replyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_launcher, "??????", replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        Notification notification =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("??????")
                        .setContentText("?????????????????????")
                        .extend(new NotificationCompat.WearableExtender().addAction(action))
                        .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(200, notification);
    }

    private void sendNotificationWithPages(Context context) {
        Notification page =
                new NotificationCompat.Builder(context)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("????????????????????? $7.99"))
                        .build();

        Notification notification =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("xx?????????????????????")
                        .setContentText("??????????????????????????????")
                        .extend(new NotificationCompat.WearableExtender().addPage(page))
                        .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(300, notification);
    }

}
