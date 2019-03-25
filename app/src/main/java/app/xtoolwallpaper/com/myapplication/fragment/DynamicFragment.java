package app.xtoolwallpaper.com.myapplication.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import app.xtoolwallpaper.com.myapplication.GC;
import app.xtoolwallpaper.com.myapplication.R;
import app.xtoolwallpaper.com.myapplication.adapter.MyPagerAdapter;
import app.xtoolwallpaper.com.myapplication.base.BaseFragment;
import app.xtoolwallpaper.com.myapplication.fragment.ui.MainPresenter;
import app.xtoolwallpaper.com.myapplication.tablayout.TabLayout;
import app.xtoolwallpaper.com.myapplication.view.SlidingTabLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DynamicFragment extends BaseFragment<MainPresenter> {
    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> list_Title;
    private List<Fragment> fragmentList;



    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        return view;
    }

    @Override
    public void init() {
        tabLayout = getTabLayout();
        viewPager = getViewPager();
        fragmentList = new ArrayList<>();
        list_Title = new ArrayList<>();
        for (int i = 0; i < GC.sTitleBeans_dynamiic.size(); i++) {
            list_Title.add(GC.sTitleBeans_dynamiic.get(i).getTitle());
            fragmentList.add(new ItemFragment(GC.sTitleBeans_dynamiic.get(i).getId()));
            mPresenter.loadDynamic(1,GC.sTitleBeans_dynamiic.get(i).getId());
        }

        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), getContext(), fragmentList, list_Title));
        tabLayout.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(GC.sTitleBeans_dynamiic.size()-1);
    }


}
