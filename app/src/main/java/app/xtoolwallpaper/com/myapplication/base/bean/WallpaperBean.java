package app.xtoolwallpaper.com.myapplication.base.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class WallpaperBean implements BaseBean{
    @JSONField(name = "category_info")
    private List<TitleBean> mTitleBeanList;

    public List<TitleBean> getTitleBeanList() {
        return mTitleBeanList;
    }

    public void setTitleBeanList(List<TitleBean> titleBeanList) {
        mTitleBeanList = titleBeanList;
    }
}
