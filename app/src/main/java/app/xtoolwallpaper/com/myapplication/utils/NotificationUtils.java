package app.xtoolwallpaper.com.myapplication.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import app.xtoolwallpaper.com.myapplication.MainActivity;
import app.xtoolwallpaper.com.myapplication.R;
import app.xtoolwallpaper.com.myapplication.activity.DetailActivity;
import app.xtoolwallpaper.com.myapplication.base.bean.NotificationBean;
import app.xtoolwallpaper.com.myapplication.base.bean.PushInfoBean;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.os.Build.VERSION_CODES.LOLLIPOP_MR1;

public class NotificationUtils {
    //生成常驻通知栏
    public static void sendNotification(Context context, NotificationBean notificationBean, Bitmap mIconBitmap) {
        PushInfoBean pushInfoBean = notificationBean.getPush_info().get(0);
        //使用RemoteViews来定义通知栏的界面
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_1);
        //设置垃圾大小
        Intent notificationIntent = new Intent(context, DetailActivity.class);
        if (pushInfoBean.getImg_type() == 0) {
            notificationIntent.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_FLAG, true);
            notificationIntent.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL, pushInfoBean.getUrl_img());
        } else {
            notificationIntent.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_DYNAMIC_WALLPAPER_PIC_URL_ID, pushInfoBean.getUrl_img());
            notificationIntent.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_FLAG, false);
            notificationIntent.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_DYNAMIC_WALLPAPER_PIC_URL_TUMB_ID, pushInfoBean.getUrl_tumb());
        }
        notificationIntent.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL_ID, pushInfoBean.getId());
        //携带的数据
        PendingIntent contentIntent = PendingIntent.getActivity(context, 1, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        //当点击id为btn_1的控件时，跳转到SecondActivity
        remoteViews.setOnClickPendingIntent(R.id.rl_remote_view, contentIntent);
        //为remoteView设置图片和文本
        remoteViews.setTextViewText(R.id.tv_title, pushInfoBean.getTitle());
        remoteViews.setTextViewText(R.id.tv_content_text, pushInfoBean.getContent());
        remoteViews.setImageViewBitmap(R.id.iv_image, mIconBitmap);
        //   remoteViews.setImageViewResource(R.id.image, R.drawable.foldleft);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        //   builder.setVibrate(new long[]{100, 200, 300, 200, 100});
//        builder.setSmallIcon(R.drawable.icon_logo);
//        builder.setContent(remoteViews);
        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification no = null;
        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O && Build.VERSION.SDK_INT >= LOLLIPOP_MR1) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.drawable.icon_logo);
            builder.setContent(remoteViews);
            no = builder.build();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN &&
                Build.VERSION.SDK_INT <= LOLLIPOP_MR1) {
            no = new Notification.Builder(context)
                    .setContent(remoteViews)
                    .setSmallIcon(R.drawable.ic_lancher)
                    .setWhen(System.currentTimeMillis())
                    .build();
        } else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "HD wallpaper";
            CharSequence name = "HD wallpaper";
            String Description = "This is HD wallpaper";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
         //   mChannel.setVibrationPattern(new long[]{100, 200, 300, 200, 100});
            mChannel.setShowBadge(false);

            if (nm != null) {
                nm.createNotificationChannel(mChannel);
            }
            no = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_lancher)
                    .setContent(remoteViews).build();
        }

//        no = builder.build();
        //no.flags = Notification.FLAG_ONGOING_EVENT; // 设置常驻 Flag
        if (no!=null){
            no.flags = Notification.FLAG_AUTO_CANCEL;
            Log.d("aaa", "NotificationSuccess");
            nm.notify(233, no);
        }

    }

    //取消常驻通知栏
    public static void disappearNotification(Context context) {

        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(233);
    }
}
