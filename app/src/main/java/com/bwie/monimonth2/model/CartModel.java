package com.bwie.monimonth2.model;

import com.bwie.monimonth2.api.Api;
import com.bwie.monimonth2.api.CartApi;
import com.bwie.monimonth2.contract.CartContract;
import com.bwie.monimonth2.entity.ShowCartsBean;
import com.hao.base.net.RetrofitUtils;

import io.reactivex.Observable;

public class CartModel implements CartContract.ICartModel {
    @Override
    public Observable<ShowCartsBean> getCart(String uid) {
        return RetrofitUtils.getInstanse().createApi(Api.BASE_URL,CartApi.class).getCarts(uid);
    }
}
