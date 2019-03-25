package app.xtoolwallpaper.com.myapplication.base.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class ListLine {
    @JSONField(name = "all_line_info")
    private List<Lineinfo>  mLineinfos;

    public List<Lineinfo> getLineinfos() {
        return mLineinfos;
    }

    public void setLineinfos(List<Lineinfo> lineinfos) {
        mLineinfos = lineinfos;
    }
}
