package com.mrrun.lib.androidbase.base.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.mrrun.lib.androidbase.R;

/**
 * Created by lipin on 2017/8/29.
 * b>类功能描述:</b><br>
 * 支持xml配置布局类型、布局方向、布局列数，无需在代码中编写.
 *
 * @author lipin
 * @version 1.0
 */

public class EasyRecyclerView extends RecyclerView {

    // 布局类型
    public static final int TYPE_LINEAE = 0;
    public static final int TYPE_GRID = 1;
    public static final int TYPE_STAGGER = 2;
    // 布局方向
    public static final int ORIENTATION_HORIZONTAL = OrientationHelper.HORIZONTAL;
    public static final int ORIENTATION_VERTICAL = OrientationHelper.VERTICAL;
    // 布局列数
    public static final int DEF_COLUMN_NUM = 1;
    // 分割线高或宽大小
    public static final float DEF_DIVIDER_SIZE = 0f;
    // 分割线颜色
    public static final int DEF_DIVIDER_COLOR = Color.BLACK;


    // 自定义分割线颜色
    private int mDividerColor = DEF_DIVIDER_COLOR;
    // 自定义分割线Drawable
    private Drawable mDividerDrawable;
    // 分割线高或宽大小
    private float mDividerSize = DEF_DIVIDER_SIZE;
    // 默认线性类型
    private int mLayType = TYPE_LINEAE;
    // 默认方向
    private int mOrientation = ORIENTATION_VERTICAL;
    // 默认列数
    private int mColumnNum = DEF_COLUMN_NUM;

    private Context mContext;
    private LayoutManager mLayoutManager ;
    // 线性布局使用的分割线
    private LinearDividerItemDecoration mLinearDividerItemDecoration;

    public EasyRecyclerView(@NonNull Context context) {
        super(context);
        this.mContext = context;
        init(null);
    }

    public EasyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    public EasyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initCustomAttributes(attrs);
        initView();
    }

    private void initView() {
        initRecyclerView();
    }

    /**
     * 获取自定义属性
     *
     * @param attrs
     */
    private void initCustomAttributes(AttributeSet attrs) {
        if (attrs != null){
            TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.EasyRecyclerView);
            mLayType = typedArray.getInt(R.styleable.EasyRecyclerView_recycle_layout_type, TYPE_LINEAE);// 默认线性布局
            mOrientation = typedArray.getInt(R.styleable.EasyRecyclerView_recycle_orientation, ORIENTATION_VERTICAL);// 默认垂直方向
            mColumnNum = typedArray.getInt(R.styleable.EasyRecyclerView_recycle_columnNum, DEF_COLUMN_NUM);// 默认布局列数1
            if (mColumnNum < DEF_COLUMN_NUM) {
                mColumnNum = DEF_COLUMN_NUM;
            }
            mDividerSize = typedArray.getDimension(R.styleable.EasyRecyclerView_recycle_dividerSize, DEF_DIVIDER_SIZE);// 默认分割线大小
            mDividerColor = typedArray.getColor(R.styleable.EasyRecyclerView_recycle_dividerColor, DEF_DIVIDER_COLOR);// 默认分割线颜色
            mDividerDrawable = typedArray.getDrawable(R.styleable.EasyRecyclerView_recycle_dividerDrawable);// 分割线Drawable
            /*for (int i = 0; i < typedArray.getIndexCount(); i ++) {
                int attr = typedArray.getIndex(i);
                switch (attr) {
                    case R.styleable.EasyRecycleView_recycle_layout_type:// 布局类型
                        mLayType = typedArray.getInt(R.styleable.EasyRecycleView_recycle_layout_type, TYPE_LINEAE);// 默认线性布局
                        break;
                    case R.styleable.EasyRecycleView_recycle_orientation:// 布局方向
                        mOrientation = typedArray.getInt(R.styleable.EasyRecycleView_recycle_orientation, ORIENTATION_VERTICAL);// 默认垂直方向
                        break;
                    case R.styleable.EasyRecycleView_recycle_columnNum:// 布局列数
                        mColumnNum = typedArray.getInt(R.styleable.EasyRecycleView_recycle_columnNum, DEF_COLUMN_NUM);// 默认布局列数1
                        if (mColumnNum < DEF_COLUMN_NUM) {
                            mColumnNum = DEF_COLUMN_NUM;
                        }
                        break;
                    default:
                        break;
                }
            }*/
            typedArray.recycle();
        }
    }

    private void initRecyclerView() {
        // 1、设置RecyclerView的类型和方向
        setupLayAndOrientation();
        // 2、设置RecyclerView的分割线样式
        setupItemDecoration();
    }

    /**
     * 设置RecyclerView的分割线样式
     */
    private void setupItemDecoration() {
        switch (mLayType) {
            case TYPE_LINEAE:// 线性布局
                this.removeItemDecoration(mLinearDividerItemDecoration);
                mLinearDividerItemDecoration = new LinearDividerItemDecoration(mContext, mOrientation, mDividerSize, mDividerColor, mDividerDrawable);
                this.addItemDecoration(mLinearDividerItemDecoration);
                break;
            case TYPE_GRID:// 网格布局
                break;
            case TYPE_STAGGER:// 瀑布流布局
                break;
            default:
                this.removeItemDecoration(mLinearDividerItemDecoration);
                mLinearDividerItemDecoration = new LinearDividerItemDecoration(mContext, mOrientation, mDividerSize, mDividerColor, mDividerDrawable);
                this.addItemDecoration(mLinearDividerItemDecoration);
                break;
        }
    }

    /**
     * 设置RecyclerView的类型和方向
     */
    private void setupLayAndOrientation() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
        switch (mLayType) {
            case TYPE_LINEAE:// 线性布局
                mLayoutManager = new LinearLayoutManager(this.getContext());
                ((LinearLayoutManager)mLayoutManager).setOrientation(mOrientation);// 布局方向
                break;
            case TYPE_GRID:// 网格布局
                mLayoutManager = new GridLayoutManager(this.getContext(), mColumnNum);
                ((GridLayoutManager)mLayoutManager).setOrientation(mOrientation);// 布局方向
                break;
            case TYPE_STAGGER:// 瀑布流布局
                mLayoutManager = new StaggeredGridLayoutManager(mColumnNum, mOrientation);// 布局方向
                break;
            default:
                mLayoutManager = new LinearLayoutManager(this.getContext());// 线性布局
                ((LinearLayoutManager)mLayoutManager).setOrientation(ORIENTATION_VERTICAL);// 布局方向
                break;
        }
        this.setLayoutManager(mLayoutManager);
    }
}
