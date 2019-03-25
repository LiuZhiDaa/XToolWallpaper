package app.xtoolwallpaper.com.myapplication.base.bean;

import com.blankj.utilcode.util.LogUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * 网络异常处理类
 *
 * @param <T>
 */
public class ErrorFilter<T> implements Function<Throwable, Mesg<T>> {

    @Override
    public Mesg<T> apply(@NonNull Throwable throwable) {
        LogUtils.e(throwable.getMessage());
        //可根据Throwable具体区分,现在先统一变成服务器异常
        return new Mesg<>("500", "数据异常");
    }
}