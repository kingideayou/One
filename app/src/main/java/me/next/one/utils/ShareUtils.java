package me.next.one.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import me.next.one.R;

/**
 * Created by NeXT on 16/9/22.
 */

public class ShareUtils {

    public static void shareImage(Context context, Uri uriToImage, String content) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, context.getResources().getText(R.string.send_to)));
    }

}
