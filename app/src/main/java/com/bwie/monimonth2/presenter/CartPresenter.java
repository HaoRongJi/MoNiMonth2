package com.bwie.monimonth2.presenter;

import com.bwie.monimonth2.contract.CartContract;
import com.bwie.monimonth2.entity.ShowCartsBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartPresenter extends CartContract.CartPresenter {
    @Override
    public void getCart(String uid) {
        mModel.getCart(uid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ShowCartsBean>() {
                    @Override
                    public void accept(ShowCartsBean showCartsBean) throws Exception {
                        mView.onSuccess(showCartsBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onFailure(throwable.toString());
                    }
                });
    }
}
