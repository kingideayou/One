package me.next.one.home.view;

import java.util.List;

import me.next.one.home.model.HomeModel;

/**
 * Created by NeXT on 16/9/12.
 */
public interface IHomeView {
    void onLoadDone(boolean result, List<HomeModel> homeModels);
}
