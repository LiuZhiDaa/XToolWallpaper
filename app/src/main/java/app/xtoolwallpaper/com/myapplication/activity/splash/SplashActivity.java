package app.xtoolwallpaper.com.myapplication.activity.splash;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import app.xtoolwallpaper.com.myapplication.GC;
import app.xtoolwallpaper.com.myapplication.MainActivity;
import app.xtoolwallpaper.com.myapplication.R;
import app.xtoolwallpaper.com.myapplication.base.BaseActivity;
import app.xtoolwallpaper.com.myapplication.base.bean.TitleBean;
import app.xtoolwallpaper.com.myapplication.utils.FileUtil;

public class SplashActivity extends BaseActivity<SpalshPresenter> {
    String mShotPhotosPath;
    ImageView mIvImag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
       mIvImag = findViewById(R.id.iv_image);
        File folder = FileUtil.getSavedShotsFolder();
        if (folder != null) {
            mShotPhotosPath = folder.getAbsolutePath();
            initSplashImg();
        } else {
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.launch(SplashActivity.this);
                finish();
                overridePendingTransition(0, 0);
            }
        }, 3000);
        mPresenter.initTitle();
        mPresenter.loadStatic();
        mPresenter.loadDynamic();

        DisplayMetrics dm = getResources().getDisplayMetrics();
        GC.wideth = dm.widthPixels;
        GC.height = dm.heightPixels;

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //do something.
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }


    private void initSplashImg() {
        File folder = new File(mShotPhotosPath);
        ArrayList<String> list = null;
        if (folder.exists()) {
            list = new ArrayList<>();
            File[] files = folder.listFiles();
            Arrays.sort(files, (o1, o2) -> Long.compare(o1.lastModified(), o2.lastModified()));
            for (File file : files) {
                if (file.isFile()) {
                    String name = file.getName().toLowerCase();
                    if (name.endsWith(".png")) {
                        list.add(file.getAbsolutePath());
                    }
                }
            }
        }
        if (list != null && list.size() > 0) {

            Glide.with(this).load(list.get(SplashRandom(list.size())))

                    .into(mIvImag);
        }

    }

    //在一定范围内生成随机数.
    //比如此处要求在[0 - n)内生成随机数.
    //注意:包含0不包含n
    private int SplashRandom(int size) {
        Random random = new Random();
        final int i = random.nextInt(size);
        return i;
    }
}
