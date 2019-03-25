package app.xtoolwallpaper.com.myapplication.base.bean;

public class MessageEvent {
    private int MsgCode;
    private int id;


    public MessageEvent(int messageCode) {
        this.MsgCode = messageCode;
    }

    public MessageEvent(int messageCode, int id) {
        this.MsgCode = messageCode;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMsgCode() {
        return MsgCode;
    }

    public void setMsgCode(int msgCode) {
        MsgCode = msgCode;
    }
}
