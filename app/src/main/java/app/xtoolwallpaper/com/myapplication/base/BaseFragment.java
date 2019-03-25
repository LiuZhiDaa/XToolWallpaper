package app.xtoolwallpaper.com.myapplication.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import app.xtoolwallpaper.com.myapplication.R;
import app.xtoolwallpaper.com.myapplication.view.SlidingTabLayout;

/**
 * BaseFragment,供新创建的Fragment继承
 *
 * @param <P> 其依附的Activity的Presenter的类
 * @author Liuzhida
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment {
    public P mPresenter;
    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;
    private View mContentView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout) inflater.inflate(R.layout.fragment_base, container, false);
        tabLayout=view.findViewById(R.id.tablayout);
        mPresenter = (P) getBaseActivity().mPresenter;
        viewPager=view.findViewById(R.id.viewpager);
        mContentView = initView(inflater,container,savedInstanceState);
        view.addView(mContentView);
        init();
        return view;
    }
    public abstract View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
    public abstract void init();
    public BaseActivity getBaseActivity() {
        return ((BaseActivity) getActivity());
    }
    public SlidingTabLayout getTabLayout(){
        return tabLayout;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
