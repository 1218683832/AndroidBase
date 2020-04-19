package com.mrrun.lib.androidbase.widget.imageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.mrrun.lib.androidbase.R;

/**
 * Created by lipin on 2017/8/21.
 * <b>类功能描述:</b><br>
 * 实现圆角ImageView
 * 可设置上下左右圆角值的ImageView
 *
 * @author lipin
 * @version 1.0
 */

@SuppressLint("AppCompatCustomView")
public class RoundAngleImageView extends ImageView {

    private Context mContext;
    /**
     * 图形控件的宽、高
     */
    float mImageViewWidth, mImageViewHeight;
    /**
     * 图形控件左下角圆角数值
     */
    private int mBottomLeftRadius = 0;
    /**
     * 图形控件右下角圆角数值
     */
    private int mBottomRightRadius = 0;
    /**
     * 图形控件左上角圆角数值
     */
    private int mTopLeftRadius = 0;
    /**
     * 图形控件右上角圆角数值
     */
    private int mTopRightRadius = 0;
    /**
     * 图形控件圆角数值
     */
    private int mRadius = DEF_RADIUS;
    /**
     * 图形控件圆角默认数值
     */
    private static final int DEF_RADIUS = 10;

    public RoundAngleImageView(Context context) {
        super(context);
        mContext = context;
        init(null);
    }

    public RoundAngleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        initCustomAttributes(attrs);
    }

    private void initCustomAttributes(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.RoundAngleImageView);
            mRadius = typedArray.getDimensionPixelSize(R.styleable.RoundAngleImageView_radius, 0);
            if (mRadius > 0) {
                mBottomLeftRadius = mBottomRightRadius = mTopRightRadius = mTopLeftRadius = mRadius;
            } else {
                mBottomLeftRadius = typedArray.getDimensionPixelSize(R.styleable.RoundAngleImageView_bottomLeftRadius, 0);
                mBottomRightRadius = typedArray.getDimensionPixelSize(R.styleable.RoundAngleImageView_bottomRightRadius, 0);
                mTopLeftRadius = typedArray.getDimensionPixelSize(R.styleable.RoundAngleImageView_topLeftRadius, 0);
                mTopRightRadius = typedArray.getDimensionPixelSize(R.styleable.RoundAngleImageView_topRightRadius, 0);
                mBottomLeftRadius = typedArray.getDimensionPixelSize(R.styleable.RoundAngleImageView_bottomLeftRadius, 0);
            }
            typedArray.recycle();
        } else {
            float density = mContext.getResources().getDisplayMetrics().density;
            mRadius = (int) (mRadius * density);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mImageViewWidth = getWidth();
        mImageViewHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        onDrawRoundAngle(canvas);
        super.onDraw(canvas);
    }

    private void onDrawRoundAngle(Canvas canvas) {
        Path path = new Path();
        path.moveTo(mTopLeftRadius, 0);
        path.lineTo(mImageViewWidth - mTopRightRadius, 0);
        path.quadTo(mImageViewWidth, 0, mImageViewWidth, mTopRightRadius);
        path.lineTo(mImageViewWidth, mImageViewHeight - mTopLeftRadius);
        path.quadTo(mImageViewWidth, mImageViewHeight, mImageViewWidth - mTopRightRadius, mImageViewHeight);
        path.lineTo(mBottomLeftRadius, mImageViewHeight);
        path.quadTo(0, mImageViewHeight, 0, mImageViewHeight - mTopLeftRadius);
        path.lineTo(0, mTopLeftRadius);
        path.quadTo(0, 0, mTopLeftRadius, 0);
        canvas.clipPath(path);
    }
}
