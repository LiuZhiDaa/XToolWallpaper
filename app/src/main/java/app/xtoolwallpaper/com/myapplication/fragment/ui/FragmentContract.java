package app.xtoolwallpaper.com.myapplication.fragment.ui;

import app.xtoolwallpaper.com.myapplication.base.BaseView;

public class FragmentContract {
    public interface View extends BaseView {
        void loadStatic();
        void loadDynamic();
    }
}
