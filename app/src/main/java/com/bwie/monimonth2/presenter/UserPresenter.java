package com.bwie.monimonth2.presenter;

import com.bwie.monimonth2.contract.UserContract;
import com.bwie.monimonth2.entity.UserEntity;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserPresenter extends UserContract.UserPresenter {
    @Override
    public void getUser(String uid) {
        mModel.getUser(uid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserEntity>() {
                    @Override
                    public void accept(UserEntity userEntity) throws Exception {
                        mView.onSuccess(userEntity);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onFailure(throwable.toString());
                    }
                });
    }

    @Override
    public void upLoadHead(File headfile, String uid) {

    }
}
