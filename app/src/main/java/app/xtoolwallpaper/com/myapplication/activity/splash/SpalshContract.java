package app.xtoolwallpaper.com.myapplication.activity.splash;

import app.xtoolwallpaper.com.myapplication.base.BasePresenter;
import app.xtoolwallpaper.com.myapplication.base.BaseView;

public class SpalshContract {
    public interface View extends BaseView {
        void loadStatic();

        void loadDynamic();

    }

    abstract static class Presenter extends BasePresenter<View> {
        public abstract void initTitle();

        public abstract void loadStatic();

        public abstract void loadDynamic();

    }
}
