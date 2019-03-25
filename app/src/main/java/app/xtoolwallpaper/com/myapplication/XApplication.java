package app.xtoolwallpaper.com.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import com.bumptech.glide.request.target.ViewTarget;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


public class XApplication extends Application {
    public static XApplication APP;
    private static String ShareName = "PAPER";

    @Override
    public void onCreate() {
        super.onCreate();

        APP = this;
        UMConfigure.init(this, "5c9204d42036575c57001a40", "ChannelWallpapers",UMConfigure.DEVICE_TYPE_PHONE, null);

        ViewTarget.setTagId(R.id.tag_glide);
    }

    public static XApplication getInstance() {
        return APP;
    }

    public static SharedPreferences getSharedPreferences() {
        // 这里保存的视频路径是在服务里获取，所以这里一定要用：Context.MODE_MULTI_PROCESS
        SharedPreferences sharedPreferences = getInstance().getSharedPreferences(ShareName, APP.MODE_MULTI_PROCESS);
        return sharedPreferences;
    }

}
