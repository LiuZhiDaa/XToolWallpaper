package app.xtoolwallpaper.com.myapplication.base.bean;

import java.util.List;

public class DynamicContentInfo {


    /**
     * data : {"all_line_info":[{"ad_config":0,"line_info":[{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/images/1552300792116_0003.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/images/1552300792116_0003.jpg","corner_type":0,"purchase_type":[0],"price":0},{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0},{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0}]},{"ad_config":0,"line_info":[{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0},{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0},{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0}]},{"ad_config":1,"line_info":[]},{"ad_config":0,"line_info":[{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0},{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0},{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0}]},{"ad_config":0,"line_info":[{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0},{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0},{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0}]},{"ad_config":1,"line_info":[]},{"ad_config":0,"line_info":[{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0},{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0},{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0}]},{"ad_config":0,"line_info":[{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0}]}]}
     * code : 1
     * mag : 拉取壁纸成功
     */

    private DataBean data;
    private int code;
    private String mag;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMag() {
        return mag;
    }

    public void setMag(String mag) {
        this.mag = mag;
    }

    public static class DataBean {
        private List<AllLineInfoBean> all_line_info;

        public List<AllLineInfoBean> getAll_line_info() {
            return all_line_info;
        }

        public void setAll_line_info(List<AllLineInfoBean> all_line_info) {
            this.all_line_info = all_line_info;
        }

        public static class AllLineInfoBean {
            /**
             * ad_config : 0
             * line_info : [{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/images/1552300792116_0003.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/images/1552300792116_0003.jpg","corner_type":0,"purchase_type":[0],"price":0},{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0},{"url_tumb":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/shipin.jpg","url_img":"http://192.168.3.9:8888/cutecamera_images/wallpaper_images/videos/1551953281076_shipin.mp4","corner_type":0,"purchase_type":[0],"price":0}]
             */

            private int ad_config;
            private List<LineInfoBean> line_info;

            public int getAd_config() {
                return ad_config;
            }

            public void setAd_config(int ad_config) {
                this.ad_config = ad_config;
            }

            public List<LineInfoBean> getLine_info() {
                return line_info;
            }

            public void setLine_info(List<LineInfoBean> line_info) {
                this.line_info = line_info;
            }

            public static class LineInfoBean {
                /**
                 * url_tumb : http://192.168.3.9:8888/cutecamera_images/wallpaper_images/images/1552300792116_0003.jpg
                 * url_img : http://192.168.3.9:8888/cutecamera_images/wallpaper_images/images/1552300792116_0003.jpg
                 * corner_type : 0
                 * purchase_type : [0]
                 * price : 0
                 */

                private String url_tumb;
                private String url_img;
                private int corner_type;
                private int price;
                private List<Integer> purchase_type;

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
        }
    }
}
