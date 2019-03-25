package app.xtoolwallpaper.com.myapplication.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
//import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import app.xtoolwallpaper.com.myapplication.R;
import app.xtoolwallpaper.com.myapplication.XApplication;
import app.xtoolwallpaper.com.myapplication.base.BaseSelectedAdapter;

import app.xtoolwallpaper.com.myapplication.base.bean.Lineinfo;

import app.xtoolwallpaper.com.myapplication.utils.SPUtil;
import app.xtoolwallpaper.com.myapplication.utils.UtilsApp;

public class DynamicAdapter extends BaseSelectedAdapter<Lineinfo> {

    public DynamicAdapter(@Nullable List<Lineinfo> data) {
        super(R.layout.item_vp, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Lineinfo item) {
        if (item.getAd_config() == 1) {
            helper.getView(R.id.item_lin).setVisibility(View.GONE);
        }
        helper.getView(R.id.img_a_isUse).setVisibility(View.GONE);
        helper.getView(R.id.img_b_isUse).setVisibility(View.GONE);
        helper.getView(R.id.img_c_isUse).setVisibility(View.GONE);
        helper.addOnClickListener(R.id.img_a);
        helper.addOnClickListener(R.id.img_b);
        helper.addOnClickListener(R.id.img_c);
        int swidth = UtilsApp.getScreenWidth(XApplication.APP);
        int ivWidth = ((swidth) - UtilsApp.px2dp(XApplication.APP, 12)) / 3;
        int ivHeight = ivWidth / 9 * 16;
        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_wallpaper_loading).skipMemoryCache(false)
                .error(R.drawable.ic_wallpaper_loading).transform(roundedCorners)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).override(ivWidth, ivHeight);
        for (int i = 0; i < item.getItemInfoList().size(); i++) {
            if (i == 0) {
                if (item.getItemInfoList().get(i).getUrl_tumb() == null || item.getItemInfoList().get(i).getUrl_tumb().equals("")) {
                    helper.getView(R.id.img_a_Corne).setVisibility(View.INVISIBLE);
                } else {
                    Glide.with(mContext).load(item.getItemInfoList().get(i).getUrl_tumb())
                            .apply(options)
                            .into((ImageView) helper.getView(R.id.img_a));
                    //     RequestOptions.bitmapTransform(new RoundedCorners(10))
                    //    .into(new TransformationUtils((ImageView) helper.getView(R.id.img_a)));
                    if (SPUtil.getId() == item.getItemInfoList().get(i).getId()) {
                        helper.getView(R.id.img_a_isUse).setVisibility(View.VISIBLE);
                    }
                }
            } else if (i == 1) {
                if (item.getItemInfoList().get(i).getUrl_tumb() == null || item.getItemInfoList().get(i).getUrl_tumb().equals("")) {
                    helper.getView(R.id.img_b_Corne).setVisibility(View.INVISIBLE);
                } else {
                    Glide.with(mContext).load(item.getItemInfoList().get(i).getUrl_tumb())
                            .apply(options)
                            .into((ImageView) helper.getView(R.id.img_b));
                    if (SPUtil.getId() == item.getItemInfoList().get(i).getId()) {
                        helper.getView(R.id.img_b_isUse).setVisibility(View.VISIBLE);
                    }
                }
            } else {
                if (item.getItemInfoList().get(i).getUrl_tumb() == null || item.getItemInfoList().get(i).getUrl_tumb().equals("")) {
                    helper.getView(R.id.img_c_Corne).setVisibility(View.INVISIBLE);
                } else {
                    Glide.with(mContext).load(item.getItemInfoList().get(i).getUrl_tumb())
                            .apply(options)
                            .into((ImageView) helper.getView(R.id.img_c));
                    if (SPUtil.getId() == item.getItemInfoList().get(i).getId()) {
                        helper.getView(R.id.img_c_isUse).setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

}
