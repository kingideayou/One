package me.next.one.home.view;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.next.one.R;
import me.next.one.home.model.HomeModel;
import me.next.one.utils.ImageUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class HomeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_HOME_MODEL = "home_model";
    public static final String EXTRA_IMAGE_WIDTH = "image_width";
    public static final String EXTRA_IMAGE_HEIGHT = "image_height";

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.strHpTitle)
    TextView strHpTitle;
    @BindView(R.id.strAuthor)
    TextView strAuthor;
    @BindView(R.id.strContent)
    TextView strContent;

    @OnClick(R.id.rl_root)
    void finishActivity() {
        HomeDetailActivity.super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // set an enter transition
            getWindow().setEnterTransition(new Fade());
            // set an exit transition
            getWindow().setExitTransition(new Fade());
        }
        setContentView(R.layout.activity_home_detail);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //暂时阻止启动共享元素 Transition
            postponeEnterTransition();
        }

        HomeModel homeModel = getIntent().getParcelableExtra(EXTRA_HOME_MODEL);
        initView(homeModel);
    }

    private void initView(HomeModel homeModel) {

        int height = getIntent().getIntExtra(EXTRA_IMAGE_HEIGHT, 0);
        int width = getIntent().getIntExtra(EXTRA_IMAGE_WIDTH, 0);

        strAuthor.setText(homeModel.getStrAuthor());
        strContent.setText(homeModel.getStrContent());
        strHpTitle.setText(homeModel.getStrHpTitle());

        try {
            Glide.with(this)
                    .load(homeModel.getStrThumbnailUrl())
                     .asBitmap().listener(new RequestListener<String, Bitmap>() {
                         @Override
                         public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                             startTransition();
                             return false;
                         }

                         @Override
                         public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                             isBitmapDark(resource);
                             imageView.setImageBitmap(resource);
                             startTransition();
                             return false;
                         }
                     }).into(width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTextColor(boolean dark) {
        if (dark) {
            strAuthor.setTextColor(getResources().getColor(R.color.white));
            strAuthor.setShadowLayer(2, 2, 2, R.color.black);
            strHpTitle.setTextColor(getResources().getColor(R.color.white));
            strHpTitle.setShadowLayer(2, 2, 2, R.color.black);
            strContent.setTextColor(getResources().getColor(R.color.white));
            strContent.setShadowLayer(2, 2, 2, R.color.black);
        } else {
            strAuthor.setTextColor(getResources().getColor(R.color.black));
            strHpTitle.setTextColor(getResources().getColor(R.color.black));
            strContent.setTextColor(getResources().getColor(R.color.black));
        }
    }

    public void isBitmapDark(final Bitmap bitmap) {

        Observable.just("")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String str) {
                        return ImageUtils.isDark(bitmap);
                    }
                })
                /*
                .map(new Func1<Boolean, List<Integer>>() {
                    @Override
                    public List<Integer> call(Boolean isDark) {
                        List<Integer> colorList = new ArrayList<>();
                        Palette p = Palette.from(bitmap).generate();
                        colorList.add(isDark ?
                                p.getDarkMutedColor(getResources().getColor(R.color.black)) :
                                p.getLightMutedColor(getResources().getColor(R.color.white)));
                        return colorList;
                    }
                })
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        updateTextColor(integers);
                    }
                });
                */
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean isDark) {
                        updateTextColor(isDark);
                    }
                });
    }

    private void startTransition() {
        //启动共享元素 Transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startPostponedEnterTransition();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
