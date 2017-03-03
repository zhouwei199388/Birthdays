package com.zw.birthdays.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.zw.birthdays.R;
import com.zw.birthdays.utils.ToastUtil;

/**
 * Created by lenovo on 2017/3/3.
 */

public class OneShotAlarm extends BroadcastReceiver {
    private static final String TAG = OneShotAlarm.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        ToastUtil.showToast(context, "sdjflasjdflkja");
        setNotification(context);

    }


    private void setNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("标题")
                .setContentInfo("打飞机阿拉山口戴假发世纪东方垃圾速度开了房间")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(Notification.PRIORITY_MAX)
                .build();
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification);
    }

}
