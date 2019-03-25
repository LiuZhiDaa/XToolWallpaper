package app.xtoolwallpaper.com.myapplication.base.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class Lineinfo implements BaseBean{


    private int ad_config;
    @JSONField(name = "line_info")
    private List<ItemInfo> mItemInfoList;

    public List<ItemInfo> getItemInfoList() {
        return mItemInfoList;
    }


    public int getAd_config() {
        return ad_config;
    }

    public void setAd_config(int ad_config) {
        this.ad_config = ad_config;
    }

    public void setItemInfoList(List<ItemInfo> itemInfoList) {
        mItemInfoList = itemInfoList;
    }


}
