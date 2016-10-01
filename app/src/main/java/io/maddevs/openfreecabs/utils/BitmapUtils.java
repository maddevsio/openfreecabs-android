package io.maddevs.openfreecabs.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;

/**
 * Created by man on 02.10.16.
 */
public class BitmapUtils {
    public static final int Fill = 0;
    public static final int AspectFit = 1;
    public static final int AspectFill = 2;
    public static final int CenterCrop = 3;

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int newWidth, int newHeight, int scaleType) {
        Bitmap scaledBitmap;
        double wScale = (double) newWidth / (double) bitmap.getWidth();
        double hScale = (double) newHeight / (double) bitmap.getHeight();
        int scaledWidth;
        int scaledHeight;

        switch (scaleType) {
            case Fill:
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
                break;
            case AspectFit:
                scaledWidth = (int)(bitmap.getWidth() * Math.min(wScale, hScale));
                scaledHeight = (int)(bitmap.getHeight() * Math.min(wScale, hScale));
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
                break;
            case AspectFill:
                scaledWidth = (int)(bitmap.getWidth() * Math.max(wScale, hScale));
                scaledHeight = (int)(bitmap.getHeight() * Math.max(wScale, hScale));
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
                break;
            case CenterCrop:
                scaledWidth = (int)(bitmap.getWidth() * Math.max(wScale, hScale));
                scaledHeight = (int)(bitmap.getHeight() * Math.max(wScale, hScale));
                int xOffset = (scaledWidth - newWidth) / 2;
                int yOffset = (scaledHeight - newHeight) / 2;
                scaledBitmap = Bitmap.createBitmap(
                        Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true),
                        xOffset, yOffset, newWidth, newHeight);
                break;
            default:
                scaledBitmap = bitmap;
                break;
        }


        return scaledBitmap;
    }
}
