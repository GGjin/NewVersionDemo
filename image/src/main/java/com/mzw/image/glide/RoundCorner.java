package com.mzw.image.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Create by GG on 2019/2/20
 * mail is gg.jin.yu@gmail.com
 */
public class RoundCorner extends BitmapTransformation {


    private Float leftTop = 0F;
    private Float rightTop = 0F;
    private Float leftBottom = 0F;
    private Float rightBottom = 0F;

    private String id = "com.mzw.image.glide" +
            ".RoundCorner$leftTop$rightTop$leftBottom$rightBottom";

    private byte[] ID_BYTES;

    public RoundCorner(Context context, Float leftTop, Float rightTop, Float leftBottom,
                       Float rightBottom) {
        this.leftTop = dip2px(context, leftTop);
        this.rightTop = dip2px(context, rightTop);
        this.leftBottom = dip2px(context, leftBottom);
        this.rightBottom = dip2px(context, rightBottom);
        ID_BYTES = id.getBytes(Key.CHARSET);
    }

    private Float dip2px(Context context, Float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics());
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setHasAlpha(true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(toTransform, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP));
        RectF rectF = new RectF(0f, 0f, width + 0f, height + 0f);
        float[] radii = {leftTop, leftTop, rightTop, rightTop, rightBottom, rightBottom, leftBottom, leftBottom};
        Path path = new Path();
        path.addRoundRect(rectF, radii, Path.Direction.CW);
        canvas.drawPath(path, paint);
        return bitmap;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof RoundCorner) {
            return leftTop == ((RoundCorner) obj).leftTop && rightTop == ((RoundCorner) obj).rightTop && leftBottom == ((RoundCorner) obj).leftBottom && rightBottom == ((RoundCorner) obj).rightBottom;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode() + leftTop.hashCode() + rightTop.hashCode() + leftBottom.hashCode() + rightBottom.hashCode();
    }


    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
