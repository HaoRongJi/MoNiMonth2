package com.bwie.monimonth2.ui.activity;

import android.os.Bundle;

import com.bwie.monimonth2.R;
import com.bwie.monimonth2.ui.fragment.FragmentFive;
import com.bwie.monimonth2.ui.fragment.FragmentFour;
import com.bwie.monimonth2.ui.fragment.FragmentOne;
import com.gyf.barlibrary.ImmersionBar;
import com.hao.base.base.BaseActivity;
import com.hjm.bottomtabbar.BottomTabBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.bottom_tabbar)
    BottomTabBar bottomTabbar;

    @Override
    protected void initData() {

    }

    @Override
    public boolean getIsFullScreen() {
        return false;
    }

    @Override
    protected void initView() {

        ImmersionBar.with(this);

        bottomTabbar.init(getSupportFragmentManager())
                .setImgSize(30,30)
                .addTabItem("首页",R.drawable.ic_launcher_background,FragmentOne.class)
                .addTabItem("分类",R.drawable.ic_launcher_background,FragmentOne.class)
                .addTabItem("秘密",R.drawable.ic_launcher_background,FragmentOne.class)
                .addTabItem("购物车",R.drawable.ic_launcher_background,FragmentFour.class)
                .addTabItem("我的",R.drawable.ic_launcher_background,FragmentFive.class);


    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
