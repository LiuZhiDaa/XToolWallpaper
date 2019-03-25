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
import app.xtoolwallpaper.com.myapplication.adapter.DynamicAdapter;
import app.xtoolwallpaper.com.myapplication.base.BaseFragment;
import app.xtoolwallpaper.com.myapplication.base.bean.MessageCornerEvent;
import app.xtoolwallpaper.com.myapplication.base.bean.DateList;
import app.xtoolwallpaper.com.myapplication.fragment.ui.MainPresenter;
import app.xtoolwallpaper.com.myapplication.view.SlidingTabLayout;

@SuppressLint("ValidFragment")
public class ItemFragment extends BaseFragment<MainPresenter> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    RecyclerView mRecyclerView;
    private DynamicAdapter mDynamicAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int id;
    private int mIdToDetail;
    private SlidingTabLayout tabLayout;
    private ViewPager viewPager;
    private boolean isLoading = false;

    @SuppressLint("ValidFragment")
    public ItemFragment(int id) {
        super();
        this.id = id;
    }


    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_dynamic_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.recycle);
        mSwipeRefreshLayout = view.findViewById(R.id.list_refresh);
        return view;
    }

    @Override
    public void init() {
        tabLayout = getTabLayout();
        viewPager = getViewPager();
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        initUI();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {//加上判断
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (EventBus.getDefault().isRegistered(this))//加上判断
//            EventBus.getDefault().unregister(this);
    }

    private void initUI() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mDynamicAdapter = new DynamicAdapter(null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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
                        mIdToDetail = mDynamicAdapter.getData().get(position).getItemInfoList().get(0).getId();
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL_ID, mIdToDetail);
                        intent.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_DYNAMIC_WALLPAPER_PIC_URL_ID, mDynamicAdapter.getData().get(position).getItemInfoList().get(0).getUrl_img());
                        intent.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_DYNAMIC_WALLPAPER_PIC_URL_TUMB_ID, mDynamicAdapter.getData().get(position).getItemInfoList().get(0).getUrl_tumb());
                        startActivity(intent);
                        break;
                    case R.id.img_b:
                        Intent intent1 = new Intent(getActivity(), DetailActivity.class);
                        mIdToDetail = mDynamicAdapter.getData().get(position).getItemInfoList().get(1).getId();
                        intent1.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_DYNAMIC_WALLPAPER_PIC_URL_TUMB_ID, mDynamicAdapter.getData().get(position).getItemInfoList().get(1).getUrl_tumb());
                        intent1.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_DYNAMIC_WALLPAPER_PIC_URL_ID, mDynamicAdapter.getData().get(position).getItemInfoList().get(1).getUrl_img());
                        intent1.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL_ID, mIdToDetail);
                        startActivity(intent1);

                        break;
                    case R.id.img_c:
                        mIdToDetail = mDynamicAdapter.getData().get(position).getItemInfoList().get(2).getId();
                        Intent intent2 = new Intent(getActivity(), DetailActivity.class);
                        intent2.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_DYNAMIC_WALLPAPER_PIC_URL_TUMB_ID, mDynamicAdapter.getData().get(position).getItemInfoList().get(2).getUrl_tumb());
                        intent2.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_DYNAMIC_WALLPAPER_PIC_URL_ID, mDynamicAdapter.getData().get(position).getItemInfoList().get(2).getUrl_img());
                        intent2.putExtra(DetailActivity.VALUE_STR_EXTRA_IS_STATIC_WALLPAPER_PIC_URL_ID, mIdToDetail);
                        startActivity(intent2);
                        break;
                }
            }

        });
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mDynamicAdapter);
        mDynamicAdapter.setOnLoadMoreListener(this, mRecyclerView);

    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void CornerIconDynamicEvent(MessageCornerEvent event) {
        if (event.getMsgCode() == 1) {
            onRefresh();
        }

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void mEvent(DateList dateList) {
        if (dateList.getType() == 1 && dateList.getId() == id && dateList.getDataType() == 0) {
            Log.d("aaa", dateList.getLineinfos().size() + "");
            mDynamicAdapter.getData().clear();
            mSwipeRefreshLayout.setRefreshing(false);
            mDynamicAdapter.setNewData(dateList.getLineinfos());
            mDynamicAdapter.setEnableLoadMore(true);
            Log.d("aaa", mDynamicAdapter.getData().size() + "");
        } else if (dateList.getType() == 1 && dateList.getId() == id && dateList.getDataType() == 1) {
            if (dateList.getLineinfos().size() < 6) {
                Log.d("aaa", dateList.getLineinfos().size() + "");
                mDynamicAdapter.addData(dateList.getLineinfos());
                mDynamicAdapter.loadMoreComplete();
                mDynamicAdapter.setEnableLoadMore(false);
                Log.d("aaa", mDynamicAdapter.getData().size() + "");
            } else {
                mDynamicAdapter.setEnableLoadMore(true);
                mDynamicAdapter.addData(dateList.getLineinfos());
                mDynamicAdapter.loadMoreComplete();
                Log.d("aaa", mDynamicAdapter.getData().size() + "");
            }
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onRefresh() {

        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.loadDynamic(1, id);
    }

    @Override
    public void onLoadMoreRequested() {
        mDynamicAdapter.setEnableLoadMore(false);
        mPresenter.getLoadDynamic(1, id);
    }
}
