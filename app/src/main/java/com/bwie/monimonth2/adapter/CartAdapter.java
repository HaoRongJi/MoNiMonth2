package com.bwie.monimonth2.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.bwie.monimonth2.R;
import com.bwie.monimonth2.entity.ShowCartsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class CartAdapter extends BaseQuickAdapter<ShowCartsBean.DataBean,BaseViewHolder> {

    private Context mcontext;
    private List<ShowCartsBean.DataBean> bean;
    private int a= -1;

    public List<ShowCartsBean.DataBean> getBean() {
        return bean;
    }

    public CartAdapter(int layoutResId, @Nullable List<ShowCartsBean.DataBean> data) {
        super(layoutResId, data);
        EventBus.getDefault().register(this);
        bean= data;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shua(String ma) {

        notifyDataSetChanged();

    }



    @Override
    protected void convert(BaseViewHolder helper, ShowCartsBean.DataBean item) {

        final int positions = helper.getLayoutPosition();
        final ShowCartsBean.DataBean list = this.bean.get(positions);
        helper.setChecked(R.id.z_box, list.getIschekbox());
        helper.setText(R.id.z_text, list.getSellerName());
        RecyclerView z_recyclerview = helper.getView(R.id.z_recyclerview);
        final CheckBox z_box = helper.getView(R.id.z_box);
        z_recyclerview.setLayoutManager(new LinearLayoutManager(mcontext));
        final CartItemAdapter cartItemAdapter = new CartItemAdapter(R.layout.cart_item_layout, list.getList());
        z_recyclerview.setAdapter(cartItemAdapter);


        for (int i = 0; i < list.getList().size(); i++) {

            if (!list.getList().get(i).isIscheckbox()) {
                helper.setChecked(R.id.z_box, false);
                break;
            } else {
                helper.setChecked(R.id.z_box, true);
            }
        }

        z_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (z_box.isChecked()) {

                    list.setIschekbox(true);

                    for (int i = 0; i < list.getList().size(); i++) {
                        list.getList().get(i).setIscheckbox(true);
                    }
                } else {
                    list.setIschekbox(false);
                    for (int i = 0; i < list.getList().size(); i++) {
                        list.getList().get(i).setIscheckbox(false);
                    }

                }
                // notifyDataSetChanged();
                notifyItemChanged(positions);
                String mas ="";
                EventBus.getDefault().post(mas);
            }
        });


        cartItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int num = list.getList().get(position).getNum();
                switch (view.getId()) {
                    case R.id.zu_btn_del:
                        if (num == 1) {
                            return;
                        } else {
                            num--;
                            list.getList().get(position).setTotalNum(num);
                            list.getList().get(position).setNum(num);
                        }


                        notifyItemChanged(positions);
                        break;

                    case R.id.zu_btn_add:
                        num++;
                        list.getList().get(position).setTotalNum(num);
                        list.getList().get(position).setNum(num);

                        notifyItemChanged(positions);
                        break;

                }
            }
        });

    }


}
