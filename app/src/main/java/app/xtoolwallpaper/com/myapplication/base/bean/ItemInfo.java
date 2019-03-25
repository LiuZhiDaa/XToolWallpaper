package app.xtoolwallpaper.com.myapplication.base.bean;

import java.util.List;

public class ItemInfo {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String url_tumb;
    private String url_img;
    private int corner_type;
    private List<Integer> purchase_type;

    public List<Integer> getPurchase_type() {
        return purchase_type;
    }

    public void setPurchase_type(List<Integer> purchase_type) {
        this.purchase_type = purchase_type;
    }

    private String price;

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

    public int getCorner_type() {
        return corner_type;
    }

    public void setCorner_type(int corner_type) {
        this.corner_type = corner_type;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
