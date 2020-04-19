package com.mrrun.lib.androidbase.widget.textview;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 可设置多个关键字高亮变色下划线点击事件
 */
public class TextViewClickSpan extends ClickableSpan {

    private String mKeyWord; // 关键字
    private int mHighLightColor = Color.RED; // 部分文字高亮
    private boolean mUnderLine = false;// 该部分文字下划线
    private View.OnClickListener mClickListener;// 该部分文字可以响应点击事件

    public TextViewClickSpan(View.OnClickListener listener, String keyWord) {
        this.mKeyWord = keyWord;
        this.mClickListener = listener;
    }

    public TextViewClickSpan(View.OnClickListener listener, String keyWord, int highLightColor, boolean underline) {
        this(listener, keyWord);
        this.mHighLightColor = highLightColor;
        this.mUnderLine = underline;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        // 设置文本的颜色
        ds.setColor(mHighLightColor);
        // 超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
        ds.setUnderlineText(mUnderLine);
    }

    @Override
    public void onClick(View widget) {
        if (null != mClickListener) {
            mClickListener.onClick(widget);
        }
    }

    public String getKeyWord() {
        return mKeyWord;
    }

    /**
     * 可设置多个关键字高亮变色下划线点击事件
     *
     * @param tv
     * @param centent
     * @param clickSpans
     */
    public static void setTextHighLightWithClick(@Nullable TextView tv, @Nullable String centent,
                                                 @Nullable TextViewClickSpan[] clickSpans) throws Exception {
        tv.setClickable(true);
        tv.setHighlightColor(Color.TRANSPARENT);//设置点击后的颜色为透明
        tv.setMovementMethod(LinkMovementMethod.getInstance());//不设置 没有点击事件
        SpannableString s = new SpannableString(centent);
        if (clickSpans.length <= 0) {
            return;
        }
        for (TextViewClickSpan clickSpan : clickSpans) {
            Pattern p = Pattern.compile(clickSpan.getKeyWord());
            Matcher m = p.matcher(s);
            while (m.find()) {
                try {
                    int start = m.start();
                    int end = m.end();
                    s.setSpan(new TextViewClickSpan(clickSpan.mClickListener, clickSpan.mKeyWord, clickSpan.mHighLightColor, clickSpan.mUnderLine),
                            start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }
        }
        tv.setText(s);
    }
}
