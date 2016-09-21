package me.next.one.home.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.next.one.R;
import me.next.one.base.BaseFragment;
import me.next.one.home.model.HomeModel;
import me.next.one.home.presenter.HomeDataPresenter;


public class HomeFragment extends BaseFragment implements IHomeView {

    private boolean isLoding = false;

    private int recyclerViewSize = 0;
    private int currentVisibleItemPosition = 0;

    private RecyclerView mRecyclerView;
    private HomeCardAdapter mHomeCardAdapter;
    private HomeDataPresenter mHomeDataPresenter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstance) {

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                view.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        LinearSnapHelper snapHelper = new LinearSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                View centerView = findSnapView(layoutManager);
                if (centerView == null) {
                    return RecyclerView.NO_POSITION;
                }
                int position = layoutManager.getPosition(centerView);
                int targetPosition = -1;
                if (layoutManager.canScrollHorizontally()) {
                    if (velocityX < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }
                if (layoutManager.canScrollVertically()) {
                    if (velocityY < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }
                final int firstItem = 0;
                final int lastItem = layoutManager.getItemCount() - 1;
                targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem));
                return targetPosition;
            }
        };
        snapHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!isLoding &&  RecyclerView.SCROLL_STATE_IDLE == newState) {
                    recyclerViewSize = linearLayoutManager.getItemCount();
                    currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    if (recyclerViewSize - currentVisibleItemPosition >= 1) {
                        isLoding = true;
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        mHomeDataPresenter = new HomeDataPresenter(this);
        mHomeDataPresenter.initDatas();
        mHomeCardAdapter = new HomeCardAdapter();
        mRecyclerView.setAdapter(mHomeCardAdapter);
    }

    @Override
    public void onLoadDone(boolean result, HomeModel homeModel) {
        if (result) {
            mHomeCardAdapter.appendData(homeModel);
        }
    }

    @Override
    public void onInitializeDone(boolean result, HomeModel homeModel) {
        if (result) {
            mHomeCardAdapter.initData(homeModel);
            mHomeDataPresenter.loadDatas(1);
        }
    }

}
