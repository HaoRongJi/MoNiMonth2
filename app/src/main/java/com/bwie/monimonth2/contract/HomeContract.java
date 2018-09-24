package com.bwie.monimonth2.contract;

import com.bwie.monimonth2.entity.HomeEntity;
import com.bwie.monimonth2.model.HomeModel;
import com.hao.base.base.mvp.BasePresenter;
import com.hao.base.base.mvp.IBaseModel;
import com.hao.base.base.mvp.IBaseView;

import io.reactivex.Observable;

public class HomeContract {

    public static abstract class HomePresenter extends BasePresenter<IHomeModel,IHomeView>{


        @Override
        public IHomeModel getmModel() {
            return new HomeModel();
        }

        public abstract void getHome();
    }

    public interface IHomeModel extends IBaseModel{

        Observable<HomeEntity> getHome();

    }

    public interface IHomeView extends IBaseView{

        void onSuccess(HomeEntity homeEntity);
        void onFailure(String Msg);

    }

}
