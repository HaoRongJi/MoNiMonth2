package com.bwie.monimonth2.presenter;

import com.bwie.monimonth2.contract.HomeContract;
import com.bwie.monimonth2.entity.HomeEntity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter extends HomeContract.HomePresenter {

    @Override
    public void getHome() {
        mModel.getHome().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HomeEntity>() {
                    @Override
                    public void accept(HomeEntity homeEntity) throws Exception {
                        mView.onSuccess(homeEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onFailure(throwable.toString());
                    }
                });
    }
}
