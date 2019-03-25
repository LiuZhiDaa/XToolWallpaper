package app.xtoolwallpaper.com.myapplication.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;


import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import app.xtoolwallpaper.com.myapplication.GC;
import app.xtoolwallpaper.com.myapplication.R;
import app.xtoolwallpaper.com.myapplication.XApplication;
import app.xtoolwallpaper.com.myapplication.activity.detail.DetailPresenter;
import app.xtoolwallpaper.com.myapplication.base.BaseActivity;
import app.xtoolwallpaper.com.myapplication.base.bean.MessageCornerEvent;
import app.xtoolwallpaper.com.myapplication.base.bean.MessageEvent;

import app.xtoolwallpaper.com.myapplication.base.bean.ReportBean;
import app.xtoolwallpaper.com.myapplication.service.VideoLiveWallpaper;
import app.xtoolwallpaper.com.myapplication.service.VideoLiveWallpaper2;
import app.xtoolwallpaper.com.myapplication.utils.AlertDialog;
import app.xtoolwallpaper.com.myapplication.utils.DownLoadingDialog;
import app.xtoolwallpaper.com.myapplication.utils.FileUtil;
import app.xtoolwallpaper.com.myapplication.utils.SPUtil;
import app.xtoolwallpaper.com.myapplication.utils.SharedPreferencesUtil;
import app.xtoolwallpaper.com.myapplication.utils.UtilsApp;
import butterknife.BindView;

import static com.blankj.utilcode.util.SnackbarUtils.dismiss;

public class DetailActivity extends BaseActivity<DetailPresenter> {
    @BindView(R.id.iv_static_wallpaper_setting)
    ImageView mTvStaticWallpaper;
    @BindView(R.id.dynamic_video)
    VideoView dynamicVideo;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.b)
    TextView b;
    @BindView(R.id.btn_select)
    Button btnSelect;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.lin_bottom)
    LinearLayout linBottom;
    @BindView(R.id.rl_click)
    RelativeLayout rlClick;

    private File mFile1;
    WallpaperManager wpManager;
    static String Url = "";
    private int mCornerStaticCode = 0;//静态code 0
    private int mCornerDynamicCode = 1;//动态 code 1
    public static final String SERCIVE_1 = "app.xtoolwallpaper.com.myapplication.service.VideoLiveWallpaper";
    public static final String SERCIVE_2 = "app.xtoolwallpaper.com.myapplication.service.VideoLiveWallpaper2";
    private SharedPreferencesUtil sharedPreferencesUtil;
    private String currentService = SERCIVE_1;
    Intent intents;
    public static final String VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_FLAG = "form_static_fragment";
    public static final String VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL = "static_pic_url";
    public static final String VALUE_STR_EXTRA_IS_DYNAMIC_WALLPAPER_PIC_URL_ID = "dynamic_pic_url";
    public static final String VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL_ID = "pic_url_id";
    public static final String VALUE_STR_EXTRA_IS_DYNAMIC_WALLPAPER_PIC_URL_TUMB_ID = "dynamic_pic_url_tumb";
    private boolean isFull = false;
    static int MSG_WHAT = 2;
    static int MSG_WHAT_DYNAMIC = 3;
    Dialog dialog;
    Dialog loadingActivityDialog;
    int id;
    boolean isCanBack;
    int screenWidth;
    int screenHeight;
    private static final int UPDATE_UI = 1;
    private int oldProgress = 0;
    private boolean isCallBack = false;
    MyDownLoadAsyncTask myDownLoadAsyncTask;
    int isStatic=0;
    @SuppressLint("HandlerLeak")
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_WHAT) {
                setDesktopWallpaper(Url);
                ReportBean reportBean = new ReportBean();
                reportBean.setBrand(GC.brand);
                reportBean.setCreatTime(UtilsApp.getDateToString(System.currentTimeMillis()));
                reportBean.setDeviceid(GC.deviceid);
                reportBean.setDownLoad(id);
                reportBean.setSystemVersion(GC.systemVersion);
                reportBean.setHeight(GC.height);
                reportBean.setWideth(GC.wideth);
                reportBean.setBehaviorId(0);
                mPresenter.upReport(reportBean);
                EventBus.getDefault().post(new MessageEvent(0x16));
            } else if (msg.what == MSG_WHAT_DYNAMIC) {
                ReportBean reportBean = new ReportBean();
                reportBean.setBrand(GC.brand);
                reportBean.setCreatTime(UtilsApp.getDateToString(System.currentTimeMillis()));
                reportBean.setDeviceid(GC.deviceid);
                reportBean.setDownLoad(id);
                reportBean.setSystemVersion(GC.systemVersion);
                reportBean.setHeight(GC.height);
                reportBean.setWideth(GC.wideth);
                reportBean.setBehaviorId(1);
                mPresenter.upReport(reportBean);
                EventBus.getDefault().post(new MessageEvent(0x16));
                setDynamicWallpaper();
            } else if (msg.what == UPDATE_UI) {
                if (null != dynamicVideo && dynamicVideo.isPlaying()) {
                    int currentProgress = dynamicVideo.getCurrentPosition();
                    if (oldProgress == currentProgress) {
//                        showComeInDialog();
                        mTvStaticWallpaper.setVisibility(View.VISIBLE);
                    } else {
                        closeComeDialog();
                        mTvStaticWallpaper.setVisibility(View.GONE);
                    }
                    oldProgress = currentProgress;
                }
                mhandler.sendEmptyMessageDelayed(UPDATE_UI, 500);
            }

        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        if (window != null) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        sharedPreferencesUtil = SharedPreferencesUtil.getInstance();
        wpManager = WallpaperManager.getInstance(this);
        intents = getIntent();
        screenHeight = UtilsApp.getScreenHeight(XApplication.APP);
        //   screenWidth = UtilsApp.getScreenWidth(XApplication.APP);
        screenWidth = screenHeight / 16 * 9;
        showComeInDialog();
        if (intents != null && intents.getBooleanExtra(VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_FLAG, false)) {
            mTvStaticWallpaper.setVisibility(View.VISIBLE);
            dynamicVideo.setVisibility(View.GONE);
            showComeInDialog();
            initStaticWallpaper();
        } else {
            isStatic = 1;
            mTvStaticWallpaper.setVisibility(View.VISIBLE);
            dynamicVideo.setVisibility(View.VISIBLE);
            initDynamic();
        }
        if (SPUtil.getId() == id) {
            btnSelect.setText(getString(R.string.detailActivity_Using));
            btnSelect.setClickable(false);
        }
        Animation translateAnimation = new TranslateAnimation(0, 0, 0, 300);//平移动画  从0,0,平移到100,100
        translateAnimation.setDuration(500);//动画持续的时间为1.5s
        translateAnimation.setFillEnabled(true);//使其可以填充效果从而不回到原地
        translateAnimation.setFillAfter(true);//不回到起始位置
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                rlClick.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isFull = true;
                rlClick.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Animation translateAnimation1 = new TranslateAnimation(0, 0, 300, 0);//平移动画  从0,0,平移到100,100
        translateAnimation1.setDuration(500);//动画持续的时间为1.5s
        translateAnimation1.setFillEnabled(true);//使其可以填充效果从而不回到原地
        translateAnimation1.setFillAfter(true);//不回到起始位置
        translateAnimation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                rlClick.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isFull = false;
                rlClick.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rlClick.setOnClickListener(v -> {
            if (isFull) {
                linBottom.startAnimation(translateAnimation1);
            } else {
                linBottom.startAnimation(translateAnimation);
            }

        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showWallpaperDialog() {
        AlertDialog mAlertDialog = new AlertDialog(DetailActivity.this, getString(R.string.Detail_SuccessWallpaper_Tips_Text),
                getString(R.string.Detail_SuccessWallpaper_Text),
                getString(R.string.Detail_Dialog_Confirm_Btn_Text), "", new AlertDialog.OnDialogButtonClickListener() {
            @Override
            public void onDialogButtonClick(boolean isPositive) {
                if (isPositive) {
                    dismiss();
                }
            }
        });
        mAlertDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void initStaticWallpaper() {
        Url = intents.getStringExtra(VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL);
        Log.i("@@@", "DetailUrl" + Url);
        id = intents.getIntExtra(VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL_ID, 0);
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                closeComeDialog();
                mTvStaticWallpaper.setImageDrawable(resource);
            }
        };
        RequestOptions options = new RequestOptions().override(screenWidth, screenHeight);
        Glide.with(getApplicationContext()).load(Url).apply(options).into(simpleTarget);
        //Glide.with(getApplicationContext()).load(url).centerCrop().into(mTvStaticWallpaper);
        //装载完成监听
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (minute == 0) {
            a.setText(hour + ":00");
        } else if (minute < 10) {
            a.setText(hour + ":" + "0" + minute);
        } else {
            a.setText(hour + ":" + minute);
        }

        btnSelect.setOnClickListener(v -> {
            //设置桌面壁纸
            //壁纸管理器
            Log.i("@@@", isCallBack + "showDialogisCallBack");
            showDialog();
//            downloadPhotoFile(Url, 0);
            myDownLoadAsyncTask = new MyDownLoadAsyncTask(mContext, Url, 0);
            myDownLoadAsyncTask.execute(500);

        });
    }


    private void setDesktopWallpaper(String url) {

        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                try {
                    SPUtil.setId(id);
                    Log.d("@@@", "id=" + id);
                    EventBus.getDefault().post(new MessageCornerEvent(mCornerStaticCode));// 0 代表是更新角标的code
                    wpManager.setBitmap(((BitmapDrawable) resource).getBitmap());
                    closeDialog();
                    showWallpaperDialog();
                    btnSelect.setText("Using");
                    btnSelect.setClickable(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Glide.with(this).load(url).into(simpleTarget);
    }

    private void initDynamic() {
        Intent intents = getIntent();
        Url = intents.getStringExtra(DetailActivity.VALUE_STR_EXTRA_IS_DYNAMIC_WALLPAPER_PIC_URL_ID);
        Log.i("@@@", Url + "initDynamic");
        String tumb = intents.getStringExtra(DetailActivity.VALUE_STR_EXTRA_IS_DYNAMIC_WALLPAPER_PIC_URL_TUMB_ID);
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                mTvStaticWallpaper.setImageDrawable(resource);
            }
        };
        Glide.with(getApplicationContext()).load(tumb).into(simpleTarget);
        id = intents.getIntExtra(VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL_ID, 0);
        mhandler.sendEmptyMessageDelayed(UPDATE_UI, 250);
        //装载完成监听
        dynamicVideo.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.start();
        });
        dynamicVideo.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                ToastUtils.showShort("If the load fails, please download it directly");
                closeComeDialog();
                return true;
            }
        });
        dynamicVideo.setVideoPath(Url);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (minute == 0) {
            a.setText(hour + ":00");
        } else if (minute < 10) {
            a.setText(hour + ":" + "0" + minute);
        } else {
            a.setText(hour + ":" + minute);
        }

        btnSelect.setOnClickListener(v -> {
            showDialog();
            myDownLoadAsyncTask = new MyDownLoadAsyncTask(mContext, Url, 1);
            myDownLoadAsyncTask.execute(500);
            //   downloadPhotoFile(Url, 1);
        });

    }

    private void setDynamicWallpaper() {
        // 判断当前运行的是哪个Service，然后跳转到不是当前运行的Service，并记录下跳转到的Service名称
        // 如果只用一个Service，更改了视频源后，跳转到同一个Service，预览界面的视频改变了，但是点击设置可是设置无效
        // 后来发现用其他的更改后可以生效，才想出两个Service方法
        // 用到此方法后如何解决 用户是点击了设置还是点击了返回键（因为onActivityResult（）里面接收到的都是同一个参数）
        // 点击了设置退出到桌面，点击返回则继续留在此界面
        // 解决方法：请参照下面 isLiveWallpaperChanged（）
        if (getCurrentService().equals(SERCIVE_2)) {
            VideoLiveWallpaper.setToWallPaper(this);
            currentService = SERCIVE_1;
        } else {
            VideoLiveWallpaper2.setToWallPaper(this);
            currentService = SERCIVE_2;
        }

    }

    public String getCurrentService() {
        return sharedPreferencesUtil.getString("service", SERCIVE_1);
    }

    String TAG = "@@@";

    public static final int REQUEST_LIVE_PAPER = 10010;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        closeDialog();
        if (requestCode == 10010) {
            if (isLiveWallpaperChanged()) {
                // 记录到数据库,更新当前的Service
                sharedPreferencesUtil.putString("service", currentService);
                EventBus.getDefault().post(new MessageCornerEvent(mCornerDynamicCode));// 0 代表是更新角标的code
                btnSelect.setText(getString(R.string.detailActivity_Using));
                btnSelect.setClickable(false);
                SPUtil.setId(id);
                Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
                startActivity(intent);
                finish();
            }
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static boolean isLiveWallpaperRunning(Context context, String tagetPackageName) {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);// 得到壁纸管理器
        WallpaperInfo wallpaperInfo = wallpaperManager.getWallpaperInfo();// 如果系统使用的壁纸是动态壁纸话则返回该动态壁纸的信息,否则会返回null
        if (wallpaperInfo != null) { // 如果是动态壁纸,则得到该动态壁纸的包名,并与想知道的动态壁纸包名做比较
            String currentLiveWallpaperPackageName = wallpaperInfo.getPackageName();
            if (currentLiveWallpaperPackageName.equals(tagetPackageName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isStatic == 1)
            showComeInDialog();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (myDownLoadAsyncTask != null) {
            closeDialog();
            myDownLoadAsyncTask.remove(this);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    private void savePath(String path) {
        SharedPreferencesUtil sp = SharedPreferencesUtil.getInstance();
        sp.putString("path", path);
    }

    /**
     * 显示Dialog
     */

    private void showDialog() {
        if (dialog == null) {
            dialog = DownLoadingDialog.createLoadingDialog(this, "DownLoading");
            dialog.show();
            isCallBack = true;
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Log.i("@@@", "showDialogisCallBack" + isCallBack);
                    isCallBack = false;
                    myDownLoadAsyncTask.remove(mContext);
                    //  OkHttpUtils.getInstance().cancelTag(1);
                }
            });
        } else {
            dialog = DownLoadingDialog.createLoadingDialog(this, "DownLoading");
            dialog.show();
            isCallBack = true;
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    Log.i("@@@", "showDialogisCallBack1" + isCallBack);
                    isCallBack = false;
                    myDownLoadAsyncTask.remove(mContext);
                }
            });
        }
    }

    /**
     * 显示Dialog
     */

    private void showComeInDialog() {
        if (loadingActivityDialog == null) {
            loadingActivityDialog = DownLoadingDialog.createLoadingDialog(this, "Loading");
            loadingActivityDialog.show();
        }
        loadingActivityDialog.setCanceledOnTouchOutside(false);
        loadingActivityDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                DetailActivity.this.finish();
            }
        });

    }

    /**
     * 关闭Dialog
     */
    private void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 关闭Dialog
     */
    private void closeComeDialog() {
        if (loadingActivityDialog != null) {
            loadingActivityDialog.dismiss();
            loadingActivityDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean isLiveWallpaperChanged() {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);// 得到壁纸管理器
        WallpaperInfo wallpaperInfo = wallpaperManager.getWallpaperInfo();// 如果系统使用的壁纸是动态壁纸话则返回该动态壁纸的信息,否则会返回null
        if (wallpaperInfo != null) { // 如果是动态壁纸,则得到该动态壁纸的包名,并与想知道的动态壁纸包名做比较
            String currentLiveWallpaperPackageName = wallpaperInfo.getPackageName();
            String currentSerciceName = wallpaperInfo.getServiceName();
            if (currentLiveWallpaperPackageName.equals(getPackageName()) && currentService.equals(currentSerciceName)) {
                return true;
            }
        }
        return false;
    }

    public static void goHome(Activity activity) {
        Intent intent = new Intent();
        // 为Intent设置Action、Category属性
        intent.setAction(Intent.ACTION_MAIN);// "android.intent.action.MAIN"
        intent.addCategory(Intent.CATEGORY_HOME); //"android.intent.category.HOME"
        activity.startActivity(intent);
    }

    class MyDownLoadAsyncTask extends AsyncTask<Integer, Integer, String> {
        private Context mContext;
        private long currentDownloadID;
        private boolean idDownloading = true;
        private String urlPath;
        private int tab;

        public MyDownLoadAsyncTask(Context mContext, String urlPath, int tab) {
            this.mContext = mContext;
            this.urlPath = urlPath;
            this.tab = tab;
        }

        @Override
        protected String doInBackground(Integer... integers) {
            downloadByDownloadManager(mContext, urlPath, tab);
            return String.valueOf(integers[0].intValue());
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int value = values[0];
            if (value <= 100) {
                if (value == 100) {
                }
            }
        }

        //"https://cdn.llscdn.com/yy/files/xs8qmxn8-lls-LLS-5.8-800-20171207-111607.apk"
        // 4下载器下载
        public void downloadByDownloadManager(Context context, String downloadUrlStr, int tab) {

            File mediaStorageDir = FileUtil.getSavedShotsFolder();
            String PicName = "";
            Log.i("@@@", tab + "下载器下载");
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINESE).format(new Date());
            if (tab == 0) {
                PicName = timeStamp + ".png";
            } else {
                PicName = timeStamp + ".mp4";
            }
            assert mediaStorageDir != null;
            File mediaFile = new File(mediaStorageDir.getPath());
            DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(downloadUrlStr));
            // 通过setAllowedNetworkTypes方法可以设置允许在何种网络下下载
            downloadRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
            downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            // 文件后缀
            //  String fileFormat = PicName;
            // 获取文件名
            String resourceName = PicName;
            // 本地保存地址
            String resourcePath = mediaFile.getAbsolutePath();
            //   String resourcePath = PublicMethodUtils.getResourcePath();
            // 下载标题
            File saveFile = new File(resourcePath, resourceName);
            downloadRequest.setDestinationUri(Uri.fromFile(saveFile));
            DownloadManager manager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            currentDownloadID = manager.enqueue(downloadRequest);
            Log.e("e", "DownloadManager start downloading ---------" + saveFile.getAbsolutePath());
            // 获取下载进度
            getDownloadProgress(manager, currentDownloadID, resourceName, saveFile.getAbsolutePath());

        }

        // 5下载进度并返回完成
        public void getDownloadProgress(final DownloadManager manager, final long downloadID, final String resourceName, String path) {
            while (idDownloading) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadID);
                Cursor cursor = manager.query(query);

                if (cursor.moveToFirst()) {
                    long bytesDownloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    long bytesTotal = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    final int downloadProgress = (int) (bytesDownloaded * 100 / bytesTotal);

                    publishProgress(downloadProgress);

                    Log.e("e", resourceName + ":下载进度: " + downloadProgress + "%");
                    if (downloadProgress == 100) {
                        if (tab == 0) {
                            mhandler.sendEmptyMessage(MSG_WHAT);

                        } else {
                            mhandler.sendEmptyMessage(MSG_WHAT_DYNAMIC);
                            savePath(path);
                        }
                        currentDownloadID = -1;
                        break;
                    } else {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            StringWriter stringWriter = new StringWriter();
                            e.printStackTrace(new PrintWriter(stringWriter, true));
                        }
                    }
                    cursor.close();
                }
            }

        }

        public void remove(Context context) {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            if (currentDownloadID >= 0) {
                Log.i("@@@", " downloadManager.remove(currentDownloadID);");
                downloadManager.remove(currentDownloadID);
            }
            idDownloading = false;
        }
    }

}
