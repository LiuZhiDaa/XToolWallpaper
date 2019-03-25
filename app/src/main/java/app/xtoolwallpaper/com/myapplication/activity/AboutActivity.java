package app.xtoolwallpaper.com.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import app.xtoolwallpaper.com.myapplication.R;
import app.xtoolwallpaper.com.myapplication.utils.UtilsApp;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {

    public static void launch(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_about);
        ButterKnife.bind(this);
        TextView tvAppVersion = findViewById(R.id.tv_app_version);
        tvAppVersion.setText(String.format("Release %s", UtilsApp.getMyAppVersionName(this)));
    }

    @OnClick(R.id.iv_back)
    public void Back() {
        finish();
    }
}