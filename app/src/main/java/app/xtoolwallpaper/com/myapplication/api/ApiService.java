package app.xtoolwallpaper.com.myapplication.api;

import com.alibaba.fastjson.JSONObject;

import app.xtoolwallpaper.com.myapplication.base.bean.Mesg;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface ApiService {
    @POST(C.URL.classification.classification)
    Observable<Mesg<String>> classification(@Body RequestBody jsonObject);

    @POST(C.URL.content.content)
    Observable<Mesg<String>> content(@Body RequestBody jsonObject);
    @POST(C.URL.download.count)
    Observable<Mesg<String>> downloadcount(@Body JSONObject jsonObject);
    @POST(C.URL.notification.notification)
    Observable<Mesg<String>> getNotification(@Body RequestBody jsonObject);
}
