package com.mrrun.lib.androidbase.widget.bottomtabview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrrun.lib.androidbase.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 仿腾讯视频底部TabView栏Tab项切换View
 * <p>
 * <!-- 配置的Item数据xml资源id -->
 * <attr name="items_xml" format="reference" />
 * <!-- item文字默认颜色 -->
 * <attr name="item_text_color_normal" format="color|reference" />
 * <!-- item文字选择时颜色 -->
 * <attr name="item_text_color_selected" format="color|reference" />
 * <!-- item文字大小 -->
 * <attr name="item_text_size" format="dimension|reference" />
 * <!-- item icon宽 -->
 * <attr name="item_icon_width" format="dimension|reference" />
 * <!-- item icon高 -->
 * <attr name="item_icon_height" format="dimension|reference" />
 * <!-- item icon距离顶部距离 -->
 * <attr name="item_icon_magre_top" format="dimension|reference" />
 * <!-- item icon距离底部文字距离 -->
 * <attr name="item_icon_magre_bottom" format="dimension|reference" />
 * <!-- item 文字距离底部距离 -->
 * <attr name="item_text_magre_bottom" format="dimension|reference" />
 *
 * @author lipin
 * @version 1.0
 * @date 2019/03/05
 */
public class BottomTabView extends LinearLayout implements View.OnClickListener {

    private static final String TAG = "BottomTabView";

    private Context mContext;

    List mIconNormalDrawables = new ArrayList(5);
    List mIconSelectedDrawables = new ArrayList(5);
    ArrayList<String> mTexts = new ArrayList<String>(5);

    int size = 0;

    private int mCurrentPosition = 0;
    private View mCurrentView = null;

    /**
     * 配置的Item数据xml资源id 默认-1
     */
    private int mItemXmlResId = -1;
    /**
     * 默认item文字颜色，默认黑色
     */
    private int mItemTextColorNormal = Color.parseColor("#000000");
    private int mItemTextColorSelected = Color.parseColor("#ff954d");
    /**
     * 默认item文字大小，默认10sp
     */
    private float mItemTextSize = 10.0F;
    /**
     * item icon宽 默认21dp
     */
    private float mITtemIconWidth = 21.0F;
    /**
     * item icon高 默认21dp
     */
    private float mITtemIconHeight = 21.0F;
    /**
     * item icon距离顶部距离 默认5dp
     */
    private float mItemIconMagreTop = 5.0F;
    /**
     * item 文字距离底部距离 默认2dp
     */
    private float mItemTextMagreBottom = 2.0F;
    /**
     * item icon距离文字底部距离 默认5dp
     */
    private float mItemIconMagreBottom = 0.0F;

    OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onClick(View v) {
        Integer i = (Integer) v.getTag();
        if (i == mCurrentPosition) {// 点击的是同一个Item， 不做处理
            // Empty
        } else {
            changeItemView(mCurrentView, (Drawable) mIconNormalDrawables.get(mCurrentPosition), mItemTextColorNormal);// 将点击之前的ItemView改变回来
            changeItemView(v, (Drawable) mIconSelectedDrawables.get(i), mItemTextColorSelected);// 将点击ItemView改变

            mCurrentPosition = i;
            mCurrentView = v;

            if (null != mItemClickListener) {
                mItemClickListener.onItemClick(v, i);
            }
        }
    }

    /**
     * 改变ItemView
     *
     * @param view
     * @param drawable
     * @param color
     */
    private void changeItemView(View view, Drawable drawable, int color) {
        ((TextView) view).setCompoundDrawables(null, drawable, null, null);
        ((TextView) view).setTextColor(color);
    }

    static class AttributeSets {
        public static final String TAG_ITEMS = "items";
        public static final String TAG_ITEM = "item";
        public static final String ATTRS_SUBJECT = "subject";
        public static final String ATTRS_ICON_NORMAL = "icon_normal";
        public static final String ATTRS_ICON_SELECTED = "icon_selected";
    }

    public BottomTabView(Context context) {
        this(context, null);
    }

    public BottomTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BottomTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initData();
        initAttrs(attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        loadXml(mItemXmlResId);
        for (int i = 0; i < size; i++) {
            dealDrawable((Drawable) mIconNormalDrawables.get(i));
            dealDrawable((Drawable) mIconSelectedDrawables.get(i));
            View view = creatItemView(i);
            if (mCurrentPosition == i) {
                changeItemView(view, (Drawable) mIconSelectedDrawables.get(i), mItemTextColorSelected);// 将默认ItemView改变
                mCurrentView = view;
            }
        }
    }

    private void initData() {
        mITtemIconWidth = dip2px(mITtemIconWidth);
        mITtemIconHeight = dip2px(mITtemIconHeight);
        mItemTextSize = sp2px(mItemTextSize);
        mItemIconMagreTop = dip2px(mItemIconMagreTop);
        mItemTextMagreBottom = dip2px(mItemTextMagreBottom);
        mItemIconMagreBottom = dip2px(mItemIconMagreBottom);
        mCurrentPosition = 0;// 默认选择第一个Item
    }

    private View creatItemView(int index) {
        TextView itemView = new TextView(mContext);

        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
        params.gravity = Gravity.CENTER;// 整个View居中

        itemView.setCompoundDrawables(null, (Drawable) mIconNormalDrawables.get(index), null, null);
        itemView.setCompoundDrawablePadding((int) mItemIconMagreBottom);
        itemView.setGravity(Gravity.CENTER);// 文字居中

        itemView.setTextColor(mItemTextColorNormal);
        itemView.setTextSize(mItemTextSize);
        itemView.setText(mTexts.get(index));
        itemView.setPadding(0, (int) mItemIconMagreTop, 0, (int) mItemTextMagreBottom);
        itemView.setLayoutParams(params);
        itemView.setTag(index);
        itemView.setOnClickListener(this);

        addView(itemView);
        return itemView;
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    private void dealDrawable(Drawable drawable) {
        float w = drawable.getIntrinsicWidth();
        float h = drawable.getIntrinsicHeight();
        float p = (w / h);
        Log.d(TAG, "宽高因子p:---" + p);
        // 调整icon显示大小
        if (w > h) {
            w = mITtemIconWidth;
            h = w / p;
        } else {
            h = mITtemIconHeight;
            w = h * p;
        }
        drawable.setBounds(0, 0, (int) w, (int) h);  // 设置图片参数
    }

    /**
     * 加载Item xml
     *
     * @param xmlId
     */
    private void loadXml(int xmlId) {
        if (xmlId == -1) {
            throw new RuntimeException("xml id 未指定");
        }
        final Resources res = getContext().getResources();
        final XmlResourceParser parser = res.getXml(xmlId);

        String iconStr;
        int id;
        try {
            while (true) {
                switch (parser.next()) {
                    case XmlPullParser.START_DOCUMENT: {
                        Log.d(TAG, "Xml 解析开始");
                        break;
                    }
                    case XmlPullParser.START_TAG: {
                        Log.d(TAG, "当前标签Start:---" + parser.getName());
                        if (AttributeSets.TAG_ITEMS.equals(parser.getName())) {
                            Log.d(TAG, "parser: " + parser.getName());
                        } else if (AttributeSets.TAG_ITEM.equals(parser.getName())) {
                            Log.d(TAG, "parser: " + parser.getName() + "，" + parser.getAttributeValue(null, AttributeSets.ATTRS_SUBJECT) +
                                    "，" + parser.getAttributeValue(null, AttributeSets.ATTRS_ICON_NORMAL) + "，"
                                    + parser.getAttributeValue(null, AttributeSets.ATTRS_ICON_SELECTED));

                            size++;

                            mTexts.add(parser.getAttributeValue(null, AttributeSets.ATTRS_SUBJECT));

                            iconStr = parser.getAttributeValue(null, AttributeSets.ATTRS_ICON_NORMAL);
                            id = Integer.valueOf(parser.getAttributeValue(null, AttributeSets.ATTRS_ICON_NORMAL).substring(1, iconStr.length()));
                            Drawable normalDrawable = res.getDrawable(id);
                            Log.d(TAG, "normalDrawable: " + normalDrawable);
                            mIconNormalDrawables.add(normalDrawable);

                            iconStr = parser.getAttributeValue(null, AttributeSets.ATTRS_ICON_SELECTED);
                            id = Integer.valueOf(parser.getAttributeValue(null, AttributeSets.ATTRS_ICON_SELECTED).substring(1, iconStr.length()));
                            Drawable selectedDrawable = res.getDrawable(id);
                            Log.d(TAG, "selectedDrawable: " + selectedDrawable);
                            mIconSelectedDrawables.add(selectedDrawable);
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        Log.d(TAG, "当前标签End:---" + parser.getName());
                        break;
                    }
                    case XmlPullParser.END_DOCUMENT: {
                        Log.d(TAG, "Xml 解析结束");
                        return;
                    }
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            parser.close();
        }
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.BottomTabView);
        mItemXmlResId = typedArray.getResourceId(R.styleable.BottomTabView_item_xml, mItemXmlResId);
        mItemIconMagreTop = typedArray.getDimension(R.styleable.BottomTabView_item_icon_magre_top, mItemIconMagreTop);
        mItemTextMagreBottom = typedArray.getDimension(R.styleable.BottomTabView_item_text_magre_bottom, mItemTextMagreBottom);
        mItemIconMagreBottom = typedArray.getDimension(R.styleable.BottomTabView_item_icon_magre_bottom, mItemIconMagreBottom);
        mITtemIconWidth = typedArray.getDimension(R.styleable.BottomTabView_item_icon_width, mITtemIconWidth);
        mITtemIconHeight = typedArray.getDimension(R.styleable.BottomTabView_item_icon_height, mITtemIconHeight);
        mItemTextColorNormal = typedArray.getColor(R.styleable.BottomTabView_item_text_color_normal, mItemTextColorNormal);
        mItemTextColorSelected = typedArray.getColor(R.styleable.BottomTabView_item_text_color_selected, mItemTextColorSelected);
        mItemTextSize = typedArray.getDimension(R.styleable.BottomTabView_item_text_size, mItemTextSize);
        mItemTextSize = px2sp(mItemTextSize);
        typedArray.recycle();
    }

    float px2sp(float pxValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (pxValue / fontScale + 0.5f);
    }

    float sp2px(float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (spValue * fontScale + 0.5f);
    }

    float dip2px(float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }
}
