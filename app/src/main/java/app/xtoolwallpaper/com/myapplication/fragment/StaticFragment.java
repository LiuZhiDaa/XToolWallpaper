package app.xtoolwallpaper.com.myapplication.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.xtoolwallpaper.com.myapplication.GC;
import app.xtoolwallpaper.com.myapplication.R;
import app.xtoolwallpaper.com.myapplication.adapter.MyPagerAdapter;
import app.xtoolwallpaper.com.myapplication.base.BaseFragment;
import app.xtoolwallpaper.com.myapplication.base.bean.TitleBean;
import app.xtoolwallpaper.com.myapplication.fragment.ui.MainPresenter;
import app.xtoolwallpaper.com.myapplication.tablayout.TabLayout;
import app.xtoolwallpaper.com.myapplication.view.SlidingTabLayout;
import okhttp3.Call;

public class StaticFragment extends BaseFragment<MainPresenter> {
    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;
    List<String> list_Title = new ArrayList<>();
    List<Fragment> fragmentList;

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_static, container, false);
        return view;
    }

    @Override
    public void init() {
         tabLayout = getTabLayout();
         viewPager = getViewPager();
        fragmentList= new ArrayList<>();
        for (int i = 0; i < GC.sTitleBeans_static.size(); i++) {
            list_Title.add(GC.sTitleBeans_static.get(i).getTitle());
            fragmentList.add(new StaticItemFragment(GC.sTitleBeans_static.get(i).getId()));
        }
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), getContext(), fragmentList, list_Title));
        tabLayout.setViewPager(viewPager);
        viewPager.setOffscreenPageLimit(GC.sTitleBeans_static.size()-1);
    }



}
