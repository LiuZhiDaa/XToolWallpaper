package app.xtoolwallpaper.com.myapplication.base;

import android.content.Context;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.View;


import com.blankj.utilcode.util.KeyboardUtils;


import app.xtoolwallpaper.com.myapplication.R;
import app.xtoolwallpaper.com.myapplication.utils.GenericsUtil;
import app.xtoolwallpaper.com.myapplication.utils.InstanceUtil;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * BaseActivity,供新创建的Activity继承
 *
 * @param <P> Presenter,新创建的继承BasePresenter的类
 * @author Liuzhida
 */

public abstract class BaseActivity<P extends BasePresenter> extends SupportActivity {

    public Context mContext;
    public P mPresenter;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.setTheme(getCustomTheme());
        super.onCreate(null);
        mContext = this;
        View rootView = getLayoutInflater().inflate(this.getLayoutId(), null, false);
        super.setContentView(rootView);
        ButterKnife.bind(this);
        initPresenter();
        initView();
    }

    protected int getCustomTheme() {
        return R.style.AppTheme;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDetached();
    }

    protected void initPresenter() {
        mPresenter = InstanceUtil.getInstance(GenericsUtil.getSuperClassGenericsType(this.getClass(), 0));
        mPresenter.setView(this).setIntent(getIntent()).build();
    }

    public abstract void initView();

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return mPresenter.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
    }
    int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    private void hideNavigation() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

}
