package me.next.one.home.presenter;

import me.next.one.OneFactory;
import me.next.one.home.model.HomeModel;
import me.next.one.home.view.IHomeView;
import me.next.one.network.OneResultFunc;
import me.next.one.utils.TimeUtils;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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
        String dateString = TimeUtils.getDateStringBeforeDays(daysBeforeToday);
        OneFactory.getOneServiceSingleton().getHomeModel(dateString, "1")
                .map(new OneResultFunc())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeModel>() {
                    @Override
                    public void onNext(HomeModel homeModel) {
                        finishLoad(true, homeModel, daysBeforeToday);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
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
