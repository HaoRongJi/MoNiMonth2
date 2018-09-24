package com.bwie.monimonth2.model;

import com.bwie.monimonth2.api.Api;
import com.bwie.monimonth2.api.HomeApi;
import com.bwie.monimonth2.contract.HomeContract;
import com.bwie.monimonth2.entity.HomeEntity;
import com.hao.base.net.RetrofitUtils;

import io.reactivex.Observable;

public class HomeModel implements HomeContract.IHomeModel {
    @Override
    public Observable<HomeEntity> getHome() {
        return RetrofitUtils.getInstanse().createApi(Api.BASE_URL,HomeApi.class).getHome();
    }
}
