package app.xtoolwallpaper.com.myapplication.base.bean;

import java.util.List;

public class PushInfoBean {
    /**
     * id : 112
     * title : 更新壁纸
     * content : 全世界最好看的壁纸
     * url_tumb : http://192.168.3.9/cutecamera_images/wallpaper_images/videos/shipin.jpg
     * url_img : http://192.168.3.9/cutecamera_images/wallpaper_images/videos/1551953281076.mp4
     * purchase_type : [0]
     * price : 0
     * img_type  0 代表静态 1代表动态;
     */

    private int id;
    private String title;
    private String content;
    private String url_tumb;
    private String url_img;
    private int price;
    private int img_type;

    public int getImg_type() {
        return img_type;
    }

    public void setImg_type(int img_type) {
        this.img_type = img_type;
    }


    private List<Integer> purchase_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl_tumb() {
        return url_tumb;
    }

    public void setUrl_tumb(String url_tumb) {
        this.url_tumb = url_tumb;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Integer> getPurchase_type() {
        return purchase_type;
    }

    public void setPurchase_type(List<Integer> purchase_type) {
        this.purchase_type = purchase_type;
    }


}
