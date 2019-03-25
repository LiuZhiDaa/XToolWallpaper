package app.xtoolwallpaper.com.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.xtoolwallpaper.com.myapplication.base.bean.MessageEvent;
import app.xtoolwallpaper.com.myapplication.utils.UtilsApp;

public class SendNotificationService extends Service {
    @Override
    public void onCreate() {


        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        int Years = 0;
        int Months = 0;
        int Days = 0;
        int hours = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date CurrentDate = formatter.parse(UtilsApp.getTimeToString(System.currentTimeMillis()));
            Years = CurrentDate.getYear(); //获取当前年份(2位)
            Months = CurrentDate.getMonth() + 1; //获取当前月份(0-11,0代表1月)
            // 所以获取当前月份是myDate.getMonth()+1;
            Days = CurrentDate.getDate(); //获取当前日(1-31)
            hours = CurrentDate.getHours(); //获取当前小时数(0-23)
            int min = CurrentDate.getMinutes() + 1; //获取当前分钟数(0-59)
            Log.i("@@@", "SendNotificationService+years==" + Years + "Months==" + Months + "days==" + Days + "hours==" + hours + "min" + min);
            if ((hours >= 8 && hours <= 22) && min == 40) {
                EventBus.getDefault().post(new MessageEvent(1000));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return super.onStartCommand(intent, flags, startId);
    }

//    /**
//     * 整点报时
//     */
//    private void initTimePrompt() {
//        IntentFilter timeFilter = new IntentFilter();
//        timeFilter.addAction(Intent.ACTION_TIME_TICK);
//        registerReceiver(mTimeReceiver, timeFilter);
//    }
//
//    private BroadcastReceiver mTimeReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Calendar cal = Calendar.getInstance();
//            int hour = cal.get(Calendar.HOUR_OF_DAY);
//            int min = cal.get(Calendar.MINUTE);
//            if (min == 0) {
//                //   TXZTtsManager.getInstance().speakText("现在是北京时间" + hour + "点整");
//            } else if (min == 30) {
//                // TXZTtsManager.getInstance().speakText("现在是北京时间" + hour + "点三十分");
//            }
//        }
}
