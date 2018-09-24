package com.bwie.monimonth2.model;

import com.bwie.monimonth2.api.Api;
import com.bwie.monimonth2.api.UserApi;
import com.bwie.monimonth2.contract.UserContract;
import com.bwie.monimonth2.entity.HeadEntity;
import com.bwie.monimonth2.entity.UserEntity;
import com.hao.base.net.RetrofitUtils;

import java.io.File;

import io.reactivex.Observable;

public class UserModel implements UserContract.IUserModel {
    @Override
    public Observable<UserEntity> getUser(String uid) {
        return RetrofitUtils.getInstanse().createApi(Api.BASE_URL,UserApi.class).getUsetInfo(uid);
    }

    @Override
    public Observable<HeadEntity> upLoadHead(File headfile, String uid) {
        return null;
    }
}
