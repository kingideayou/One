package me.next.one.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by NeXT on 16/9/13.
 */
public class ToastUtils {

    public static void show(Context context, int resId) {
        show(context, context.getString(resId));
    }

    public static void show(Context context, CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
