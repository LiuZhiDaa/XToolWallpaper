package app.xtoolwallpaper.com.myapplication.base.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class NotificationBean implements BaseBean{

    /**
     * data : {"push_info":[{"id":112,"title":"更新壁纸","content":"全世界最好看的壁纸","url_tumb":"http://192.168.3.9/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9/cutecamera_images/wallpaper_images/videos/1551953281076.mp4","purchase_type":[0],"price":0}]}
     * code : 1
     */
    @JSONField(name = "push_info")
    private List<PushInfoBean> push_info;

    public List<PushInfoBean> getPush_info() {
        return push_info;
    }

    public void setPush_info(List<PushInfoBean> push_info) {
        this.push_info = push_info;
    }


}
