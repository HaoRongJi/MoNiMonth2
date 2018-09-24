package com.bwie.monimonth2.api;

import com.bwie.monimonth2.entity.HomeEntity;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface HomeApi {

    @POST("home/getHome")
    Observable<HomeEntity> getHome();


}
