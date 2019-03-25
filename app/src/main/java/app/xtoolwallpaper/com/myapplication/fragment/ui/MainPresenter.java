package app.xtoolwallpaper.com.myapplication.fragment.ui;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.xtoolwallpaper.com.myapplication.GC;
import app.xtoolwallpaper.com.myapplication.api.ApiFactory;
import app.xtoolwallpaper.com.myapplication.base.bean.Lineinfo;
import app.xtoolwallpaper.com.myapplication.base.bean.NotificationBean;
import app.xtoolwallpaper.com.myapplication.base.bean.Pager;
import app.xtoolwallpaper.com.myapplication.base.bean.PushInfoBean;
import app.xtoolwallpaper.com.myapplication.base.bean.ReportBean;
import app.xtoolwallpaper.com.myapplication.base.bean.TitleBean;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainPresenter extends MainContract.Presenter {
    public CompositeDisposable mCompositeDisposables = new CompositeDisposable();
    private static List<Lineinfo> mLineinfos;
    ;
    int Staticcurrent = 1;
    int Dynamiccurrent = 1;

    @Override
    public void onAttached() {
        mLineinfos = new ArrayList<>();

        mCompositeDisposables = mCompositeDisposable;
    }

    @Override
    public void upReport(ReportBean reportBean) {
        Observable<String> o = ApiFactory.APP.downloadcount(JSON.toJSONString(reportBean));
        mCompositeDisposable.add(o.subscribe(s -> {
            Log.d("aaa", "第一次上报的和日常接口1" + s);
        }, throwable -> {
            Log.d("aaa", "第一次上报的和日常接口2" + throwable.getMessage());
        }));

    }

    @Override
    public void loadStatic(int type, int id) {
        Staticcurrent = 1;
        RequestBody formBody = new FormBody.Builder()
                .add("page_type", "0")
                .add("category", String.valueOf(id))
                .add("line", "[0,6]")
                .build();
        Observable<String> o = ApiFactory.APP.content(formBody);
        mCompositeDisposable.add(o.subscribe(s -> {
            mLineinfos = JSON.parseArray(JSON.parseObject(s).getString("all_line_info"), Lineinfo.class);
            mView.loadStaticSuccess(mLineinfos, type, id);
        }, throwable -> {
            ToastUtils.showShort(throwable.getMessage());
        }));
    }

    @Override
    public void loadDynamic(int type, int id) {
        Dynamiccurrent = 1;
        RequestBody formBody = new FormBody.Builder()
                .add("page_type", "1")
                .add("category", String.valueOf(id))
                .add("line", "[0,6]")
                .build();
        Observable<String> o = ApiFactory.APP.content(formBody);
        mCompositeDisposable.add(o.subscribe(s -> {
            mLineinfos = JSON.parseArray(JSON.parseObject(s).getString("all_line_info"), Lineinfo.class);
            mView.loadDynamicSuccess(mLineinfos, type, id);
        }, throwable -> {
            ToastUtils.showShort(throwable.getMessage());
        }));
    }

    @Override
    public void getLoadStatic(int type, int id) {
        Staticcurrent = Staticcurrent + 1;
        int end = 6 * Staticcurrent;
        int start = end - 6;
        String line = "[" + start + "," + end + "]";
        RequestBody formBody = new FormBody.Builder()
                .add("page_type", "0")
                .add("category", String.valueOf(id))
                .add("line", line)
                .build();
        Log.i("@@@", line + "lines");
        Observable<String> o = ApiFactory.APP.content(formBody);
        mCompositeDisposable.add(o.subscribe(s -> {
            mLineinfos = JSON.parseArray(JSON.parseObject(s).getString("all_line_info"), Lineinfo.class);
            mView.getLoadStaticSuccess(mLineinfos, type, id);
        }, throwable -> {
            ToastUtils.showShort(throwable.getMessage());
        }));


    }

    @Override
    public void getLoadDynamic(int type, int id) {
        Dynamiccurrent = Dynamiccurrent + 1;
        int end = 6 * Dynamiccurrent;
        int start = end - 6;
        String line = "[" + start + "," + end + "]";
        RequestBody formBody = new FormBody.Builder()
                .add("page_type", "1")
                .add("category", String.valueOf(id))
                .add("line", line)
                .build();
        Log.i("@@@", line + "dynmiclines" + id + "id");
        Observable<String> o = ApiFactory.APP.content(formBody);
        mCompositeDisposable.add(o.subscribe(s -> {
            mLineinfos = JSON.parseArray(JSON.parseObject(s).getString("all_line_info"), Lineinfo.class);
            mView.getLoadDynamicSuccess(mLineinfos, type, id);
        }, throwable -> {
            ToastUtils.showShort(throwable.getMessage());
        }));
    }

    @Override
    public void getNotificationContent(String dateTime) {
        RequestBody formBody = new FormBody.Builder()
                .add("current_date", dateTime).build();
        Log.i("@@@", "@@@10022");
        Observable<String> o = ApiFactory.APP.getNotification(formBody);
        mCompositeDisposable.add(o.subscribe(s -> {
            NotificationBean notificationBean = JSON.parseObject(s, NotificationBean.class);
            mView.getNotificationSuccess(notificationBean);
        }, throwable -> {
            ToastUtils.showShort(throwable.getMessage());
        }));
    }


}
