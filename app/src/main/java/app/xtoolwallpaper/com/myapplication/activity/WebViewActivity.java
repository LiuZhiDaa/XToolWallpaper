package app.xtoolwallpaper.com.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import app.xtoolwallpaper.com.myapplication.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by WangYu on 2018/5/3.
 */
public class WebViewActivity extends AppCompatActivity {
    private static final String VALUE_STRING_TARGET_URL = "target_url";
    private static final String VALUE_STRING_WEBVIEW_TITLE = "webview_title";
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView textTitle;
    String mTargetUrl;
    String mWebViewTitle;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        mTargetUrl = getIntent().getStringExtra(VALUE_STRING_TARGET_URL);
        mWebViewTitle = getIntent().getStringExtra(VALUE_STRING_WEBVIEW_TITLE);
        dealUrl();
    }

    private ArrayList<String> titleList = new ArrayList<>();

    public static void start(Context context, String targetUrl, String webViewTitle) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(VALUE_STRING_TARGET_URL, targetUrl);
        intent.putExtra(VALUE_STRING_WEBVIEW_TITLE, webViewTitle);
        context.startActivity(intent);
    }

    /**
     * 处理url
     */
    private void dealUrl() {
        if (mTargetUrl == null) {
            mTargetUrl = "";
        }
        if (mTargetUrl.endsWith(".pdf")) {
            try {
                Uri uri = Uri.parse(mTargetUrl);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (!TextUtils.isEmpty(mTargetUrl) && mTargetUrl.contains("http")) {
                if (mTargetUrl.contains("https")) {
                    mTargetUrl = "http" + mTargetUrl.substring(5);
                }
            } else {
                mTargetUrl = "http://" + mTargetUrl;
            }
            initSetting();
            mWebView.loadUrl(mTargetUrl);
            textTitle.setText(mWebViewTitle);
//            setLayoutState(VALUE_INT_LOADING_STATE);
        }
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void initSetting() {
        // 负责网页设置。处理缩放，字体，JS支持，缓存，数据库，网站访问文件权限等。
        WebSettings webSettings = mWebView.getSettings();
        // 触摸焦点起作用
        mWebView.requestFocus();
        // 支持网页本身的js
        webSettings.setJavaScriptEnabled(true);
        // 取消滚动条
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webSettings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);// 支持多指缩放
        webSettings.setDisplayZoomControls(false);// 隐藏缩放控制条
        webSettings.setDefaultTextEncodingName("utf-8");

        webSettings.setAllowFileAccess(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String mTargetUrl) {
                if (mTargetUrl.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mTargetUrl));
                    startActivity(intent);
                } else if (mTargetUrl.startsWith("http:") || mTargetUrl.startsWith("https:")) {
                    view.loadUrl(mTargetUrl);
                }
                return true;

            }

            @Override
            public void onPageFinished(WebView view, String mTargetUrl) {
                super.onPageFinished(view, mTargetUrl);
//                setLayoutState(VALUE_INT_CONTENT_STATE);
            }

            @Override
            public void onPageStarted(WebView view, String mTargetUrl, Bitmap favicon) {
//                setLayoutState(VALUE_INT_LOADING_STATE);
                super.onPageStarted(view, mTargetUrl, favicon);
            }

        });
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title.contains("404")) {
                    // 进入404页面
//                    setLayoutState(VALUE_INT_ERROR_STATE);
                } else {
//                    setLayoutState(VALUE_INT_CONTENT_STATE);
                    if (title.length() > 13) {
                        titleList.add(title.substring(0, 13) + "...");
//                        setToolbarTittle(title.substring(0, 13) + "...");
                    } else {
                        titleList.add(title);
//                        setToolbarTittle(title);
                    }
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }


        });

//        mWebView.addJavascriptInterface(getHtmlObject(), "ddsb");
        mWebView.clearCache(true);
    }


    @Override
    // 设置回退
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            if (mTargetUrl.equals(mWebView.getUrl())) {
                finish();
            } else {
                mWebView.goBack(); // goBack()表示返回WebView的上一页面
                if (titleList != null && titleList.size() > 1) {
                    titleList.remove(titleList.size() - 1);
//                    setToolbarTittle(titleList.get(titleList.size() - 1));
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //    @Override
//    protected void onReloadClick() {
//        if (mWebView != null) {
//            mWebView.reload();
//        }
//    }
    @OnClick({R.id.iv_back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.destroy();
        }
    }


}
