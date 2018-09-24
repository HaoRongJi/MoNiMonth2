package com.bwie.monimonth2.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.monimonth2.R;
import com.bwie.monimonth2.adapter.CartAdapter;
import com.bwie.monimonth2.contract.CartContract;
import com.bwie.monimonth2.entity.ShowCartsBean;
import com.bwie.monimonth2.presenter.CartPresenter;
import com.hao.base.base.mvp.BaseMvpFragment;
import com.hao.base.base.mvp.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentFour extends BaseMvpFragment<CartContract.ICartModel, CartContract.CartPresenter> implements CartContract.ICartView {
    @BindView(R.id.f_recyclerView)
    RecyclerView fRecyclerView;
    @BindView(R.id.f_checkbox)
    CheckBox fCheckbox;
    @BindView(R.id.f_price)
    TextView fPrice;
    Unbinder unbinder;
    private CartAdapter cartAdapter;
    private List<ShowCartsBean.DataBean> data;

    @Override
    protected int setLayoutId() {
        return R.layout.fragmentfour_layout;
    }

    @Override
    public BasePresenter initPresenter() {
        return new CartPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getCart("17224");
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void fail(String Msg) {

    }

    @Override
    public void onSuccess(final ShowCartsBean showCartsBean) {
        Toast.makeText(mActivity, showCartsBean.toString(), Toast.LENGTH_SHORT).show();

        fRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartAdapter = new CartAdapter(R.layout.cart_item, showCartsBean.getData());
        fRecyclerView.setAdapter(cartAdapter);


        data = showCartsBean.getData();

        fCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fCheckbox.isChecked()) {
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setIschekbox(true);
                        for (int i1 = 0; i1 < data.get(i).getList().size(); i1++) {
                            data.get(i).getList().get(i1).setIscheckbox(true);
                        }
                    }
                } else {
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).setIschekbox(false);
                        for (int i1 = 0; i1 < data.get(i).getList().size(); i1++) {
                            data.get(i).getList().get(i1).setIscheckbox(false);
                        }
                    }

                }
                totalPrice();
                cartAdapter.notifyDataSetChanged();
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shub(String mas) {

        cartAdapter.notifyDataSetChanged();
        totalPrice();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shua(String ma) {
        cartAdapter.notifyDataSetChanged();

        StringBuilder stringBuilder = new StringBuilder();
        if (cartAdapter != null) {
            for (int i = 0; i < cartAdapter.getBean().size(); i++) {

                stringBuilder.append(cartAdapter.getBean().get(i).getIschekbox());

                for (int i1 = 0; i1 < cartAdapter.getBean().get(i).getList().size(); i1++) {

                    stringBuilder.append(cartAdapter.getBean().get(i).getList().get(i1).isIscheckbox());

                }
            }
        }
        if (stringBuilder.toString().contains("false")) {
            fCheckbox.setChecked(false);
        } else {
            fCheckbox.setChecked(true);
        }

        totalPrice();


    }

    private void totalPrice() {
        double totalprice = 0;
        /*循环嵌套*/
        for (int i = 0; i < cartAdapter.getBean().size(); i++) {
            for (int i1 = 0; i1 < cartAdapter.getBean().get(i).getList().size(); i1++) {
                if (cartAdapter.getBean().get(i).getList().get(i1).isIscheckbox()) {
                    ShowCartsBean.DataBean.ListBean listBean = cartAdapter.getBean().get(i).getList().get(i1);
                    totalprice += listBean.getTotalNum() * listBean.getBargainPrice();
                }
            }
        }
        fPrice.setText("合计：￥" + totalprice);
    }

    @Override
    public void onFailure(String Msg) {

        Toast.makeText(mActivity, Msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
