package app.xtoolwallpaper.com.myapplication.base;

import android.support.annotation.Nullable;


import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public abstract class BaseSelectedAdapter<T> extends BaseAdapter<T> {

    private int mSelectedPosition = 0;
    private boolean isEnabled = true;
    private OnItemClickListener mOnItemClickListener;
    private List<T> mDataList = null;

    public BaseSelectedAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        mDataList = data;
        super.setOnItemClickListener((adapter, view, position) -> {
            if (!isEnabled) return;
            notifyItemChanged(mSelectedPosition);
            notifyItemChanged(position);
            mSelectedPosition = position;
        });
    }


    @Override
    protected void convert(BaseViewHolder helper, T item) {
        if (mOnItemClickListener != null && mSelectedPosition == helper.getAdapterPosition()) {
            mOnItemClickListener.onItemClick(this, helper.itemView, mSelectedPosition);
        }
        convert(helper, item);
    }

    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public void setOnItemClickListener(@Nullable OnItemClickListener listener, int selectedPosition) {
        this.mSelectedPosition = selectedPosition;
        this.mOnItemClickListener = listener;
    }

    public int getSelectedPosition() {
        return mSelectedPosition;
    }

    public void setSelectedPosition(int position) {
        this.mSelectedPosition = position;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }


}
