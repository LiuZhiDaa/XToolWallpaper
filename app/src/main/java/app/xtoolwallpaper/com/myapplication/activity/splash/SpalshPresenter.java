package app.xtoolwallpaper.com.myapplication.activity.splash;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.ToastUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.xtoolwallpaper.com.myapplication.GC;
import app.xtoolwallpaper.com.myapplication.api.ApiFactory;

import app.xtoolwallpaper.com.myapplication.base.bean.TitleBean;
import app.xtoolwallpaper.com.myapplication.base.bean.WallpaperBean;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SpalshPresenter extends SpalshContract.Presenter {
    private List<TitleBean> mTitleBeanList_static;
    private List<TitleBean> mTitleBeanList_dynamic;
    public CompositeDisposable mCompositeDisposables = new CompositeDisposable();
    @Override
    public void onAttached() {
        mCompositeDisposables=mCompositeDisposable;
        mTitleBeanList_static=new ArrayList<>();
        mTitleBeanList_dynamic=new ArrayList<>();
    }


    @Override
    public void initTitle() {
        TitleBean titleBean2=new TitleBean();
        titleBean2.setId(101);
        titleBean2.setTitle("Hot");
        GC.sTitleBeans_dynamiic.add(titleBean2);
        TitleBean titleBean3=new TitleBean();
        titleBean3.setId(102);
        titleBean3.setTitle("New");
        GC.sTitleBeans_dynamiic.add(titleBean3);
        TitleBean titleBean6=new TitleBean();
        titleBean6.setId(101);
        titleBean6.setTitle("Hot");
        GC.sTitleBeans_static.add(titleBean6);
        TitleBean titleBean7=new TitleBean();
        titleBean7.setId(102);
        titleBean7.setTitle("New");
        GC.sTitleBeans_static.add(titleBean7);
    }

    @Override
    public void loadStatic() {
        RequestBody formBody = new FormBody.Builder()
                .add("page_type", "0")
                .build();
        Observable<String> o = ApiFactory.APP.classification(formBody);
        mCompositeDisposable.add(o.subscribe(s -> {
            GC.sTitleBeans_static.clear();
            WallpaperBean wallpaperBean = JSON.parseObject(s, WallpaperBean.class);
            GC.sTitleBeans_static  = wallpaperBean.getTitleBeanList();
       //     GC.sTitleBeans_static = mTitleBeanList_static;
        }, throwable -> {
            ToastUtils.showShort(throwable.getMessage()+"获取静态title");
        }));
    }

    @Override
    public void loadDynamic() {
        RequestBody formBody = new FormBody.Builder()
                .add("page_type", "1")
                .build();
        Observable<String> o=ApiFactory.APP.classification(formBody);
        mCompositeDisposable.add(o.subscribe(s -> {
            GC.sTitleBeans_dynamiic.clear();
            WallpaperBean wallpaperBean= JSON.parseObject(s, WallpaperBean.class);
            GC.sTitleBeans_dynamiic=wallpaperBean.getTitleBeanList();
        },throwable -> {
            ToastUtils.showShort(throwable.getMessage()+"获取动态title");
        }));
    }
}
