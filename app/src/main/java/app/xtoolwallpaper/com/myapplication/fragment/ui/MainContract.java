package app.xtoolwallpaper.com.myapplication.fragment.ui;

import java.util.List;

import app.xtoolwallpaper.com.myapplication.base.BasePresenter;
import app.xtoolwallpaper.com.myapplication.base.BaseView;
import app.xtoolwallpaper.com.myapplication.base.bean.Lineinfo;
import app.xtoolwallpaper.com.myapplication.base.bean.NotificationBean;
import app.xtoolwallpaper.com.myapplication.base.bean.ReportBean;

public class MainContract {
    public interface View extends BaseView {

        void loadStaticSuccess(List<Lineinfo> mLineinfos, int type, int id);

        void loadDynamicSuccess(List<Lineinfo> mLineinfos, int type, int id);

        void getLoadStaticSuccess(List<Lineinfo> mLineinfos, int type, int id);

        void getLoadDynamicSuccess(List<Lineinfo> mLineinfos, int type, int id);

        void getNotificationSuccess(NotificationBean notificationBean);
    }

    abstract static class Presenter extends BasePresenter<View> {
        public abstract void loadStatic(int type, int id);

        public abstract void loadDynamic(int type, int id);

        public abstract void getLoadStatic(int type, int id);

        public abstract void getLoadDynamic(int type, int id);

        public abstract void getNotificationContent(String dateTime);

        public abstract void upReport(ReportBean reportBean);


    }
}
