package com.bwie.monimonth2.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.monimonth2.R;
import com.bwie.monimonth2.adapter.ShowGridAdapter;
import com.bwie.monimonth2.adapter.ShowGridAdapter2;
import com.bwie.monimonth2.contract.HomeContract;
import com.bwie.monimonth2.entity.HomeEntity;
import com.bwie.monimonth2.presenter.HomePresenter;
import com.hao.base.base.mvp.BaseMvpFragment;
import com.hao.base.base.mvp.BasePresenter;
import com.stx.xhb.xbanner.XBanner;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FragmentOne extends BaseMvpFragment<HomeContract.IHomeModel, HomeContract.HomePresenter> implements HomeContract.IHomeView, XBanner.XBannerAdapter {
    @BindView(R.id.recycler_one)
    RecyclerView recyclerOne;
    Unbinder unbinder;
    @BindView(R.id.zxing)
    ImageView zxing;
    private View inflate;
    private XBanner xbannerOne;
    private ArrayList<String> strings;
    private String icon;
    private ShowAdapter showAdapter;
    private View inflate1;
    private RecyclerView recyclerTwo;
    private ShowGridAdapter showGridAdapter;
    private View inflate2;
    private RecyclerView recyclerThree;
    private ShowGridAdapter2 showGridAdapter2;

    @Override
    protected int setLayoutId() {
        return R.layout.fragmentone_layout;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getHome();
    }

    @Override
    public BasePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void fail(String Msg) {

    }

    @Override
    public void onSuccess(HomeEntity homeEntity) {
        Toast.makeText(mActivity, "homeEntity:" + homeEntity.toString(), Toast.LENGTH_SHORT).show();
        strings = new ArrayList<>();

        for (int i = 0; i < homeEntity.getData().getBanner().size(); i++) {

            icon = homeEntity.getData().getBanner().get(i).getIcon();
            strings.add(icon);

        }

        recyclerOne.setLayoutManager(new LinearLayoutManager(getActivity()));


        showAdapter = new ShowAdapter(R.layout.show_item_layout, homeEntity.getData().getTuijian().getList());

        recyclerOne.setAdapter(showAdapter);

        inflate = getLayoutInflater().inflate(R.layout.banner_one, (ViewGroup) recyclerOne.getParent(), false);
        xbannerOne = inflate.findViewById(R.id.xbannerone);

        showAdapter.addHeaderView(inflate);

        xbannerOne.setData(strings);
        xbannerOne.setmAdapter(this);

        inflate1 = getLayoutInflater().inflate(R.layout.fenlei, (ViewGroup) recyclerOne.getParent(), false);
        recyclerTwo = inflate1.findViewById(R.id.recycler_two);
        recyclerTwo.setLayoutManager(new GridLayoutManager(getActivity(), 10));
        showGridAdapter = new ShowGridAdapter(R.layout.grid_view_layout, homeEntity.getData().getFenlei());
        recyclerTwo.setAdapter(showGridAdapter);

        showAdapter.addHeaderView(inflate1);

        inflate2 = getLayoutInflater().inflate(R.layout.miaosha, (ViewGroup) recyclerOne.getParent(), false);

        recyclerThree = inflate2.findViewById(R.id.recycler_three);
        recyclerThree.setLayoutManager(new GridLayoutManager(getActivity(), 12));
        showGridAdapter2 = new ShowGridAdapter2(R.layout.grid_view_layout2, homeEntity.getData().getMiaosha().getList());
        recyclerThree.setAdapter(showGridAdapter2);

        showAdapter.addHeaderView(inflate2);

    }

    @Override
    public void onFailure(String Msg) {

        //Toast.makeText(mActivity, "homeEntity:" + Msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void loadBanner(XBanner banner, View view, int position) {
        Glide.with(getActivity()).load(strings.get(position)).into((ImageView) view);
    }

    @OnClick(R.id.zxing)
    public void onViewClicked() {

        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, 100);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
