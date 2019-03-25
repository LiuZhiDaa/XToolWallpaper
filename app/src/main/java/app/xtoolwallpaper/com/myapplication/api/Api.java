package app.xtoolwallpaper.com.myapplication.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

class Api {
    ApiService service;

    private static class ApiHolder {
        private static final Api INSTANCE = new Api();
    }

    static Api getInstance() {
        return ApiHolder.INSTANCE;
    }

    private Api() {
        //LOG
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //HEAD
        Interceptor headInterceptor = (chain) -> chain.proceed(chain.request().newBuilder()
                .addHeader("User-Agent", C.HEAD.USER_AGENT)
                .addHeader("Connection", C.HEAD.CONNECTION)
                .addHeader("Content-Type", C.HEAD.CONTENT_TYPE)
                .addHeader("Content-Language", C.HEAD.CONTENT_LANGUAGE)
                .addHeader("Cache-Control", C.HEAD.CACHE_CONTROL)
                .addHeader("Accept-Language", C.HEAD.ACCEPT_LANGUAGE)
                .build());
        //Client
        OkHttpClient okHttpClient = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                .readTimeout(C.API_BUILDER.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(C.API_BUILDER.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(headInterceptor)
                .addInterceptor(logInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ConverterFactory.create())
                .baseUrl(C.URL.BASE_URL)
                .build();
        service = retrofit.create(ApiService.class);
    }

}
