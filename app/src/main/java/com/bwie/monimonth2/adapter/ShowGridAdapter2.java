package com.bwie.monimonth2.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.bwie.monimonth2.R;
import com.bwie.monimonth2.entity.HomeEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class ShowGridAdapter2 extends BaseQuickAdapter<HomeEntity.DataBean.MiaoshaBean.ListBean,BaseViewHolder> {

    private SimpleDraweeView view;
    private String[] split;

    public ShowGridAdapter2(int layoutResId, @Nullable List<HomeEntity.DataBean.MiaoshaBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeEntity.DataBean.MiaoshaBean.ListBean item) {
        helper.setText(R.id.title_text_1,item.getBargainPrice()+"￥");

        view = helper.getView(R.id.icon_img_1);

        split = item.getImages().split("\\|");

        view.setImageURI(split[0]);
    }
}
