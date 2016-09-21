package me.next.one.home.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import me.next.one.R;
import me.next.one.home.model.HomeModel;
import me.next.one.utils.AppLogger;
import me.next.one.utils.ImageLoaderUtils;
import me.next.one.utils.ToastUtils;

import static me.next.one.R.id.imageView;

class HomeCardAdapter extends RecyclerView.Adapter<HomeCardAdapter.ViewHolder> {

    private List<HomeModel> mHomeModels = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_home, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        AppLogger.d(mHomeModels.get(position).getStrThumbnailUrl());
        ImageLoaderUtils.loadImage(mHomeModels.get(position).getStrThumbnailUrl(), holder.mImageView);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show(holder.mImageView.getContext(), "" + holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHomeModels.size();
    }

    public List<HomeModel> getHomeModels() {
        return mHomeModels;
    }

    void initData(HomeModel homeModel) {
        getHomeModels().add(homeModel);
        notifyDataSetChanged();
    }

    void appendData(HomeModel homeModel) {
        getHomeModels().add(homeModel);
        notifyItemInserted(mHomeModels.size() - 1);
    }

    public void setHomeModels(List<HomeModel> homeModels) {
        mHomeModels = homeModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(imageView);
        }

    }

}
