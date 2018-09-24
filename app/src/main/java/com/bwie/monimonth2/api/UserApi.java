package com.bwie.monimonth2.api;

import com.bwie.monimonth2.entity.HeadEntity;
import com.bwie.monimonth2.entity.UserEntity;

import java.io.File;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @GET("user/getUserInfo")
    Observable<UserEntity> getUsetInfo(@Query("uid") String uid);

    @POST("file/upload")
    @Multipart
    Observable<HeadEntity> upLoadHead(@Field("file") File head,@Query("uid") String uid);

}
