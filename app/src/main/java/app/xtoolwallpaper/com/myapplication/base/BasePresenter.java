package app.xtoolwallpaper.com.myapplication.base;


import android.content.Intent;
import android.view.KeyEvent;

import io.reactivex.disposables.CompositeDisposable;

/**
 * BasePresenter,写在Contract中,在继承他的类中写数据交互的方法
 * 网络操作需要加入mCompositeDisposable中
 *
 * @param <V> 界面执行操作的接口
 * @author liuzhida
 */
public abstract class BasePresenter<V> {
  protected V mView;
  protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

  private Intent mIntent;

  BasePresenter setView(V v) {
    this.mView = v;
    return this;
  }

  void build() {
    this.onAttached();
  }


  public abstract void onAttached();

  void onDetached() {
    mCompositeDisposable.dispose();
  }


  BasePresenter setIntent(Intent intent) {
    this.mIntent = intent;
    return this;
  }

  protected Intent getIntent() {
    return mIntent;
  }

  public boolean dispatchKeyEvent(KeyEvent event) {
    return false;
  }
}
