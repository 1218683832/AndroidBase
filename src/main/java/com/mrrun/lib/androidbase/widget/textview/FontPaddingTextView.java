package com.mrrun.lib.androidbase.widget.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * <b>问题:</b><br>
 * 使用textview时发现在使用Android:layout_height="wrap_content"这个属性设置后，textview会有默认的padding，也就是fontpadding。
 * 这样就会造成textview和其他view中间的间距会比自己的设置的大。我们需要remove掉这个间距。
 *
 * <b>类功能描述:</b><br>
 * TextView去掉自适应默认的fontpadding,解决TextView顶部留白问题
 * 1、使用该FontPaddingTextView取代TextView
 * 2、xml设置includefontpadding=false
 *
 * Created by lipin on 2017/8/6.
 * @author lipin
 * @version 1.0
 */

@SuppressLint("AppCompatCustomView")
public class FontPaddingTextView extends TextView {

    Paint.FontMetricsInt fontMetricsInt;

    /**
     * 是否remove间距，true为remove
     */
    private boolean adjustTopForAscent = false;

    public FontPaddingTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        adjustTopForAscent = true;
    }

    public FontPaddingTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (adjustTopForAscent){
            if (fontMetricsInt == null){
                fontMetricsInt = new Paint.FontMetricsInt();
                getPaint().getFontMetricsInt(fontMetricsInt);
            }
            canvas.translate(0, fontMetricsInt.top - fontMetricsInt.ascent);
        }
        super.onDraw(canvas);
    }
}
