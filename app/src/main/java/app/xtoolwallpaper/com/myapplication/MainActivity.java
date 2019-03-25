package app.xtoolwallpaper.com.myapplication;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;

import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import app.xtoolwallpaper.com.myapplication.adapter.MainPagerAdapter;
import app.xtoolwallpaper.com.myapplication.base.BaseActivity;
import app.xtoolwallpaper.com.myapplication.base.bean.MessageEvent;
import app.xtoolwallpaper.com.myapplication.base.bean.DateList;
import app.xtoolwallpaper.com.myapplication.base.bean.Lineinfo;
import app.xtoolwallpaper.com.myapplication.base.bean.NotificationBean;
import app.xtoolwallpaper.com.myapplication.base.bean.ReportBean;
import app.xtoolwallpaper.com.myapplication.fragment.DynamicFragment;
import app.xtoolwallpaper.com.myapplication.fragment.SettingFragment;
import app.xtoolwallpaper.com.myapplication.fragment.StaticFragment;
import app.xtoolwallpaper.com.myapplication.fragment.ui.MainContract;
import app.xtoolwallpaper.com.myapplication.fragment.ui.MainPresenter;
import app.xtoolwallpaper.com.myapplication.utils.DateBean;
import app.xtoolwallpaper.com.myapplication.utils.NotificationUtils;
import app.xtoolwallpaper.com.myapplication.utils.SPUtil;
import app.xtoolwallpaper.com.myapplication.utils.UtilsApp;
import app.xtoolwallpaper.com.myapplication.view.ntb.NavigationTabBar;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    List<Fragment> fragmentList;
    TimerChangeReceiver mTimeChangeReceiver;
    private boolean mReceiverTag = false;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void initView() {
        initUI();
    }

    private void initUI() {
        if (SPUtil.getOpen()) {
            ReportBean reportBean = new ReportBean();
            reportBean.setBrand(GC.brand);
            reportBean.setCreatTime(UtilsApp.getDateToString(System.currentTimeMillis()));
            reportBean.setDeviceid(GC.deviceid);
            reportBean.setSystemVersion(GC.systemVersion);
            reportBean.setHeight(GC.height);
            reportBean.setWideth(GC.wideth);
            reportBean.setBehaviorId(1);
            SPUtil.setOpen(false);
            mPresenter.upReport(reportBean);
        } else {
            ReportBean reportBean = new ReportBean();
            reportBean.setBrand(GC.brand);
            reportBean.setCreatTime(UtilsApp.getDateToString(System.currentTimeMillis()));
            reportBean.setDeviceid(GC.deviceid);
            reportBean.setSystemVersion(GC.systemVersion);
            reportBean.setHeight(GC.height);
            reportBean.setWideth(GC.wideth);
            reportBean.setBehaviorId(2);
            mPresenter.upReport(reportBean);
        }
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        fragmentList = new ArrayList<>();
        fragmentList.add(new StaticFragment());
        fragmentList.add(new DynamicFragment());
        fragmentList.add(new SettingFragment());
        //  InitDateTimes();
        if (!mReceiverTag) {
            mReceiverTag = true;
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_TIME_TICK);
            mTimeChangeReceiver = new TimerChangeReceiver();
            registerReceiver(mTimeChangeReceiver, filter);
        }


        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), MainActivity.this, fragmentList));
        viewPager.setOffscreenPageLimit(4);
        final String[] colorss = getResources().getStringArray(R.array.default_preview);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_static_wallpaper_narmal),
                        Color.parseColor(colorss[5]))
                        .title("Static")
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_static_wallpaper_selected))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_dynamic_wallpaper_normal),
                        Color.parseColor(colorss[5]))
                        .title("Dynamic")
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_dynamic_wallpaper_selected))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_setting_normalcopy),
                        Color.parseColor(colorss[5]))
                        .title("Settings")
                        .selectedIcon(getResources().getDrawable(R.drawable.ic_setting_selected))
                        .build()
        );


        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }

    }


    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void loadStaticSuccess(List<Lineinfo> mLineinfos, int type, int id) {
        DateList dateList = new DateList();
        dateList.setType(type);
        dateList.setId(id);
        dateList.setDataType(0);
        dateList.setLineinfos(mLineinfos);
        EventBus.getDefault().post(dateList);
        Log.d("aaa", "发一次0");
    }

    @Override
    public void loadDynamicSuccess(List<Lineinfo> mLineinfos, int type, int id) {
        DateList dateList = new DateList();
        dateList.setType(type);
        dateList.setId(id);
        dateList.setDataType(0);
        dateList.setLineinfos(mLineinfos);
        EventBus.getDefault().post(dateList);
        Log.d("aaa", "发一次1");
    }

    @Override
    public void getLoadStaticSuccess(List<Lineinfo> mLineinfos, int type, int id) {
        DateList dateList = new DateList();
        dateList.setType(type);
        dateList.setId(id);
        dateList.setDataType(1);
        dateList.setLineinfos(mLineinfos);
        EventBus.getDefault().post(dateList);
        Log.d("aaa", "发一次3");
    }

    @Override
    public void getLoadDynamicSuccess(List<Lineinfo> mLineinfos, int type, int id) {
        DateList dateList = new DateList();
        dateList.setType(type);
        dateList.setId(id);
        dateList.setDataType(1);
        dateList.setLineinfos(mLineinfos);
        EventBus.getDefault().post(dateList);
        Log.d("aaa", "发一次4");
    }

    @Override
    public void getNotificationSuccess(NotificationBean notificationBean) {
        Log.d("aaa", "getNotificationSuccess");
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                NotificationUtils.sendNotification(MainActivity.this, notificationBean, ((BitmapDrawable) resource).getBitmap());
                SPUtil.setCurrentTime(UtilsApp.getCurrentDate());


            }
        };
     Glide.with(this).load(notificationBean.getPush_info().get(0).getUrl_tumb()).into(simpleTarget);


    }

    long backTime;

    @Override
    public void onBackPressedSupport() {
        long secTime = System.currentTimeMillis();
        if (secTime - backTime > 2000) {
            backTime = secTime;
            Toast.makeText(this, "Click back again, and you will exit app", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressedSupport();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiverTag) {   //判断广播是否注册
            mReceiverTag = false;   //Tag值 赋值为false 表示该广播已被注销
            this.unregisterReceiver(mTimeChangeReceiver);   //注销广播
        }
    }

    public void InitDateTimes() {
        int Years = 0;
        int Months = 0;
        int Days = 0;
        int hours = 0;
        int min = 0;
        int SaveYears = 0;
        int SaveMonths = 0;
        int Savedays = 0;

        /**
         *
         */
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date CurrentDate = null;
        try {
            CurrentDate = formatter.parse(UtilsApp.getTimeToString(System.currentTimeMillis()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Years = CurrentDate.getYear(); //获取当前年份(2位)
        Months = CurrentDate.getMonth() + 1; //获取当前月份(0-11,0代表1月)
        // 所以获取当前月份是myDate.getMonth()+1;
        Days = CurrentDate.getDate(); //获取当前日(1-31)
        hours = CurrentDate.getHours(); //获取当前小时数(0-23)
        min = CurrentDate.getMinutes(); //获取当前分钟数(0-59)
        Log.i("@@@", "years==" + Years + "Months==" + Months + "days==" + Days + "hours==" + hours + "min" + min);
        if (SPUtil.getCurrentTime() != null) {
            // 2019-03-15 15:56
            DateBean dateBean = UtilsApp.getCurrentTime(SPUtil.getCurrentTime());
            if (dateBean != null) {
                SaveYears = dateBean.getYears();
                SaveMonths = dateBean.getMonth();
                Savedays = dateBean.getDay();
            }

            if (Years == SaveYears && Months == SaveMonths && Days == Savedays) {
                if (mReceiverTag) {   //判断广播是否注册
                    mReceiverTag = false;   //Tag值 赋值为false 表示该广播已被注销
                    this.unregisterReceiver(mTimeChangeReceiver);   //注销广播
                }
            } else {
                if ((hours > 7 && hours < 23) && min ==0) {

                    EventBus.getDefault().post(new MessageEvent(100000));
                }
                if (mReceiverTag) {   //判断广播是否注册
                    mReceiverTag = false;   //Tag值 赋值为false 表示该广播已被注销
                    this.unregisterReceiver(mTimeChangeReceiver);   //注销广播
                }
            }

        }
//        else {
//            if ((hours > 7 && hours < 23)) {
//                EventBus.getDefault().post(new MessageEvent(100000));
//                Log.i("@@@", "Sen===" + Years + "Months==" + Months + "days==" + Days + "hours==" + hours + "min" + min);
//            }

        //    }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (messageEvent.getMsgCode() == 100000) {
            Log.i("@@@", "@@@10000");
            mPresenter.getNotificationContent(UtilsApp.getDateToString(System.currentTimeMillis()));
        }

    }

    public class TimerChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                Log.i("###", "hour" + hour + "min" + min);

                if (hour > 8 && hour < 23 && min ==0) {
                    InitDateTimes();
                }
            }
        }
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

}
