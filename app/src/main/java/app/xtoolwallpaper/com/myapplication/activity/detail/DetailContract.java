package app.xtoolwallpaper.com.myapplication.activity.detail;

import app.xtoolwallpaper.com.myapplication.base.BasePresenter;
import app.xtoolwallpaper.com.myapplication.base.BaseView;
import app.xtoolwallpaper.com.myapplication.base.bean.ReportBean;
import app.xtoolwallpaper.com.myapplication.fragment.ui.MainContract;

public class DetailContract {
    public interface View extends BaseView{

    }
    abstract static class Presenter extends BasePresenter<DetailContract.View>{
        public abstract void upReport(ReportBean reportBean);
    }
}
