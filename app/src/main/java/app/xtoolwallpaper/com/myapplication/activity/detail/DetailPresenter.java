package app.xtoolwallpaper.com.myapplication.activity.detail;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import app.xtoolwallpaper.com.myapplication.api.ApiFactory;
import app.xtoolwallpaper.com.myapplication.base.bean.ReportBean;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class DetailPresenter extends DetailContract.Presenter {
    public CompositeDisposable mCompositeDisposables = new CompositeDisposable();
    @Override
    public void onAttached() {
        mCompositeDisposables = mCompositeDisposable;
    }

    @Override
    public void upReport(ReportBean reportBean) {
        Observable<String> o = ApiFactory.APP.downloadcount(JSON.toJSONString(reportBean));
        mCompositeDisposable.add(o.subscribe(s -> {
            Log.d("aaa",s);
        },throwable -> {

        }));

    }
}
