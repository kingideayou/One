package me.next.one.home.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import me.next.one.R;
import me.next.one.home.model.HomeModel;

public class HomeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_HOME_MODEL = "home_model";
    public static final String EXTRA_IMAGE_WIDTH = "image_width";
    public static final String EXTRA_IMAGE_HEIGHT = "image_height";

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

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        findViewById(R.id.rl_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeDetailActivity.super.onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //暂时阻止启动共享元素 Transition
            postponeEnterTransition();
        }

        HomeModel homeModel = getIntent().getParcelableExtra(EXTRA_HOME_MODEL);
        int height = getIntent().getIntExtra(EXTRA_IMAGE_HEIGHT, 0);
        int width = getIntent().getIntExtra(EXTRA_IMAGE_WIDTH, 0);

        Glide.with(getApplicationContext()).load(homeModel.getStrThumbnailUrl()).override(width, height).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//              //启动共享元素 Transition
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startPostponedEnterTransition();
                }
                return false;
            }
            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                //启动共享元素 Transition
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startPostponedEnterTransition();
                }
                return false;
            }
        }).into(imageView);
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
