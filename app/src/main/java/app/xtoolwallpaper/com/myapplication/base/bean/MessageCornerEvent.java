package app.xtoolwallpaper.com.myapplication.base.bean;

public class MessageCornerEvent {
    private int MsgCode;

    public MessageCornerEvent(int messageCode) {
        this.MsgCode = messageCode;
    }


    public int getMsgCode() {
        return MsgCode;
    }

    public void setMsgCode(int msgCode) {
        MsgCode = msgCode;
    }
}
