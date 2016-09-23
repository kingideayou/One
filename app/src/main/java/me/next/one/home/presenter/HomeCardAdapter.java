package me.next.one.home.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.next.one.R;
import me.next.one.home.model.HomeModel;
import me.next.one.home.view.HomeDetailActivity;
import me.next.one.utils.AppLogger;
import me.next.one.utils.ImageLoaderUtils;

public class HomeCardAdapter extends RecyclerView.Adapter<HomeCardAdapter.HomeViewHolder> {

    private Activity mActivity;

    public HomeCardAdapter(Activity activity) {
        mActivity = activity;
    }

    private List<HomeModel> mHomeModels = new ArrayList<>();

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_home, parent, false);
        return new HomeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final HomeViewHolder holder, int position) {
        AppLogger.d(mHomeModels.get(position).getStrThumbnailUrl());
        HomeModel homeModel = mHomeModels.get(position);
        holder.onBindViewHolder(homeModel);
    }

    @Override
    public int getItemCount() {
        return mHomeModels.size();
    }

    public List<HomeModel> getHomeModels() {
        return mHomeModels;
    }

    public void initData(HomeModel homeModel) {
        getHomeModels().add(homeModel);
        notifyDataSetChanged();
    }

    public void appendData(HomeModel homeModel) {
        getHomeModels().add(homeModel);
        notifyItemInserted(mHomeModels.size() - 1);
    }

    public void setHomeModels(List<HomeModel> homeModels) {
        mHomeModels = homeModels;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.strHpTitle)
        TextView strHpTitle;
        @BindView(R.id.strAuthor)
        TextView strAuthor;
        @BindView(R.id.strContent)
        TextView strContent;

        public HomeViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void onBindViewHolder(final HomeModel homeModel) {
            ImageLoaderUtils.loadImage(homeModel.getStrThumbnailUrl(), imageView);
            strAuthor.setText(homeModel.getStrAuthor());
            strContent.setText(homeModel.getStrContent());
            strHpTitle.setText(homeModel.getStrHpTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mActivity, HomeDetailActivity.class);
                    intent.putExtra(HomeDetailActivity.EXTRA_HOME_MODEL, homeModel);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        int width = imageView.getMeasuredWidth();
                        int height = imageView.getMeasuredHeight();
                        intent.putExtra(HomeDetailActivity.EXTRA_IMAGE_WIDTH, width);
                        intent.putExtra(HomeDetailActivity.EXTRA_IMAGE_HEIGHT, height);
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(mActivity, imageView, "PrimaryImage");
                        mActivity.startActivity(intent, options.toBundle());
                    } else {
                        mActivity.startActivity(intent);
                    }
                }
            });
        }

    }

}
