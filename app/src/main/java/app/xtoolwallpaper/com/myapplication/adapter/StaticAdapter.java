package app.xtoolwallpaper.com.myapplication.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;


import java.util.ArrayList;
import java.util.List;

import app.xtoolwallpaper.com.myapplication.R;
import app.xtoolwallpaper.com.myapplication.XApplication;
import app.xtoolwallpaper.com.myapplication.base.BaseSelectedAdapter;
import app.xtoolwallpaper.com.myapplication.base.bean.Lineinfo;

import app.xtoolwallpaper.com.myapplication.utils.SPUtil;
import app.xtoolwallpaper.com.myapplication.utils.UtilsApp;


public class StaticAdapter extends BaseSelectedAdapter<Lineinfo> {
    private List<Integer> checkPositionlist;

    public StaticAdapter(@Nullable List<Lineinfo> data) {
        super(R.layout.item_static_vp, data);
        checkPositionlist = new ArrayList<>();
    }

    @Override
    protected void convert(BaseViewHolder helper, Lineinfo item) {
        if (item.getAd_config() == 1) {
            helper.getView(R.id.item_lin).setVisibility(View.GONE);
        }
        helper.getView(R.id.img_a_isuse).setVisibility(View.GONE);
        helper.getView(R.id.img_b_isuse).setVisibility(View.GONE);
        helper.getView(R.id.img_c_isuse).setVisibility(View.GONE);
        Log.i("@@@", "Convert Fragment ID ==" + SPUtil.getId());
        helper.addOnClickListener(R.id.img_a);
        helper.addOnClickListener(R.id.img_b);
        helper.addOnClickListener(R.id.img_c);
        RoundedCorners roundedCorners = new RoundedCorners(10);
        int swidth = UtilsApp.getScreenWidth(XApplication.APP);
        int ivWidth = ((swidth) - UtilsApp.px2dp(XApplication.APP, 12)) / 3;
        int ivHeight = ivWidth / 9 * 16;
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_wallpaper_loading).skipMemoryCache(false)
                .error(R.drawable.ic_wallpaper_loading).transform(roundedCorners)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(ivWidth, ivHeight);
        for (int i = 0; i < item.getItemInfoList().size(); i++)
            if (i == 0) {
                Glide.with(mContext).load(item.getItemInfoList().get(i).getUrl_tumb())
                        .apply(options)
                        .into((ImageView) helper.getView(R.id.img_a));
                Log.i("@@@", "Item0 ID ==" + item.getItemInfoList().get(i).getId());
                Log.i("@@@", "Item0 Url_tumb ==" + item.getItemInfoList().get(i).getUrl_tumb());
                Log.i("@@@", "Item0 Url_image ==" + item.getItemInfoList().get(i).getUrl_img());
                if (SPUtil.getId() == item.getItemInfoList().get(i).getId()) {
                    helper.getView(R.id.img_a_isuse).setVisibility(View.VISIBLE);
                }
            } else if (i == 1) {
                Log.i("@@@", "Item1 ID ==" + item.getItemInfoList().get(i).getId());
                Log.i("@@@", "Item1 Url_tumb ==" + item.getItemInfoList().get(i).getUrl_tumb());
                Log.i("@@@", "Item0 Url_image ==" + item.getItemInfoList().get(i).getUrl_img());
                Glide.with(mContext).load(item.getItemInfoList().get(i).getUrl_tumb())
                        .apply(options)
                        .into((ImageView) helper.getView(R.id.img_b));
                if (SPUtil.getId() == item.getItemInfoList().get(i).getId()) {
                    helper.getView(R.id.img_b_isuse).setVisibility(View.VISIBLE);
                }
            } else {
                Log.i("@@@", "Item2 ID ==" + item.getItemInfoList().get(i).getId());
                Log.i("@@@", "Item2 Url_tumb ==" + item.getItemInfoList().get(i).getUrl_tumb());
                Log.i("@@@", "Item0 Url_image ==" + item.getItemInfoList().get(i).getUrl_img());
                Glide.with(mContext).load(item.getItemInfoList().get(i).getUrl_tumb())
                        .apply(options)
                        .into((ImageView) helper.getView(R.id.img_c));
                if (SPUtil.getId() == item.getItemInfoList().get(i).getId()) {
                    helper.getView(R.id.img_c_isuse).setVisibility(View.VISIBLE);
                }
            }
    }

}
