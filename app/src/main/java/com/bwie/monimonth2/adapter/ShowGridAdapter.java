package com.bwie.monimonth2.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.bwie.monimonth2.R;
import com.bwie.monimonth2.entity.HomeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class ShowGridAdapter extends BaseQuickAdapter<HomeEntity.DataBean.FenleiBean,BaseViewHolder> {

    private SimpleDraweeView view;

    public ShowGridAdapter(int layoutResId, @Nullable List<HomeEntity.DataBean.FenleiBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeEntity.DataBean.FenleiBean item) {
        helper.setText(R.id.title_text,item.getName());

        view = helper.getView(R.id.icon_img);

        view.setImageURI(item.getIcon());
    }
}
