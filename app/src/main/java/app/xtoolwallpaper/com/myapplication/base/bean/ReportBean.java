package app.xtoolwallpaper.com.myapplication.base.bean;

public class ReportBean implements BaseBean{
    private String systemVersion;
    private String brand;
    private String deviceid;
    private String creatTime;
    private int behaviorId;
    private int downLoad;
    private int wideth;
    private int height;

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public int getBehaviorId() {
        return behaviorId;
    }

    public void setBehaviorId(int behaviorId) {
        this.behaviorId = behaviorId;
    }

    public int getDownLoad() {
        return downLoad;
    }

    public void setDownLoad(int doownLoad) {
        this.downLoad = doownLoad;
    }

    public int getWideth() {
        return wideth;
    }

    public void setWideth(int wideth) {
        this.wideth = wideth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
