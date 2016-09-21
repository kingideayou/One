package me.next.one.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.next.one.OneApplication;

/**
 * Created by NeXT on 16/9/21.
 */

public class ImageLoaderUtils {

    public static void loadImage(String url, ImageView imageView) {
        Glide.with(OneApplication.getOneApplication())
                .load(url).into(imageView);
    }

}
