package app.xtoolwallpaper.com.myapplication.api;


public class C {

    /**
     * 交互HEAD信息
     */
    public static final class HEAD {
        public static final String USER_AGENT = "UrlConnection - LZDHttpClient V1.0";
        public static final String CONNECTION = "Keep-Alive";
        public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
        public static final String CONTENT_LANGUAGE = "zh-cn";
        public static final String CACHE_CONTROL = "no-cache";
        public static final String ACCEPT_LANGUAGE = "zh-cn";
    }

    /**
     * 接口设置
     */
    public final class API_BUILDER {
        public static final int READ_TIMEOUT = 60 * 1000;
        public static final int CONNECT_TIMEOUT = 60 * 1000;
        public static final int RETRY_DELAY_TIME = 3 * 1000;
    }

    public static final class URL {
        /**
         * 总地址
         */
                public static final String BASE_URL = "http://192.168.3.9:8888/api/v3/wallpaper_android/";
//        public static final String BASE_URL = "http://www.davinwsang.com/api/v3/wallpaper_android/";

        /**
         * 分类
         */
        public static final class classification {
            public static final String classification = BASE_URL + "category_Info/wallpaper_android";
        }

        /**
         * 内容
         */
        public static final class content {
            public static final String content = BASE_URL + "content_details/wallpaper_android";
        }


        /**
         * 下载计数
         */
        public static final class download {
            public static final String count = BASE_URL + "download_count/wallpaper_android";
        }

        /**
         * 下载计数
         */
        public static final class notification {
            public static final String notification = BASE_URL + "update_push/wallpaper_android";
        }

        /**
         * 隐私协议
         */
        public static final class policy {
            public static final String VALUE_STRING_PRIVACY_POLICY_URL = "https://wallpapers-pp.weebly.com";

        }

        /**
         * 用户协议
         */
        public static final class termofuser {
            public static final String VALUE_STRING_TERM_OF_USE = "https://wallpapers-pp.weebly.com/privacy-policy.html";

        }
    }


}