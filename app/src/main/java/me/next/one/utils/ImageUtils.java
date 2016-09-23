package me.next.one.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

/**
 * Created by NeXT on 16/9/22.
 */

public class ImageUtils {

    /**
     * 判断 Bitmap 为亮色 or 暗色
     * 只选取 Bitmap 下 1/3 部分
     * @param bitmap
     * @return
     */
    public static boolean isDark(Bitmap bitmap) {
        boolean dark = false;

        float darkThreshold = bitmap.getWidth() * bitmap.getHeight() * 0.45f * 0.333333f;
        int darkPixels = 0;

        int[] pixels = new int[(int)(bitmap.getWidth() * bitmap.getHeight() * 0.333333)];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, (int)(bitmap.getHeight() * 0.6666667), bitmap.getWidth(), (int)(bitmap.getHeight() * 0.333333));

        for (int color : pixels) {
            int r = Color.red(color);
            int g = Color.green(color);
            int b = Color.blue(color);
            double luminance = (0.299 * r + 0.0f + 0.587 * g + 0.0f + 0.114 * b + 0.0f);
            if (luminance < 150) {
                darkPixels++;
            }
        }

        if (darkPixels >= darkThreshold) {
            dark = true;
        }
        return dark;
    }

    /**
     * 移除 Bitmap 中透明部分
     * ps: 很耗时...
     * @param sourceBitmap
     * @return
     */
    public static Bitmap cropBitmapTransparency(Bitmap sourceBitmap) {
        int minX = sourceBitmap.getWidth();
        int minY = sourceBitmap.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < sourceBitmap.getHeight(); y++) {
            for (int x = 0; x < sourceBitmap.getWidth(); x++) {
                int alpha = (sourceBitmap.getPixel(x, y) >> 24) & 255;
                // pixel is not 100% transparent
                if (alpha > 0) {
                    if (x < minX)
                        minX = x;
                    if (x > maxX)
                        maxX = x;
                    if (y < minY)
                        minY = y;
                    if (y > maxY)
                        maxY = y;
                }
            }
        }
        if ((maxX < minX) || (maxY < minY))
            return null; // Bitmap is entirely transparent

        // crop bitmap to non-transparent area and return:
        return Bitmap.createBitmap(sourceBitmap, minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);
    }


    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width , height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

}
