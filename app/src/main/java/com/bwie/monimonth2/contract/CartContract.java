package com.bwie.monimonth2.contract;

import com.bwie.monimonth2.entity.HomeEntity;
import com.bwie.monimonth2.entity.ShowCartsBean;
import com.bwie.monimonth2.model.CartModel;
import com.bwie.monimonth2.model.HomeModel;
import com.hao.base.base.mvp.BasePresenter;
import com.hao.base.base.mvp.IBaseModel;
import com.hao.base.base.mvp.IBaseView;

import io.reactivex.Observable;

public class CartContract {

    public static abstract class CartPresenter extends BasePresenter<CartContract.ICartModel,CartContract.ICartView> {


        @Override
        public CartContract.ICartModel getmModel() {
            return new CartModel();
        }

        public abstract void getCart(String uid);
    }

    public interface ICartModel extends IBaseModel {

        Observable<ShowCartsBean> getCart(String uid);

    }

    public interface ICartView extends IBaseView {

        void onSuccess(ShowCartsBean showCartsBean);
        void onFailure(String Msg);

    }

}
