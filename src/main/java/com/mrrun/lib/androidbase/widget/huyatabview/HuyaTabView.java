package com.mrrun.lib.androidbase.widget.huyatabview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.mrrun.lib.androidbase.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 仿虎牙TabView栏Tab项切换View
 * 设置每个Item之间的间隔px数值{@link #setItemSpace(int)}
 * 设置几个Item数目后可移动位置数目{@link #setFixedItem(int)}
 *
 * <!-- 每个Item之间的间隔 -->
 * <attr name="itemSpace" format="dimension|reference" />
 * <!-- 几个Item数目后可移动位置 -->
 * <attr name="fixedItem" format="integer"/>
 * <!-- 配置的Item数据xml资源id -->
 * <attr name="items_xml" format="reference"/>
 *
 * @author lipin
 * @version 1.0
 * @date 2019/03/04
 */
public class HuyaTabView extends RecyclerView {

    private static final String TAG = "HuyaTabView";

    private Context mContext;

    private LinearLayoutManager mLayoutManager;

    private ItemAdapter mAdapter;

    private HuyaItemDecoration mItemDecoration;

    /**
     * 几个Item数目后可移动位置数目 默认3
     */
    private int mFixedItem = 3;
    /**
     * 每个Item之间的间隔 默认8dip
     */
    private int mItemSpace = 8;
    /**
     * 配置的Item数据xml资源id 默认-1
     */
    private int mItemXmlResId = -1;
    /**
     * 默认item文字颜色，默认黑色
     */
    private int mDefColor = Color.parseColor("#000000");
    private int mDefSelectedColor = Color.parseColor("#ff954d");

    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }

    static class AttributeSets {
        public static final String TAG_ITEMS = "items";
        public static final String TAG_ITEM = "item";
        public static final String ATTRS_SUBJECT = "subject";
    }

    public HuyaTabView(@NonNull Context context) {
        this(context, null);
    }

    public HuyaTabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HuyaTabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        this.mItemDecoration = new HuyaItemDecoration();
        this.mLayoutManager = new LinearLayoutManager(mContext);
        initAttrs(attrs);
        // 设置固定大小
        setHasFixedSize(true);
        this.mLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        setLayoutManager(mLayoutManager);
        this.mAdapter = new ItemAdapter(mContext);
        setItemSpace(mItemSpace);
        setFixedItem(mFixedItem);
        setAdapter(mAdapter);
        loadXml(mItemXmlResId);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.HuyaTabView);
        mFixedItem = typedArray.getInteger(R.styleable.HuyaTabView_fixedItem, mFixedItem);
        mItemSpace = typedArray.getDimensionPixelSize(R.styleable.HuyaTabView_itemSpace, dp2px(mItemSpace));
        mItemXmlResId = typedArray.getResourceId(R.styleable.HuyaTabView_items_xml, mItemXmlResId);
        typedArray.recycle();
    }

    int dp2px(int dipValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 设置几个Item数目后可移动位置数目
     *
     * @param index
     */
    public void setFixedItem(int index) {
        mAdapter.setFixedItem(index);
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mAdapter.mItemClickListener = listener;
    }

    public void setItemSpace(int px) {
        mItemSpace = px;
        this.mItemDecoration.space = mItemSpace;
        addItemDecoration(mItemDecoration);
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
        List datas = mAdapter.getData();
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
                            Log.d(TAG, "parser: " + parser.getName() + "，" + parser.getAttributeValue(null, AttributeSets.ATTRS_SUBJECT));
                            datas.add(parser.getAttributeValue(null, AttributeSets.ATTRS_SUBJECT));
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {
                        Log.d(TAG, "当前标签End:---" + parser.getName());
                        break;
                    }
                    case XmlPullParser.END_DOCUMENT: {
                        Log.d(TAG, "Xml 解析结束");
                        mAdapter.notifyDataSetChanged();
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

    /**
     * Item间隔
     */
    static class HuyaItemDecoration extends RecyclerView.ItemDecoration {

        /**
         * 间隔
         */
        private int space = 18;

        public void setSpace(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
            int itemCount = parent.getAdapter().getItemCount();
            int position = parent.getChildAdapterPosition(view);
            if (0 < position && position < itemCount - 1) {// 中间item
                outRect.left = space;
                outRect.right = space;
            } else if (0 == position) {// 第一个item
                outRect.right = space;
            } else if (position == itemCount - 1) {// 最后一个item
                outRect.left = space;
            }
        }
    }

    /**
     * 适配器
     */
    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

        // 放大动画时长
        private Long mDuration = 100L;

        private float mScale = 1.3f;

        private LayoutInflater mInflater;

        private List<String> mDatas;

        OnItemClickListener mItemClickListener;

        // 当前选择的item
        int mCurrentPosition = 0;
        // 当前选择的item
        private View mCurrentView;
        // 上次选中的Item
        int mPrevPosition = 0;
        // 上次选中的Item
        private View mPrevView;

        // 移动的固定位置
        private int mMoveLeft = -1;
        private int mMoveIndex = 3;

        public ItemAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
            this.mDatas = new ArrayList<>(3);
            setCurrentView(null, 0);
            setPrevView(null, -1);
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
            View itemView = mInflater.inflate(R.layout.lay_huyatabview_item, viewGroup, false);
            ItemViewHolder viewHolder = new ItemViewHolder(itemView);
            return viewHolder;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(@NonNull final ItemViewHolder viewHolder, final int position) {
            Log.d(TAG, "position = " + position);
            Log.d(TAG, "mCurrentPosition = " + mCurrentPosition);
            ((TextView) viewHolder.itemView).setText(mDatas.get(position));
            if (position == mMoveIndex - 1 && mMoveLeft == -1) {// 只赋值一次，移动的距离
                viewHolder.itemView.post(new Runnable() {
                    @Override
                    public void run() {
                        mMoveLeft = viewHolder.itemView.getLeft();
                        Log.d(TAG, "item mMoveLeft = " + mMoveLeft);
                    }
                });
            }
            if (mCurrentPosition == position) {
                setCurrentView(viewHolder.itemView, position);
                changeTextColor(mCurrentView, mDefSelectedColor);
                animateZoomIn(mCurrentView, position);
            } else {
                changeTextColor(viewHolder.itemView, mDefColor);
                animateRestore(viewHolder.itemView, mDuration);
            }
            viewHolder.itemView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != mCurrentView) {
                        setPrevView(mCurrentView, mCurrentPosition);
                        changeTextColor(mPrevView, mDefColor);
                        animateRestore(mPrevView, mDuration);
                    }

                    setCurrentView(viewHolder.itemView, position);

                    Log.d(TAG, "mCurrentView.getLeft() = " + mCurrentView.getLeft());
                    int dx = mCurrentView.getLeft() - mMoveLeft;
                    Log.d(TAG, "dx = " + dx);
                    if (dx > 0) {
                        Log.d(TAG, "左移 dx = " + dx);
                    } else {
                        Log.d(TAG, "右移 dx = " + dx);
                    }
                    smoothScrollBy(dx, 0, new LinearInterpolator());

                    changeTextColor(mCurrentView, mDefSelectedColor);
                    animateZoomIn(mCurrentView, position);

                    if (null != mItemClickListener) {
                        mItemClickListener.onItemClick(v, position);
                    }
                }
            });
        }

        void animateZoomIn(final View view, final int position) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    if (isFirstItem(position)) {
                        animateZoomIn(view, 0, view.getHeight() / 2);
                        Log.d(TAG, "isFirstItem : h=" + view.getHeight() / 2);
                    } else if (isLastItem(position)) {
                        animateZoomIn(view, mCurrentView.getWidth(), view.getHeight() / 2);
                        Log.d(TAG, "isLastItem: w=" + view.getWidth() + ", h=" + view.getHeight() / 2);
                    } else {
                        animateZoomIn(view, view.getWidth() / 2, view.getHeight() / 2);
                        Log.d(TAG, "isMeddileItem: w=" + view.getWidth() / 2 + ", h=" + view.getHeight() / 2);
                    }
                }
            });
        }

        /**
         * View放大
         */
        void animateZoomIn(View view, float pivotX, float pivotY) {
            view.setPivotX(pivotX);
            view.setPivotY(pivotY);
            ViewCompat.animate(view)
                    .setDuration(mDuration)
                    .scaleX(mScale)
                    .scaleY(mScale)
                    .start();
        }

        /**
         * View还原
         */
        void animateRestore(View view, long duration) {
            ViewCompat.animate(view)
                    .setDuration(duration)
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .start();
        }

        void changeTextColor(View view, int color) {
            ((TextView) view).setTextColor(color);
        }

        /**
         * 当前选中的Item
         *
         * @param view
         */
        void setCurrentView(View view, int position) {
            mCurrentView = view;
            mCurrentPosition = position;
        }

        /**
         * 上次选中的Item
         */
        private void setPrevView(View view, int position) {
            mPrevView = view;
            mPrevPosition = position;
        }

        /**
         * 是否是第一个Item
         *
         * @param position
         * @return
         */
        boolean isFirstItem(int position) {
            return position == 0 ? true : false;
        }

        /**
         * 是否是最后一个Item
         *
         * @param position
         * @return
         */
        boolean isLastItem(int position) {
            return position == getItemCount() - 1 ? true : false;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return mDatas == null || mDatas.size() <= 0 ? 0 : mDatas.size();
        }

        public List<String> getData() {
            return mDatas;
        }

        public void setFixedItem(int index) {
            mMoveIndex = index;
        }

        /**
         * 项目类
         */
        class ItemViewHolder extends RecyclerView.ViewHolder {
            /**
             * 名称tabName
             */
            View item;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                item = itemView.findViewById(R.id.huyatabview_item);
                item.setBackgroundColor(Color.parseColor("#ffffff"));// 白色
            }
        }
    }
}
