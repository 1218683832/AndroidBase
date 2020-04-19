package com.mrrun.lib.androidbase.widget.imageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by lipin on 2017/9/26.
 * <b>类功能描述:</b><br>
 * 在图片上面加上灰色蒙板,用于更明显的显示上层文字等.
 * 使用方式:Like ImageView.
 *
 * @author lipin
 * @version 1.0
 */

public class GrayScaleImageView extends AppCompatImageView {

    // 前景色
    private int mForeColor = Color.parseColor("#444444");// 灰色

    private Paint mForePaint;

    public GrayScaleImageView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        initPaint();
    }

    public GrayScaleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void initPaint() {
        mForePaint = new Paint();
        mForePaint.setAntiAlias(true);//  抗锯齿，平滑
        mForePaint.setColor(mForeColor);
        mForePaint.setAlpha(153);// 透明度60%
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            // 给drawable加上了一个颜色过滤
            drawable.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        }
        super.onDraw(canvas);
    }
}
