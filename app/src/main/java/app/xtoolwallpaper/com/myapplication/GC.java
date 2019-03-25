package app.xtoolwallpaper.com.myapplication;

import android.os.Build;
import android.provider.Settings;

import com.blankj.utilcode.util.SDCardUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import app.xtoolwallpaper.com.myapplication.api.ApiFactory;
import app.xtoolwallpaper.com.myapplication.base.bean.TitleBean;

/**
 * 常量
 */
public class GC {
    public static List<TitleBean> sTitleBeans_static=new ArrayList<>();
    public static List<TitleBean> sTitleBeans_dynamiic=new ArrayList<>();
    public static String systemVersion=android.os.Build.VERSION.SDK_INT+"";
    public static String brand= Build.BRAND;
    public static String deviceid= Settings.Secure.getString(XApplication.APP.getContentResolver(), Settings.Secure.ANDROID_ID);
    public static int wideth;
    public static int height;

}
