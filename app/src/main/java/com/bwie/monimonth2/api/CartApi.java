package com.bwie.monimonth2.api;

import com.bwie.monimonth2.entity.ShowCartsBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CartApi {

    @POST("product/getCarts")
    Observable<ShowCartsBean> getCarts(@Query("uid") String uid);

}
