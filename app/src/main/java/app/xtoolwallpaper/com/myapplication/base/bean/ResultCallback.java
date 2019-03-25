package app.xtoolwallpaper.com.myapplication.base.bean;

import java.io.IOException;

import okhttp3.Request;

public abstract class ResultCallback {
    public abstract void onError(Request request, Exception e);

    public abstract void onResponse(String str) throws IOException;
}
