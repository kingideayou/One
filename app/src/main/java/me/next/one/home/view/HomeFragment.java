package me.next.one.home.view;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import me.next.one.OneApplication;
import me.next.one.R;
import me.next.one.base.BaseFragment;
import me.next.one.home.model.HomeModel;
import me.next.one.home.presenter.HomeCardAdapter;
import me.next.one.home.presenter.HomeDataPresenter;
import me.next.one.utils.CapturePhotoUtils;
import me.next.one.utils.ShareUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class HomeFragment extends BaseFragment implements IHomeView {

    @BindView(R.id.recycler_view)
    public RecyclerView mRecyclerView;

    private boolean isLoding = false;

    private static final int INIT_DATA_SIZE = 3;
    private int recyclerViewSize = 0;
    private int currentVisibleItemPosition = 0;
    private HomeCardAdapter mHomeCardAdapter;
    private HomeDataPresenter mHomeDataPresenter;
    private LinearLayoutManager linearLayoutManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        linearLayoutManager = new LinearLayoutManager(
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
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //滑动时进行预加载
                if (!isLoding) {
                    recyclerViewSize = linearLayoutManager.getItemCount();
                    currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    if (recyclerViewSize < currentVisibleItemPosition + 2) {
                        isLoding = true;
                        mHomeDataPresenter.loadDatas(recyclerViewSize > INIT_DATA_SIZE ? recyclerViewSize : INIT_DATA_SIZE);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                int currentVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
                View currentChildView = mRecyclerView.findViewHolderForAdapterPosition(currentVisiblePosition).itemView;
                saveAndShareImage(currentVisiblePosition, currentChildView);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initData() {
        mHomeDataPresenter = new HomeDataPresenter(this);
        mHomeDataPresenter.initDatas();
        mHomeCardAdapter = new HomeCardAdapter(getActivity());
        mRecyclerView.setAdapter(mHomeCardAdapter);
    }

    @Override
    public void onLoadDone(boolean result, HomeModel homeModel) {
        if (result) {
            mHomeCardAdapter.appendData(homeModel);
        }
        isLoding = false;
    }

    @Override
    public void onInitializeDone(boolean result, HomeModel homeModel) {
        if (result) {
            mHomeCardAdapter.initData(homeModel);
            mHomeDataPresenter.loadDatas(1);
            mHomeDataPresenter.loadDatas(2);
        }
    }

    /**
     * 截取当前 ViewHolder 的图片并保存到本地
     * @param currentChildView
     */
    private void saveAndShareImage(final int position, final View currentChildView) {
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "分享图片", "ヽ(✿ﾟ▽ﾟ)ノ 获取图片...", true);
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        currentChildView.setDrawingCacheEnabled(true);
                        currentChildView.buildDrawingCache();

                        String imagePath = CapturePhotoUtils.insertImage(
                                OneApplication.getOneApplication().getContentResolver(),
                                currentChildView.getDrawingCache(),
//                                ImageUtils.cropBitmapTransparency(currentChildView.getDrawingCache()),
                                "title", "desc...");
                        currentChildView.setDrawingCacheEnabled(false);
                        currentChildView.destroyDrawingCache();

                        return imagePath;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String imagePath) {
                        ShareUtils.shareImage(getContext(), Uri.parse(imagePath));
                        progressDialog.dismiss();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        progressDialog.dismiss();
                    }
                });

    }
}
