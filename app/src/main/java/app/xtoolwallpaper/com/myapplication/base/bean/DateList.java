package app.xtoolwallpaper.com.myapplication.base.bean;

import java.util.List;

public class DateList {
    private int dataType;  //0 刷新数据  1是加载数据

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    private int type;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int id;
    private List<Lineinfo> mLineinfos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Lineinfo> getLineinfos() {
        return mLineinfos;
    }

    public void setLineinfos(List<Lineinfo> lineinfos) {
        mLineinfos = lineinfos;
    }
}
