package com.mrrun.lib.androidbase.widget.imageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by lipin on 2017/7/31.
 * <b>类功能描述:</b><br>
 * 在图片上面画一层带透明度的灰层遮盖层
 * 用于更明显的显示上层文字等。
 * 使用方式:在ImageView上覆盖
 *
 * @author lipin
 * @version 1.0
 */

public class AshCoverImageView extends AppCompatImageView {

    // 前景色
    private int mForeColor = Color.parseColor("#444444");// 灰色

    private Paint mForePaint;

    public AshCoverImageView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        initPaint();
    }

    public AshCoverImageView(Context context, @Nullable AttributeSet attrs) {
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
        onDrawAshLayer(canvas);
    }

    /**
     * <b>方法功能描述:</b><br>
     * 在图片上面画一层灰层
     * @param canvas
     */
    private void onDrawAshLayer(Canvas canvas) {
        int x1 = (int) this.getX();
        int y1 = (int) this.getY();
        int x2 = (int) (this.getX() + this.getMeasuredWidth());
        int y2 = (int) (this.getY() + this.getMeasuredHeight());
        canvas.drawRect(x1, y1, x2, y2, mForePaint);
    }
}
