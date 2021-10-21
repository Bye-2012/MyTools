package com.moon.mui.banner.core;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.moon.mui.banner.MBanner;

import java.util.List;

/**
 * Date: 2021/10/21 2:06 下午
 * Author: Moon
 * Desc: Banner Adapter
 */
public class MBannerAdapter extends PagerAdapter {

    private final Context mContext;
    private SparseArray<MBannerViewHolder> mCachedViews;
    private MBanner.OnBannerClickListener mBannerClickListener;
    private IBindAdapter mBindAdapter;
    private List<? extends MBannerMo> mModels;
    private boolean mAutoPlay = true;
    private boolean mLoop = false; //非自动轮播状态下是否可以循环切换
    private int mLayoutResId = -1;

    public MBannerAdapter(@NonNull Context context) {
        this.mContext = context;
    }

    public void setBannerData(@NonNull List<? extends MBannerMo> models) {
        this.mModels = models;
        initCacheView();
        notifyDataSetChanged();
    }

    private void initCacheView() {
        mCachedViews = new SparseArray<>();
        for (int i = 0; i < mModels.size(); i++) {
            MBannerViewHolder viewHolder = new MBannerViewHolder(createView(LayoutInflater.from(mContext), null));
            mCachedViews.put(i, viewHolder);
        }
    }

    public void setBannerClickListener(MBanner.OnBannerClickListener bannerClickListener) {
        this.mBannerClickListener = bannerClickListener;
    }

    public void setBindAdapter(IBindAdapter bindAdapter) {
        this.mBindAdapter = bindAdapter;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
    }

    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    public void setLayoutResId(int layoutResId) {
        this.mLayoutResId = layoutResId;
    }

    private View createView(LayoutInflater layoutInflater, ViewGroup parent) {
        if (mLayoutResId == -1) {
            throw new IllegalArgumentException("you must invoke setLayoutResId first");
        }
        return layoutInflater.inflate(mLayoutResId, parent, false);
    }

    /**
     * 获取初次展示的item位置
     *
     * @return
     */
    public int getFirstItem() {
        //这里是为了配合instantiateItem方法中realPosition = position % getRealCount();
        // - (Integer.MAX_VALUE / 2) % getRealCount()的主要目的是用于获取realPosition=0的位置
        return Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % getRealCount();
    }

    @Override
    public int getCount() {
        //无限轮播关键点
        return mAutoPlay ? Integer.MAX_VALUE : (mLoop ? Integer.MAX_VALUE : getRealCount());
    }

    /**
     * 获取实际Banner页面数量
     *
     * @return 数量
     */
    public int getRealCount() {
        return mModels == null ? 0 : mModels.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //让item每次都会刷新
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position;
        if (realPosition > 0) {
            realPosition = position % getRealCount();
        }
        MBannerViewHolder viewHolder = mCachedViews.get(realPosition);
        if (container.equals(viewHolder.rootView.getParent())) {
            container.removeView(viewHolder.rootView);
        }
        onBind(viewHolder, mModels.get(realPosition), realPosition);
        if (viewHolder.rootView.getParent() != null) {
            ((ViewGroup) viewHolder.rootView.getParent()).removeView(viewHolder.rootView);
        }
        container.addView(viewHolder.rootView);
        return viewHolder.rootView;
    }

    private void onBind(MBannerViewHolder viewHolder, MBannerMo mo, int position) {
        viewHolder.rootView.setOnClickListener(v -> {
            if (mBannerClickListener != null) {
                mBannerClickListener.onBannerClick(viewHolder, mo, position);
            }
        });
        if (mBindAdapter != null) {
            mBindAdapter.onBind(viewHolder, mo, position);
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }

    public static class MBannerViewHolder {
        View rootView;
        private SparseArray<View> viewHolderSparseArr;

        public MBannerViewHolder(View rootView) {
            this.rootView = rootView;
        }

        public View getRootView() {
            return rootView;
        }

        @SuppressWarnings("unchecked")
        public <V extends View> V findViewById(int id) {
            if (!(rootView instanceof ViewGroup)) {
                return (V) rootView;
            }
            if (this.viewHolderSparseArr == null) {
                this.viewHolderSparseArr = new SparseArray<>(1);
            }

            V childView = (V) viewHolderSparseArr.get(id);
            if (childView == null) {
                childView = rootView.findViewById(id);
                this.viewHolderSparseArr.put(id, childView);
            }

            return childView;
        }
    }
}
