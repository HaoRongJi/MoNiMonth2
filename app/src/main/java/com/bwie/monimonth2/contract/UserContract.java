package com.bwie.monimonth2.contract;

import com.bwie.monimonth2.entity.HeadEntity;
import com.bwie.monimonth2.entity.ShowCartsBean;
import com.bwie.monimonth2.entity.UserEntity;
import com.bwie.monimonth2.model.CartModel;
import com.bwie.monimonth2.model.UserModel;
import com.hao.base.base.mvp.BasePresenter;
import com.hao.base.base.mvp.IBaseModel;
import com.hao.base.base.mvp.IBaseView;

import java.io.File;

import io.reactivex.Observable;

public class UserContract {


    public static abstract class UserPresenter extends BasePresenter<UserContract.IUserModel,UserContract.IUserView> {


        @Override
        public UserContract.IUserModel getmModel() {
            return new UserModel();
        }

        public abstract void getUser(String uid);

        public abstract void upLoadHead(File headfile,String uid);
    }

    public interface IUserModel extends IBaseModel {

        Observable<UserEntity> getUser(String uid);

        Observable<HeadEntity> upLoadHead(File headfile,String uid);
    }

    public interface IUserView extends IBaseView {

        void onSuccess(UserEntity userEntity);
        void onFailure(String Msg);
        void onUpSuccess(HeadEntity headEntity);

    }

}
