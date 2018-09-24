package com.bwie.monimonth2.app;

import android.app.Application;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DiskCacheConfig diskCacheConfig=DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName("images")
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                .build();

        ImagePipelineConfig config=ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this,config);
        ZXingLibrary.initDisplayOpinion(this);
    }
}
