package com.bwie.monimonth2.ui.fragment;

import android.support.annotation.Nullable;

import com.bwie.monimonth2.entity.HomeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ShowAdapter extends BaseQuickAdapter<HomeEntity.DataBean.TuijianBean.ListBeanX,BaseViewHolder> {

    public ShowAdapter(int layoutResId, @Nullable List<HomeEntity.DataBean.TuijianBean.ListBeanX> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeEntity.DataBean.TuijianBean.ListBeanX item) {

    }
}
