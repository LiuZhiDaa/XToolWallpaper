package app.xtoolwallpaper.com.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import app.xtoolwallpaper.com.myapplication.R;
import app.xtoolwallpaper.com.myapplication.activity.DetailActivity;
import app.xtoolwallpaper.com.myapplication.adapter.StaticAdapter;
import app.xtoolwallpaper.com.myapplication.base.BaseFragment;
import app.xtoolwallpaper.com.myapplication.base.bean.MessageCornerEvent;
import app.xtoolwallpaper.com.myapplication.base.bean.DateList;

import app.xtoolwallpaper.com.myapplication.fragment.ui.MainPresenter;
import app.xtoolwallpaper.com.myapplication.view.SlidingTabLayout;

@SuppressLint("ValidFragment")
public class StaticItemFragment extends BaseFragment<MainPresenter> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView mRecyclerView;
    private StaticAdapter mStaticAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    Intent intent;
    String mPicUrl;
    private int ids;
    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;
    private int id;//传给detail
    private View mFragmentView;
    private boolean isLoading = false;

    @SuppressLint("ValidFragment")
    public StaticItemFragment(int id) {
        super();
        this.ids = id;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mFragmentView = inflater.inflate(R.layout.item_static_fragment, container, false);
        mRecyclerView = mFragmentView.findViewById(R.id.m_RecycleView);
        mSwipeRefreshLayout = mFragmentView.findViewById(R.id.swipe_refresh_layout);
        mPresenter.loadStatic(0, ids);
        return mFragmentView;
    }

    @Override
    public void init() {
        tabLayout = getTabLayout();
        viewPager = getViewPager();
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        mStaticAdapter = new StaticAdapter(null);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                int itemViewId = view.getId();
                switch (itemViewId) {
                    case R.id.img_a:
                        mPicUrl = mStaticAdapter.getData().get(position).getItemInfoList().get(0).getUrl_img();
                        id = mStaticAdapter.getData().get(position).getItemInfoList().get(0).getId();
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_FLAG, true);
                        intent.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL, mPicUrl);
                        intent.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL_ID, id);
                        startActivity(intent);
                        break;
                    case R.id.img_b:
                        mPicUrl = mStaticAdapter.getData().get(position).getItemInfoList().get(1).getUrl_img();
                        id = mStaticAdapter.getData().get(position).getItemInfoList().get(1).getId();
                        Intent intent1 = new Intent(getActivity(), DetailActivity.class);
                        intent1.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL_ID, id);
                        intent1.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_FLAG, true);
                        intent1.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL, mPicUrl);
                        startActivity(intent1);
                        break;
                    case R.id.img_c:
                        mPicUrl = mStaticAdapter.getData().get(position).getItemInfoList().get(2).getUrl_img();
                        id = mStaticAdapter.getData().get(position).getItemInfoList().get(2).getId();
                        Intent intent2 = new Intent(getActivity(), DetailActivity.class);
                        intent2.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_FLAG, true);
                        intent2.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL, mPicUrl);
                        intent2.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL_ID, id);
                        startActivity(intent2);
                        break;

                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mStaticAdapter);

        mStaticAdapter.setOnLoadMoreListener(this, mRecyclerView);
    }


    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        //     mSwipeRefreshLayout.setRefreshing(false);
        mPresenter.loadStatic(0, ids);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.POSTING) //在ui线程执行
    public void CornerIconEvent(MessageCornerEvent event) {

        if (event.getMsgCode() == 0) {
            onRefresh();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void mEvent(DateList dateList) {
        if (dateList.getType() == 0 && dateList.getId() == ids && dateList.getDataType() == 0) {
            Log.d("aaa", dateList.getLineinfos().size() + "");
            mStaticAdapter.getData().clear();
            mSwipeRefreshLayout.setRefreshing(false);
            mStaticAdapter.setNewData(dateList.getLineinfos());
            mStaticAdapter.setEnableLoadMore(true);
            Log.d("aaa", mStaticAdapter.getData().size() + "");
        } else if (dateList.getType() == 0 && dateList.getId() == ids && dateList.getDataType() == 1) {
            if (dateList.getLineinfos().size() < 6) {
                Log.d("aaa", dateList.getLineinfos().size() + "");
                mStaticAdapter.addData(dateList.getLineinfos());
                mStaticAdapter.loadMoreComplete();
                mStaticAdapter.setEnableLoadMore(false);
                Log.d("aaa", mStaticAdapter.getData().size() + "");
            } else {
                mStaticAdapter.setEnableLoadMore(true);
                mStaticAdapter.addData(dateList.getLineinfos());
                mStaticAdapter.loadMoreComplete();
                Log.d("aaa", dateList.getLineinfos().size() + "");
            }

        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {
        mStaticAdapter.setEnableLoadMore(false);
        mPresenter.getLoadStatic(0, ids);
        isLoading = false;
    }
}
