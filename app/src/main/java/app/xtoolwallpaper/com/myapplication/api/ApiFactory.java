package app.xtoolwallpaper.com.myapplication.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.StringUtils;

import app.xtoolwallpaper.com.myapplication.base.bean.ErrorFilter;
import app.xtoolwallpaper.com.myapplication.base.bean.Mesg;
import app.xtoolwallpaper.com.myapplication.base.bean.ResultFilter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class ApiFactory {

    public static class APP {
        public static Observable<String> classification(RequestBody json) {
            return run(Api.getInstance().service.classification(json));
        }

        public static Observable<String> content(RequestBody json) {
            return run(Api.getInstance().service.content(json));
        }

        public static Observable<String> downloadcount(String json) {
            return run(Api.getInstance().service.downloadcount(toJSONObject(json)));
        }

        public static Observable<String> getNotification(RequestBody json) {
            return run(Api.getInstance().service.getNotification(json));
        }

    }


    private static <T> Observable<T> run(Observable<Mesg<T>> observable) {
        Observable<Mesg<T>> o = observable;
        return o.onErrorReturn(new ErrorFilter<>())
                .map(new ResultFilter<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static JSONObject toJSONObject(String json) {
        if (StringUtils.isEmpty(json)) {
            return new JSONObject();
        } else {
            try {
                return JSON.parseObject(json);
            } catch (JSONException e) {
                return new JSONObject();
            }
        }

    }
}
