package me.next.one.home.view;

import me.next.one.home.model.HomeModel;

/**
 * Created by NeXT on 16/9/12.
 */
public interface IHomeView {
    void onLoadDone(boolean result, HomeModel homeModel);
    void onInitializeDone(boolean result, HomeModel homeModel);
}
