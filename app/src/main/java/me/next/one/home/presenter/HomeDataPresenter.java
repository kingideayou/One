package me.next.one.home.presenter;

import me.next.one.constants.OneApi;
import me.next.one.home.model.HomeModel;
import me.next.one.home.view.IHomeView;
import me.next.one.network.OkHttpClientManager;
import me.next.one.utils.AppLogger;
import me.next.one.utils.TimeUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by NeXT on 16/9/20.
 */
public class HomeDataPresenter implements IHomePresenter {

    private IHomeView mIHomeView;

    public HomeDataPresenter(IHomeView IHomeView) {
        mIHomeView = IHomeView;
    }

    @Override
    public void initDatas() {
        loadDatas(0);
    }

    @Override
    public void loadDatas(final int daysBeforeToday) {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, HomeModel>() {
                    @Override
                    public HomeModel call(String s) {
                        String dateString = TimeUtils.getDateStringBeforeDays(daysBeforeToday);
                        AppLogger.e("DateString : " + dateString);
                        OkHttpClientManager.Param[] params = new OkHttpClientManager.Param[2];
                        OkHttpClientManager.Param param1 =
                                new OkHttpClientManager.Param("strDate",
                                        dateString);
                        OkHttpClientManager.Param param2 =
                                new OkHttpClientManager.Param("strRow", "1");
                        params[0] = param1;
                        params[1] = param2;
                        try {
                            return OkHttpClientManager.getDataByPost(OneApi.HOME_API, params, HomeModel.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<HomeModel>() {
                    @Override
                    public void call(HomeModel homeModel) {
                        AppLogger.e("homeModelList : " + homeModel.toString());
                        finishLoad(true, homeModel, daysBeforeToday);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        AppLogger.e("Throwable : " + throwable.toString());
                        finishLoad(false, null, daysBeforeToday);
                    }
                });

    }

    private void finishLoad(boolean result, HomeModel homeModel, int daysBeforeToday) {
        if (mIHomeView != null) {
            if (daysBeforeToday == 0) {
                mIHomeView.onInitializeDone(result, homeModel);
            } else {
                mIHomeView.onLoadDone(result, homeModel);
            }
        }
    }

}
