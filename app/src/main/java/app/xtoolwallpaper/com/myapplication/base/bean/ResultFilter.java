package app.xtoolwallpaper.com.myapplication.base.bean;



import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 用于将HttpMessage脱壳
 *
 * @param <T> 最后需要的数据类型
 * @author Zhangzhen
 */
public class ResultFilter<T> implements Function<Mesg<T>, T> {


    public ResultFilter() {
    }

    @Override
    public T apply(@NonNull Mesg<T> mesg) throws Exception {
        switch (mesg.getCode()) {
            case "1":
                return mesg.getData();
            case "500":
                throw new ApiException.IO(mesg.getCode(), mesg.getMsg());
            default:
                throw new ApiException.API(mesg.getCode(), mesg.getMsg());
        }
    }
}