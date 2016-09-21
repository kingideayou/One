package me.next.one.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by NeXT on 16/9/21.
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        initView(view, savedInstanceState);
        initData();
        return view;
    }

    protected abstract int getLayoutResId();

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract void initData();

}
