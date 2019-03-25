package app.xtoolwallpaper.com.myapplication.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UtilsApp {
    public static String getDateToString(long milSecond) {
        Date date = new Date(milSecond);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 获取当前的年月日
     *
     * @return
     */
    public static String getCurrentDate() {

        Date date = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static DateBean getCurrentTime(String DateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        DateBean dateBean;
        try {
            Date myDate = formatter.parse(DateTime);
            int Years = myDate.getYear(); //获取当前年份(2位)
            int Months = myDate.getMonth() + 1; //获取当前月份(0-11,0代表1月)
            // 所以获取当前月份是myDate.getMonth()+1;
            int Days = myDate.getDate(); //获取当前日(1-31)

            dateBean = new DateBean(Years, Months, Days, 0, 0);
            return dateBean;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTimeToString(long milSecond) {
        Date date = new Date(milSecond);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }


    public static Drawable getAppIcon(Context context, String strPackageName) {
        if (null == context || TextUtils.isEmpty(strPackageName))
            return null;

        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(strPackageName, 0);
            return ai.loadIcon(pm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getAppName(Context context, String strPackageName) {
        if (null == context || TextUtils.isEmpty(strPackageName))
            return null;

        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(strPackageName, 0);
            return ai.loadLabel(pm).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getAppVersionCode(Context context, String strPackageName) {
        if (null == context || TextUtils.isEmpty(strPackageName))
            return 0;

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(strPackageName, 0);
            return pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getAppVersionName(Context context, String strPackageName) {
        if (null == context || TextUtils.isEmpty(strPackageName))
            return null;

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(strPackageName, 0);
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static long getAppFirstInstallTime(Context context, String strPackageName) {
        if (null == context || TextUtils.isEmpty(strPackageName))
            return 0;

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(strPackageName, 0);
            return pi.firstInstallTime;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int getAppFlags(Context context, String strPackageName) {
        if (null == context || TextUtils.isEmpty(strPackageName))
            return 0;

        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(strPackageName, 0);
            return ai.flags;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static boolean isSystemApp(Context context, String strPackageName) {
        if (null == context || TextUtils.isEmpty(strPackageName))
            return false;

        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(strPackageName, 0);
            return (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean installApp(Context context, String strApkPath) {
        if (null == context || TextUtils.isEmpty(strApkPath))
            return false;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(strApkPath));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        if (!Activity.class.isAssignableFrom(context.getClass())) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
        return true;
    }

    public static boolean uninstallApp(Context context, String strPackageName) {
        if (null == context || TextUtils.isEmpty(strPackageName))
            return false;

        Uri uri = Uri.parse("package:" + strPackageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        if (!Activity.class.isAssignableFrom(context.getClass())) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
        return true;
    }

    public static boolean executeApp(Context context, String strPackageName, String strClassName) {
        if (null == context || TextUtils.isEmpty(strPackageName)
                || TextUtils.isEmpty(strClassName))
            return false;

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(strPackageName, strClassName));
        if (!Activity.class.isAssignableFrom(context.getClass())) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        context.startActivity(intent);
        return true;
    }

    public static boolean isAppInstalled(Context context, String strPackageName) {
        if (null == context || TextUtils.isEmpty(strPackageName))
            return false;

        try {
            PackageManager pm = context.getPackageManager();
            pm.getPackageInfo(strPackageName, 0);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static long getAppNetworkSize(Context context, String strPackageName, int nUID) {
        if (null == context || TextUtils.isEmpty(strPackageName))
            return 0;

        PackageManager pm = context.getPackageManager();
        if (pm.checkPermission("android.permission.INTERNET", strPackageName) != PackageManager.PERMISSION_GRANTED)
            return 0;

        long lNetworkSize = TrafficStats.getUidTxBytes(nUID) + TrafficStats.getUidRxBytes(nUID);
        if (lNetworkSize > 0)
            return lNetworkSize;

        return 0;
    }


    public static String getAppPackageNameByUID(Context context, int nUID) {
        if (null == context)
            return null;

        PackageManager pm = context.getPackageManager();
        String[] strarray = pm.getPackagesForUid(nUID);
        if (null == strarray)
            return null;

        String strPackageName = null;
        for (String str : strarray) {
            if (TextUtils.isEmpty(str))
                continue;

            String strAppName = getAppName(context, str);
            if (!TextUtils.isEmpty(strAppName))
                strPackageName = str;
        }

        return strPackageName;
    }

    public static String getMyAppName(Context context) {
        if (null == context)
            return null;

        return getAppName(context, getMyAppPackageName(context));
    }

    public static String getMyAppPackageName(Context context) {
        if (null == context)
            return null;

        return context.getPackageName();
    }

    public static int getMyAppVersionCode(Context context) {
        if (null == context)
            return 0;

        return getAppVersionCode(context, getMyAppPackageName(context));
    }

    public static String getMyAppVersionName(Context context) {
        if (null == context)
            return null;

        return getAppVersionName(context, getMyAppPackageName(context));
    }

//    public static String getMyAppSignatureMD5(Context context) {
//        if (null == context)
//            return null;
//
//        return getAppSignatureMD5(context, getMyAppPackageName(context));

//    }

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);

        int sWidth = metrics.widthPixels;
        return sWidth;
    }

    //转换px为dip
    public static int px2dp(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }


    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);

        int sHight = metrics.heightPixels;
        return sHight;
    }

    public static String getApkFileName(Context context, String strApkFilePath) {
        if (null == context || TextUtils.isEmpty(strApkFilePath))
            return null;

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageArchiveInfo(strApkFilePath, 0);
            ApplicationInfo ai = pi.applicationInfo;
            ai.sourceDir = strApkFilePath;
            ai.publicSourceDir = strApkFilePath;
            return pm.getApplicationLabel(ai).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Drawable getApkFileIcon(Context context, String strApkFilePath) {
        if (null == context || TextUtils.isEmpty(strApkFilePath))
            return null;

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageArchiveInfo(strApkFilePath, 0);
            ApplicationInfo ai = pi.applicationInfo;
            ai.sourceDir = strApkFilePath;
            ai.publicSourceDir = strApkFilePath;
            return pm.getApplicationIcon(ai);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getApkFilePackageName(Context context, String strApkFilePath) {
        if (null == context || TextUtils.isEmpty(strApkFilePath))
            return null;

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageArchiveInfo(strApkFilePath, 0);
            return pi.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getApkFileVersionName(Context context, String strApkFilePath) {
        if (null == context || TextUtils.isEmpty(strApkFilePath))
            return null;

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageArchiveInfo(strApkFilePath, 0);
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getApkFileVersionCode(Context context, String strApkFilePath) {
        if (null == context || TextUtils.isEmpty(strApkFilePath))
            return 0;

        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageArchiveInfo(strApkFilePath, 0);
            return pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static List<String> getInstalledAppPackageName(Context context) {
        if (null == context)
            return null;

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> listApplicationInfo = pm.getInstalledApplications(0);
        ArrayList<String> listResult = new ArrayList<String>();
        int nCount = listApplicationInfo.size();
        for (int nIndex = 0; nIndex < nCount; nIndex++) {
            String strPackageName = listApplicationInfo.get(nIndex).packageName;
            if (strPackageName.equals(UtilsApp.getMyAppPackageName(context)))
                continue;

            listResult.add(strPackageName);
        }

        return listResult;
    }
}
